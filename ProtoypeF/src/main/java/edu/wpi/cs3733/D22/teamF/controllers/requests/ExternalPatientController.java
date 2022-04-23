package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.ServiceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.AudioVisualRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.ExtPatientDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ExternalPatientController extends PageController
    implements Initializable, IRequestController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private BorderPane masterPane;
  @FXML private TextField patientField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private TextField addressField;
  @FXML private ComboBox methodField;
  @FXML private HBox topHBox;
  @FXML private HBox middleHBox;
  @FXML private HBox bottomHBox;
  @FXML private Rectangle rectangle1;
  @FXML private Rectangle rectangle2;
  @FXML private JFXTreeTableView treeTable;
  @FXML private Pane tablePane;
  @FXML private JFXComboBox nodeField;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  private String requestID;

  private String address;

  private String method;

  TreeItem<ExtPatientDeliveryRequest> treeRoot =
      new TreeItem<>(new ExtPatientDeliveryRequest(requestID, address, method));

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {

    //    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    //    rectangle1.heightProperty().bind(masterPane.heightProperty());
    //    rectangle2.widthProperty().bind(masterPane.widthProperty().divide(2));
    //    topHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    //    middleHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(600));
    //    middleHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    //    bottomHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(300));
    //    bottomHBox.maxWidthProperty().bind(rectangle1.widthProperty());

    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> methodType = new ArrayList<>();

    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    methodType.add("Helicopter");
    methodType.add("Plane");
    methodType.add("Ambulance");
    methodType.add("Personal Transport");
    methodField.getItems().addAll(methodType);
    methodField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);
    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    try {
      startTable();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void submit() throws SQLException, IOException {

    ArrayList<Object> requestList = new ArrayList<>();
    if (addressField.getText().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || statusChoice.getValue().equals("")
        || methodField.getValue().equals("")
        || addressField.getText().equals("")) {
      // Adds alert for empty fields for ext-patient request
      //      AGlobalMethods.showAlert("Empty Field(s)", submitButton);
    } else {
      requestList.clear();
      requestList.add("External Patient Transport Request for: " + addressField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      ServiceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("ExternalPatient");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(addressField.getText());
      fields.add(methodField.getValue().toString());
      req.placeRequest(fields);

      reset();
    }

    startTable();
  }

  @FXML
  public void reset() {
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    addressField.setText("");
    methodField.valueProperty().setValue("");
  }

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet eptReqTable =
        DatabaseManager.getInstance().getExtPatDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<ExtPatientDeliveryRequest> eptReqs = new ArrayList<ExtPatientDeliveryRequest>();
    ExtPatientDeliveryRequest ept;
    String currentLabReqID;

    while (eptReqTable.next()) {
      currentLabReqID = eptReqTable.getString("reqID");
      //      System.out.println(currentLabReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentLabReqID)) {
          //          System.out.println("matched :)");
          ept =
              new ExtPatientDeliveryRequest(
                  eptReqTable.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  eptReqTable.getString("address"),
                  eptReqTable.getString("method"));
          eptReqs.add(ept);
          servRequest.close();
          break;
        }
      }
    }

    treeRoot.setExpanded(true);
    eptReqs.stream()
        .forEach(
            (AudioVisualRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(AudioVisualRequest));
            });

    TreeTableColumn<ExtPatientDeliveryRequest, String> reqIDCol =
        new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<ExtPatientDeliveryRequest, String> nodeIDCol =
        new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<ExtPatientDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                empIDToFirstName(param.getValue().getValue().getAssignedEmpID())
                    + " "
                    + empIDToLastName(param.getValue().getValue().getAssignedEmpID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID());
        });

    TreeTableColumn<ExtPatientDeliveryRequest, String> methodColumn =
        new TreeTableColumn<>("Delivery Method");
    methodColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMethod()));

    TreeTableColumn<ExtPatientDeliveryRequest, String> addressColumn =
        new TreeTableColumn<>("Address");
    addressColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAddress()));

    TreeTableColumn<ExtPatientDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ExtPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<ExtPatientDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(reqIDCol, nodeIDCol, assignedToCol, methodColumn, addressColumn, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    methodColumn.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    addressColumn.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String generateReqID() throws SQLException {

    String nameAb = methodField.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nameAb + reqNum;
    return nID;
  }

  /** clears the table in the request page */
  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public ContextMenu makeContextMenu() {
    return null;
  }

  public String nodeIDToName(String nID) throws SQLException {
    String cmd = String.format("SELECT longName FROM Locations WHERE nodeID = '%s'", nID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("longName");
    }
    return lName;
  }

  public String empIDToFirstName(String eID) throws SQLException {
    String cmd = String.format("SELECT firstName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String fName = "";
    while (rset.next()) {
      fName = rset.getString("firstName");
    }
    return fName;
  }

  public String empIDToLastName(String eID) throws SQLException {
    String cmd = String.format("SELECT lastName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("lastName");
    }
    return lName;
  }
}
