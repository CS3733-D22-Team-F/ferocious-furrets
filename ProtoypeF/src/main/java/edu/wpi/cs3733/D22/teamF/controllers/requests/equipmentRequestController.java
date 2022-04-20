package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.AGlobalMethods;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class equipmentRequestController extends PageController
    implements Initializable, IRequestController {

  // TODO remove
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private BorderPane masterPane;
  @FXML private JFXComboBox nodeField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private ComboBox typeChoice;
  @FXML private ComboBox statusChoice;
  @FXML private TextField reqID;
  @FXML private Rectangle rectangle1;
  @FXML private Rectangle rectangle2;
  @FXML private Label label1;
  @FXML private VBox leftVBox;
  @FXML private HBox leftHBox1;
  @FXML private HBox leftHBox2;
  @FXML private HBox leftHBox3;
  @FXML private ImageView logo;
  @FXML private JFXButton resolveReq;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private TreeTableView table;
  @FXML private Pane tablePane;
  @FXML private JFXButton filterButton;
  @FXML private TextField filterEmployee;

  private String requestID;
  private String nodeID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String requestedEquipmentID;

  private TreeItem<equipmentDeliveryRequest> treeRoot =
      new TreeItem<>(
          new equipmentDeliveryRequest(
              requestID, nodeID, assignedEmpID, requesterEmpID, status, requestedEquipmentID));

  public equipmentRequestController() {}

  public equipmentRequestController(ContextMenu cm, MenuBar mb) {
    super(cm, mb);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    masterPane.setMinHeight(500);
    masterPane.setMinWidth(500);

    rectangle1.heightProperty().bind(masterPane.heightProperty());
    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    rectangle2.widthProperty().bind(masterPane.widthProperty().divide(2).add(2));
    leftHBox1.layoutXProperty().bind(rectangle1.widthProperty().divide(4));
    leftHBox2.layoutXProperty().bind(rectangle1.widthProperty().divide(4));
    leftVBox.layoutXProperty().bind(rectangle1.widthProperty().divide(4));
    leftHBox1.maxWidthProperty().bind(rectangle1.widthProperty().divide(2));
    leftHBox2.maxWidthProperty().bind(rectangle1.widthProperty().divide(2));
    leftHBox3.maxWidthProperty().bind(rectangle1.widthProperty().subtract(15));
    leftVBox.maxWidthProperty().bind(rectangle1.widthProperty().divide(2));
    leftVBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(300));
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> equipmentType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    equipmentType.add("Bed");
    equipmentType.add("X-Ray");
    equipmentType.add("Infusion Pump");
    equipmentType.add("Recliner");
    typeChoice.getItems().addAll(equipmentType);

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);

    try {
      startTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void submit() throws SQLException, IOException {

    String newReqID;
    String newNodeID;
    String newAssignedEmployee;
    String newRequestedEmployee;
    String newStatus;
    String newEquipID;

    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
//      AGlobalMethods.showAlert("Empty Field(s)", submitButton);
    } else {
      requestList.clear();
      requestList.add("Equipment Request of type: " + typeChoice.getValue().toString());
      requestList.add("Assigned Doctor: " + userField.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);

      RequestSystem req = new RequestSystem("Equipment");
      ArrayList<String> fields = new ArrayList<String>();

      newReqID = generateReqID();
      fields.add(0, newReqID);
      newNodeID = nodeIDFinder(nodeField.getValue().toString());
      fields.add(1, newNodeID);
      newAssignedEmployee = employeeIDFinder(employeeIDField.getValue().toString());
      fields.add(2, newAssignedEmployee);
      newRequestedEmployee = employeeIDFinder(userField.getValue().toString());
      fields.add(3, newRequestedEmployee);
      newStatus = statusChoice.getValue().toString();
      fields.add(4, newStatus);
      newEquipID = getAvailableEquipment();
      fields.add(5, newEquipID);
      req.placeRequest(fields);

      resetFunction();
    }

    startTable();
  }

  @FXML
  void resetFunction() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
  }

  @Override
  public void reset() {}

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet equipRequest =
        DatabaseManager.getMedEquipDelReqDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<equipmentDeliveryRequest> secReqs = new ArrayList<equipmentDeliveryRequest>();
    equipmentDeliveryRequest er;
    String currentEquipDelReqID;

    while (equipRequest.next()) {
      currentEquipDelReqID = equipRequest.getString("reqID");
      //      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)) {
          //          System.out.println("matched :)");
          er =
              new equipmentDeliveryRequest(
                  equipRequest.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  equipRequest.getString(
                      "equipID")); // ADD YOU UNIQUE FIELD TO THIS (MAKE SURE OBJECT PARAMETERS ARE
          // CORRECT TOO)
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    equipRequest.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (equipmentDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(equipmentDeliveryRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<equipmentDeliveryRequest, String> nodeIDCol =
        new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<equipmentDeliveryRequest, String> equipmentIDCol =
        new TreeTableColumn<>("Equipment ID:");
    equipmentIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequestedEquipmentID()));

    TreeTableColumn<equipmentDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<equipmentDeliveryRequest, String> requestedByCol =
        new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<equipmentDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<equipmentDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(nodeIDCol, equipmentIDCol, assignedToCol, requestedByCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    equipmentIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void f() throws SQLException, IOException {
    startFilteredTable(filterEmployee.getText());
  }

  public void startFilteredTable(String empName) throws SQLException, IOException {

    clearTable();

    ResultSet equipRequest =
        DatabaseManager.getMedEquipDelReqDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<equipmentDeliveryRequest> secReqs = new ArrayList<equipmentDeliveryRequest>();
    equipmentDeliveryRequest er;
    String currentEquipDelReqID;

    while (equipRequest.next()) {
      currentEquipDelReqID = equipRequest.getString("reqID");
      //      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)
            && servRequest.getString("assignedEmployeeID").equals(empName)) {
          //          System.out.println("matched :)");
          er =
              new equipmentDeliveryRequest(
                  equipRequest.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  equipRequest.getString(
                      "equipID")); // ADD YOU UNIQUE FIELD TO THIS (MAKE SURE OBJECT PARAMETERS ARE
          // CORRECT TOO)
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    equipRequest.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (equipmentDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(equipmentDeliveryRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<equipmentDeliveryRequest, String> nodeIDCol =
        new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<equipmentDeliveryRequest, String> equipmentIDCol =
        new TreeTableColumn<>("Equipment ID:");
    equipmentIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequestedEquipmentID()));

    TreeTableColumn<equipmentDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<equipmentDeliveryRequest, String> requestedByCol =
        new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<equipmentDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<equipmentDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<equipmentDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(nodeIDCol, equipmentIDCol, assignedToCol, requestedByCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    equipmentIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  /* Helpers */

  public String employeeIDFinder(String name) throws SQLException {
    String empID = "";
    String[] employeeName = name.split(",");
    String last = employeeName[0];
    String first = employeeName[1];
    last = last.strip();
    first = first.strip();
    String cmd =
        String.format(
            "SELECT EMPLOYEEID FROM EMPLOYEE WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'",
            first, last);
    ResultSet rset = DatabaseManager.runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Equipment");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String getAvailableEquipment() throws SQLException {
    ResultSet rset =
        DatabaseManager.runQuery(
            "SELECT EQUIPID FROM MEDICALEQUIPMENT WHERE STATUS = 'available' AND EQUIPTYPE = '"
                + typeChoice.getValue().toString()
                + "'");
    String eID = "";
    if (!rset.next()) {

    } else {
      eID = rset.getString("equipID");
    }
    return eID;
  }

  public String generateReqID() throws SQLException, IOException {
    String nNodeType = typeChoice.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getRequestDAO().get();
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  // TODO make a interaface for all controllers
  public String generateReqID(int requestListLength, String equipID, String nodeID) {
    String reqAbb = "ER";

    return reqAbb + equipID + (requestListLength + 1) + nodeID;
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }
}
