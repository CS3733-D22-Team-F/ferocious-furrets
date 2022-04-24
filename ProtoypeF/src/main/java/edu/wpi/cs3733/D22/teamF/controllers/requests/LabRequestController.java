package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.ServiceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.LabRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * lab request controller
 *
 * @see Initializable
 */
public class LabRequestController extends PageController
    implements Initializable, IRequestController {

  @FXML JFXComboBox nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML private BorderPane masterPane;
  @FXML TextField reqID;
  @FXML Button resolve;

  @FXML ComboBox typeChoice; // Lab Type Choice Box
  @FXML ComboBox statusChoice; // Status Choice Box
  //  @FXML TableView<labRequest> table;
  //  @FXML TableColumn<labRequest, String> locationCol;
  //  @FXML TableColumn<labRequest, String> assignedCol;
  //  @FXML TableColumn<labRequest, String> requestedCol;
  //  @FXML TableColumn<labRequest, String> statusCol;
  //  @FXML TableColumn<labRequest, String> equipCol;
  @FXML TreeTableView table;
  @FXML Pane tablePane;

  private String requestID;
  private String nodeID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String sampleType;

  TreeItem<LabRequest> treeRoot =
      new TreeItem<>(
          new LabRequest(requestID, nodeID, assignedEmpID, requesterEmpID, status, sampleType));

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("Blood");
    temp1.add("Urine");
    typeChoice.getItems().addAll(temp1);

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);

    try {
      startTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  // Use Try/Catch when call this function

  /**
   * Use Try/Catch when call this function submits a labrequest from user inputs
   *
   * @return labRequest object
   */
  public void submit() throws SQLException, IOException {
    ArrayList<Object> requestList = new ArrayList<>();
    // Alert is made if any of the fields are empty
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      RequestSystem req = new RequestSystem("Lab");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeChoice.getValue().toString());
      req.placeRequest(fields);
      requestList.clear();
      requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue());
      ServiceRequestStorage.addToArrayList(requestList);
    }
    reset();

    startTable();
  }

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet labRequestTables =
        DatabaseManager.getInstance().getLabRequestDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<LabRequest> secReqs = new ArrayList<LabRequest>();
    LabRequest er;
    String currentLabReqID;

    while (labRequestTables.next()) {
      currentLabReqID = labRequestTables.getString("reqID");
      //      System.out.println(currentLabReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentLabReqID)) {
          //          System.out.println("matched :)");
          er =
              new LabRequest(
                  labRequestTables.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  labRequestTables.getString("type"));
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    labRequestTables.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (labRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(labRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<LabRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<LabRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<LabRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<LabRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<LabRequest, String> assignedToCol = new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<LabRequest, String> param) -> {
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

    TreeTableColumn<LabRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<LabRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableColumn<LabRequest, String> sampleTypeCol = new TreeTableColumn<>("Sample Type");
    sampleTypeCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<LabRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getSampleType()));

    TreeTableView<LabRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(reqIDCol, nodeIDCol, assignedToCol, sampleTypeCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    sampleTypeCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  @FXML
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Lab");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  /**
   * shows the queue scene for lab requests
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    //    Scene scene = SceneManager.getInstance().setScene("labRequestQueue.fxml");
    //    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //    stage.setScene(scene);
    //    stage.show();
  }

  // TODO make a interaface for all controllers
  public String generateReqID() throws SQLException {
    String nNodeType = typeChoice.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setLandingScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
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
