package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.ServiceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.ScanRequest;
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
 * controller for scan scene
 *
 * @see Initializable
 */
public class ScanController extends PageController implements Initializable, IRequestController {

  @FXML BorderPane masterPane;
  @FXML Pane tablePane;
  @FXML private TreeTableView table;
  @FXML JFXComboBox nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML Button reset;
  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML ComboBox typeChoice; // Lab Type Choice Box
  @FXML ComboBox statusChoice; // Status Choice Box

  private String requestID;
  private String nodeID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String scanType;

  private TreeItem<ScanRequest> treeRoot =
      new TreeItem<>(
          new ScanRequest(requestID, nodeID, assignedEmpID, requesterEmpID, status, scanType));

  public ScanController() {}

  public ScanController(ContextMenu c_menu, MenuBar m_menu) {
    super(c_menu, m_menu);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("CAT");
    temp1.add("xray");
    temp1.add("MRI");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("CAT");

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

  /**
   * Use Try/Catch when call this function submits a medical request using user inputs
   *
   * @return MedicalRequest object
   */
  public void submit() throws SQLException, IOException {
    ArrayList<Object> requestList = new ArrayList<>();
    //    String reqID =
    //        generateReqID(
    //            DatabaseManager.getScanRequestDAO().getAllRequests().size(),
    //            typeChoice.getValue().toString(),
    //            nodeField.getText());
    String scanType = typeChoice.getValue().toString();
    // If any of the field is missing, pop up a notice
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      RequestSystem req = new RequestSystem("Scan");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeChoice.getValue().toString());
      req.placeRequest(fields);

      requestList.clear();
      requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
      // requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue().toString());
      ServiceRequestStorage.addToArrayList(requestList);
      this.reset();
    }

    startTable();
  }

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet scanRequestTable =
        DatabaseManager.getInstance().getScanRequestDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<ScanRequest> secReqs = new ArrayList<>();
    ScanRequest er;
    String currentEquipDelReqID;

    while (scanRequestTable.next()) {
      currentEquipDelReqID = scanRequestTable.getString("reqID");
      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)) {
          //          System.out.println("matched :)");
          er =
              new ScanRequest(
                  scanRequestTable.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  scanRequestTable.getString(
                      "type")); // ADD YOU UNIQUE FIELD TO THIS (MAKE SURE OBJECT PARAMETERS ARE
          // CORRECT TOO)
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    scanRequestTable.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (scanRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(scanRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<ScanRequest, String> nodeIDCol = new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ScanRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<ScanRequest, String> scanTypeCol = new TreeTableColumn<>("Equipment ID:");
    scanTypeCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ScanRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getScanType()));

    TreeTableColumn<ScanRequest, String> assignedToCol = new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ScanRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<ScanRequest, String> requestedByCol = new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ScanRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<ScanRequest, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<ScanRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<ScanRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(nodeIDCol, scanTypeCol, assignedToCol, requestedByCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    scanTypeCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
  }

  /* helper */
  public String generateReqID(int requestListLength, String scanType, String nodeID) {
    String reqAbb = "SR";
    String sAb = "";
    if (scanType.equals("CAT")) {
      sAb = "C";
    } else if (scanType.equals("xray")) {
      sAb = "X";
    } else if (scanType.equals("MRI")) {
      sAb = "M";
    }
    return reqAbb + sAb + (requestListLength + 1) + nodeID;
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  void showSceneQueue(ActionEvent event) throws IOException {
    switchScene("labRequestQueue.fxml");
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Scan");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

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

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }
}
