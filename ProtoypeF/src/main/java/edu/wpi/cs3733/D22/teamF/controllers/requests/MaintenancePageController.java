package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest.MaintenanceRequest;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MaintenancePageController extends PageController
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

  private TreeItem<MaintenanceRequest> treeRoot =
      new TreeItem<>(
          new MaintenanceRequest(
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
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusBox.getItems().addAll(statusDrop);

    ArrayList<Object> equipmentIDs = new ArrayList<>();
    try {
      for (String id : getEquipment()) {
        equipmentIDs.add(id);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    equipmentBox.getItems().addAll(equipmentIDs);

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
  public void submit() {

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

      try {
        fields.add(0, generateReqID());
        fields.add(1, nodeIDFinder(locationBox.getValue().toString()));
        fields.add(2, employeeIDFinder(assignedEmployeeBox.getValue().toString()));
        fields.add(3, employeeIDFinder(requestedEmployeeBox.getValue().toString()));
        fields.add(4, statusBox.getValue().toString());
        fields.add(5, equipmentBox.getValue().toString());
        fields.add(6, maintenanceBox.getValue().toString());
        req.placeRequest(fields);
        startTable();
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      }
      reset();
    }
  }

  /** clears the fields in the request page */
  public void reset() {
    locationBox.valueProperty().setValue(null);
    assignedEmployeeBox.valueProperty().setValue(null);
    requestedEmployeeBox.valueProperty().setValue(null);
    statusBox.valueProperty().setValue(null);
    equipmentBox.valueProperty().setValue(null);
    maintenanceBox.valueProperty().setValue(null);
  }

  /* resolve request */
  public void resolve() {
    if (!reqIDField.getText().equals("")) {
      RequestSystem req = new RequestSystem("Maintenance");
      try {
        req.resolveRequest(reqIDField.getText());
        startTable();
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      }
      reqIDField.clear();
    } else {
      System.out.println("Fields are blank"); // error message on screen?
    }
  }

  /**
   * Starts the table in the request page
   *
   * @throws SQLException
   * @throws IOException
   */
  public void startTable() throws SQLException, IOException {
    clearTable();

    ResultSet maintenanceRequestList =
        DatabaseManager.getInstance().getMaintenanceDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<MaintenanceRequest> secReqs = new ArrayList<>();
    MaintenanceRequest er;
    String currentEquipDelReqID;

    while (maintenanceRequestList.next()) {
      currentEquipDelReqID = maintenanceRequestList.getString("reqID");
      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)) {
          er =
              new MaintenanceRequest(
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
            (MaintenanceRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(MaintenanceRequest));
            });

    TreeTableColumn<MaintenanceRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<MaintenanceRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<MaintenanceRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) -> {
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

    TreeTableColumn<MaintenanceRequest, String> equipmentIDCol =
        new TreeTableColumn<>("Equipment ID");
    equipmentIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getEquipID()));

    TreeTableColumn<MaintenanceRequest, String> maintenanceTypeCol =
        new TreeTableColumn<>("Maintenance Type");
    maintenanceTypeCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMaintenanceType()));

    TreeTableColumn<MaintenanceRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MaintenanceRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));
    ;

    TreeTableView<MaintenanceRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(reqIDCol, nodeIDCol, assignedToCol, equipmentIDCol, maintenanceTypeCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    equipmentIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    maintenanceTypeCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
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
    String nNodeType = maintenanceBox.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().getRequestDAO().get();
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
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
  }

  public ArrayList<String> getEquipment() throws SQLException {
    ArrayList<String> equipIDs = new ArrayList<>();
    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT EQUIPID FROM MEDICALEQUIPMENT");
    while (rset.next()) {
      equipIDs.add(rset.getString("EQUIPID"));
    }
    rset.close();
    return equipIDs;
  }

  public String nodeIDToName(String nID) throws SQLException {
    String cmd = String.format("SELECT longName FROM Locations WHERE nodeID = '%s'", nID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("longName");
    }
    rset.close();

    return lName;
  }

  public String empIDToFirstName(String eID) throws SQLException {
    String cmd = String.format("SELECT firstName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String fName = "";
    while (rset.next()) {
      fName = rset.getString("firstName");
    }
    rset.close();

    return fName;
  }

  public String empIDToLastName(String eID) throws SQLException {
    String cmd = String.format("SELECT lastName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("lastName");
    }
    rset.close();

    return lName;
  }
}
