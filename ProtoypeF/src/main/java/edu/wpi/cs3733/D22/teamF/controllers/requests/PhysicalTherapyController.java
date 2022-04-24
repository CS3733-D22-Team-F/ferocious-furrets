package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.PhysicalTherapyRequest;
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

public class PhysicalTherapyController extends PageController
    implements Initializable, IRequestController {

  @FXML JFXComboBox nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML JFXComboBox typeChoice; // Lab Type Choice Box
  @FXML TextField durationTime; // duration time field in minutes
  @FXML TextArea notes;
  @FXML JFXComboBox statusChoice; // Status Choice Box
  @FXML BorderPane masterPane;
  @FXML JFXTreeTableView treeTable;
  @FXML Pane tablePane;

  private String typeChoiceS;
  private String durationTimeS;
  private String notesS;

  TreeItem<PhysicalTherapyRequest> treeRoot =
      new TreeItem<>(new PhysicalTherapyRequest(typeChoiceS, durationTimeS, notesS));

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("Electric Stim");
    temp1.add("Stretching");
    temp1.add("Heating");
    temp1.add("Ice");
    temp1.add("Resistance Therapy");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("");

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

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet ptRequestTables =
        DatabaseManager.getInstance().getPTDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<PhysicalTherapyRequest> secReqs = new ArrayList<PhysicalTherapyRequest>();
    PhysicalTherapyRequest ptr;
    String currentLabReqID;

    while (ptRequestTables.next()) {
      currentLabReqID = ptRequestTables.getString("reqID");
      //      System.out.println(currentLabReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentLabReqID)) {
          //          System.out.println("matched :)");
          ptr =
              new PhysicalTherapyRequest(
                  ptRequestTables.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  ptRequestTables.getString("type"),
                  ptRequestTables.getString("duration"),
                  ptRequestTables.getString("notes"));
          secReqs.add(ptr);
          servRequest.close();
          break;
        }
      }
    }

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (PhysicalTherapyRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(PhysicalTherapyRequest));
            });

    TreeTableColumn<PhysicalTherapyRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<PhysicalTherapyRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<PhysicalTherapyRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) -> {
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

    TreeTableColumn<PhysicalTherapyRequest, String> therapyTypeColumn =
        new TreeTableColumn<>("Therapy Type");
    therapyTypeColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMedicalType()));

    TreeTableColumn<PhysicalTherapyRequest, String> durationColumn =
        new TreeTableColumn<>("Duration");
    durationColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getDuration()));

    TreeTableColumn<PhysicalTherapyRequest, String> notesColumn = new TreeTableColumn<>("Notes");
    notesColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNotes()));

    TreeTableColumn<PhysicalTherapyRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<PhysicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<PhysicalTherapyRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(
            reqIDCol,
            nodeIDCol,
            assignedToCol,
            therapyTypeColumn,
            durationColumn,
            notesColumn,
            statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    therapyTypeColumn.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    durationColumn.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    notesColumn.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(7));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  @Override
  public void submit() throws SQLException, IOException {
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")
        || durationTime.getText().equals("")
        || notes.getText().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      RequestSystem req = new RequestSystem("PT");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeChoice.getValue().toString());
      fields.add(durationTime.getText());
      fields.add(notes.getText());
      req.placeRequest(fields);
    }
    reset();

    startTable();
  }

  @Override
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
    durationTime.clear();
    notes.clear();
  }

  /* helper */

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

  /** clears the table in the request page */
  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  @Override
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
