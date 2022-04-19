package edu.wpi.cs3733.D22.teamF.boundary.pagecontrollers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.controllers.requests.IRequestController;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest.maintenanceSR;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class maintenancePageController extends PageController
    implements Initializable, IRequestController {

  @FXML JFXComboBox locationBox;
  @FXML JFXComboBox assignedEmployeeBox;
  @FXML JFXComboBox requestedEmployeeBox;
  @FXML JFXComboBox statusBox;
  @FXML JFXComboBox equipmentBox;
  @FXML JFXComboBox maintenanceBox;

  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;

  @FXML TextField reqIDField;
  @FXML JFXButton resolveButton;

  @FXML Pane tablePane;
  @FXML BorderPane masterPane;
  @FXML JFXTreeTableView table;

  private String requestID;
  private String nodeID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String equipID;
  private String maintenanceType;

  private TreeItem<maintenanceSR> treeRoot =
      new TreeItem<>(
          new maintenanceSR(
              requestID, nodeID, assignedEmpID, requesterEmpID, status, equipID, maintenanceType));

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<Object> locations = locationNames();
    locationBox.getItems().addAll(locations);

    ArrayList<Object> employees = employeeNames();
    assignedEmployeeBox.getItems().addAll(employees);
    assignedEmployeeBox.setValue("");
    requestedEmployeeBox.getItems().addAll(employees);
    requestedEmployeeBox.setValue("");

    ArrayList<Object> statusDrop = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusBox.getItems().addAll(statusDrop);
    statusBox.setValue("");

    ArrayList<Object> equipmentType = new ArrayList<>();
    equipmentType.add("Bed");
    equipmentType.add("X-Ray");
    equipmentType.add("Infusion Pump");
    equipmentType.add("Recliner");
    equipmentBox.getItems().addAll(equipmentType);

    ArrayList<Object> maintenanceType = new ArrayList<>();
    maintenanceType.add("Routine");
    maintenanceType.add("Emergency");
    maintenanceType.add("Clean");
    maintenanceBox.getItems().addAll(maintenanceType);

    try {
      startTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * submit function for button in request controllers
   *
   * @throws SQLException
   * @throws IOException
   */
  public void submit() throws SQLException, IOException {

    String newReqID;
    String newNodeID;
    String newAssignedEmployee;
    String newRequestedEmployee;
    String newStatus;
    String newEquipID;
    String newMaintanenceType;

    if (locationBox.getValue().toString().equals("")
        || assignedEmployeeBox.getValue().toString().equals("")
        || requestedEmployeeBox.getValue().toString().equals("")
        || statusBox.getValue().equals("")
        || equipmentBox.getValue().equals("")
        || maintenanceBox.getValue().equals("")) {
      System.out.println("There are still blank fields"); // popup? error message?
    } else {

      RequestSystem req = new RequestSystem("Maintenance");
      ArrayList<String> fields = new ArrayList<>();

      fields.add(0, generateReqID());
      fields.add(1, nodeIDFinder(locationBox.getValue().toString()));
      fields.add(2, employeeIDFinder(assignedEmployeeBox.getValue().toString()));
      fields.add(3, employeeIDFinder(requestedEmployeeBox.getValue().toString()));
      fields.add(4, statusBox.getValue().toString());
      fields.add(5, getAvailableEquipment());
      fields.add(6, maintenanceBox.getValue().toString());
      req.placeRequest(fields);

      reset();
    }

    startTable();
  }

  /** clears the fields in the request page */
  public void reset() {}

  /**
   * Starts the table in the request page
   *
   * @throws SQLException
   * @throws IOException
   */
  public void startTable() throws SQLException, IOException {
    clearTable();

    ResultSet maintenanceRequestList =
        DatabaseManager.getMaintenanceDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<maintenanceSR> secReqs = new ArrayList<>();
    maintenanceSR er;
    String currentEquipDelReqID;

    while (maintenanceRequestList.next()) {
      currentEquipDelReqID = maintenanceRequestList.getString("reqID");
      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)) {
          er =
              new maintenanceSR(
                  maintenanceRequestList.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  maintenanceRequestList.getString("equipID"),
                  maintenanceRequestList.getString("maintenanceType"));
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    maintenanceRequestList.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (maintenanceSR) -> {
              treeRoot.getChildren().add(new TreeItem<>(maintenanceSR));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<maintenanceSR, String> nodeIDCol = new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<maintenanceSR, String> assignedToCol = new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<maintenanceSR, String> requestedByCol = new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<maintenanceSR, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableColumn<maintenanceSR, String> equipmentIDCol = new TreeTableColumn<>("Equipment ID:");
    equipmentIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getEquipID()));

    TreeTableColumn<maintenanceSR, String> maintenanceTypeCol =
        new TreeTableColumn<>("Equipment ID:");
    maintenanceTypeCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<maintenanceSR, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMaintenanceType()));

    TreeTableView<maintenanceSR> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(
            nodeIDCol,
            equipmentIDCol,
            maintenanceTypeCol,
            assignedToCol,
            requestedByCol,
            statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    equipmentIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  /** clears the table in the request page */
  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  /**
   * generates a reqID based on fields in the request page
   *
   * @return returns String (a reqID)
   * @throws SQLException
   */
  public String generateReqID() throws SQLException, IOException {
    String nNodeType = equipmentBox.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getRequestDAO().get();
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public ContextMenu makeContextMenu() {
    return null;
  }

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
    RequestSystem req = new RequestSystem(""); // TODO edit this
    req.resolveRequest(reqIDField.getText());
    reqIDField.clear();
  }

  public String getAvailableEquipment() throws SQLException {
    ResultSet rset =
        DatabaseManager.runQuery(
            "SELECT EQUIPID FROM MEDICALEQUIPMENT WHERE STATUS = 'available' AND EQUIPTYPE = '"
                + equipmentBox.getValue().toString()
                + "'");
    String eID = "";
    if (!rset.next()) {
      System.out.println("No Available Equipment"); // TODO Make popup?
    } else {
      eID = rset.getString("equipID");
    }
    rset.close();
    return eID;
  }
}
