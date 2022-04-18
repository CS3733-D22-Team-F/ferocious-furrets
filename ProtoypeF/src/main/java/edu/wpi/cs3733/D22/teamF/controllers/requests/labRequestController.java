package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * lab request controller
 *
 * @see Initializable
 */
public class labRequestController extends PageController
    implements Initializable, IRequestController {

  @FXML JFXComboBox nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML private AnchorPane masterPane;
  @FXML TextField reqID;
  @FXML Button resolve;

  @FXML ComboBox typeChoice; // Lab Type Choice Box
  @FXML ComboBox statusChoice; // Status Choice Box
  @FXML TableView<labRequest> table;
  @FXML TableColumn<labRequest, String> locationCol;
  @FXML TableColumn<labRequest, String> assignedCol;
  @FXML TableColumn<labRequest, String> requestedCol;
  @FXML TableColumn<labRequest, String> statusCol;
  @FXML TableColumn<labRequest, String> equipCol;

  ObservableList<labRequest> currentTableRows = FXCollections.observableArrayList();
  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
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
    temp1.add("");
    temp1.add("Blood");
    temp1.add("Urine");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("");

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);

    String currentReqIDM;
    String currentReqIDO;

    locationCol.setCellValueFactory(new PropertyValueFactory<labRequest, String>("nodeID"));
    assignedCol.setCellValueFactory(new PropertyValueFactory<labRequest, String>("assignedEmpID"));
    requestedCol.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("requesterEmpID"));
    statusCol.setCellValueFactory(new PropertyValueFactory<labRequest, String>("status"));
    equipCol.setCellValueFactory(new PropertyValueFactory<labRequest, String>("reqtype"));

    table.setItems(currentTableRows);

    ArrayList<String> currentFields = new ArrayList<>();

    try {

      // get from database
      ResultSet labRequest = DatabaseManager.getLabRequestDAO().get();

      while (labRequest.next()) {

        ResultSet ServiceRequest = DatabaseManager.getRequestDAO().get();

        currentReqIDM = labRequest.getString("reqID");
        //        System.out.println(currentReqIDM);

        while (ServiceRequest.next()) {

          currentReqIDO = ServiceRequest.getString("reqID");

          //          System.out.println(currentReqIDO);

          if (currentReqIDO.equals(currentReqIDM)) {

            System.out.println("they are equal :)");

            //            currentEquipID = EquipmentRequest.getString("equipID");
            //            currentNodeID = ServiceRequest.getString("nodeID");
            //            currentAssignedEmployeeID =
            // ServiceRequest.getString("assignedEmployeeID");
            //            currentRequesterEmployeeID =
            // ServiceRequest.getString("requesterEmployeeID");
            //            currentStatus = ServiceRequest.getString("status");

            currentFields.add(0, currentReqIDO);
            currentFields.add(1, ServiceRequest.getString("nodeID"));
            currentFields.add(2, ServiceRequest.getString("assignedEmployeeID"));
            currentFields.add(3, ServiceRequest.getString("requesterEmployeeID"));
            currentFields.add(4, ServiceRequest.getString("status"));
            currentFields.add(5, labRequest.getString("type"));

            updateTableFromFields(currentFields);

            //            System.out.println(currentReqIDO);
            //            System.out.println(currentNodeID);

            //            currentEquipmentRequestList.add(
            //                new equipmentDeliveryRequest(
            //                    currentReqIDO,
            //                    currentNodeID,
            //                    currentAssignedEmployeeID,
            //                    currentRequesterEmployeeID,
            //                    currentStatus,
            //                    currentEquipID));
          } else {
            //            System.out.println(currentReqIDM);
            //            System.out.println(currentReqIDO);
          }
        }
        ServiceRequest.close();
      }
      labRequest.close();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  public void updateTableFromFields(ArrayList<String> fields) {
    currentTableRows.add(
        new labRequest(
            fields.get(0), // req id
            fields.get(1), // node id
            fields.get(2), // assigned emp id
            fields.get(3), // requester emp id
            fields.get(4), // status
            fields.get(5))); // equip id
  }

  // Use Try/Catch when call this function

  /**
   * Use Try/Catch when call this function submits a labrequest from user inputs
   *
   * @return labRequest object
   */
  public void submit() throws SQLException {
    ArrayList<Object> returnList = new ArrayList<>(); // List will be returned
    ArrayList<String> serviceList = new ArrayList<>(); // List will show in label
    ArrayList<Object> requestList = new ArrayList<>();
    String sampleType = null;
    // Alert is made if any of the fields are empty
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {

      if (typeChoice.getValue().equals("blood")) {
        sampleType = "Blood";
      } else {
        sampleType = "Urine";
      }
      // int curLabReqSize = DatabaseManager.getLabRequestDAO().getAllRequests().size();
      // String reqID = generateReqID(curLabReqSize, sampleType, nodeField.getText());
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
      // requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
    }
    reset();
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

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
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
}
