package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.medicineDeliveryRequest;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class medicineController extends PageController
    implements Initializable, IRequestController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private JFXComboBox nodeField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private TextField typeOfMed;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private TextField prescribingDoctor;
  @FXML private TextField dosage;
  @FXML private ComboBox units;
  @FXML private ComboBox units2;
  @FXML private TextField totalAmount;
  @FXML private TextField pharmacyAddress;
  @FXML private AnchorPane masterPane;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML private TableView<medicineDeliveryRequest> table;
  @FXML private TableColumn<medicineDeliveryRequest, String> locationCol;
  @FXML private TableColumn<medicineDeliveryRequest, String> assignedCol;
  @FXML private TableColumn<medicineDeliveryRequest, String> requestedCol;
  @FXML private TableColumn<medicineDeliveryRequest, String> medTypeCol;
  @FXML private TableColumn<medicineDeliveryRequest, String> statusCol;

  ObservableList<medicineDeliveryRequest> currentTableRows = FXCollections.observableArrayList();

  @FXML
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeOfMed.clear();
    statusChoice.valueProperty().setValue(null);
    // typeChoice.valueProperty().setValue(null);
    prescribingDoctor.clear();
    dosage.clear();
    units.valueProperty().setValue(null);
    units2.valueProperty().setValue(null);
    totalAmount.clear();
    pharmacyAddress.clear();
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeOfMed.getText().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
    } else {
      requestList.clear();
      // requestList.add("Medicine Request for: " + typeChoice.getValue());
      requestList.add("Assigned Doctor: " + userField.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("Medicine");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeOfMed.getText());
      fields.add(prescribingDoctor.getText());
      String catDosage = dosage.getText() + units.getValue().toString();
      fields.add(catDosage);
      String catTotalAmount = totalAmount.getText() + units.getValue().toString();
      fields.add(catTotalAmount);
      fields.add(pharmacyAddress.getText());
      req.placeRequest(fields);

      reset();
    }
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Medicine");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String generateReqID() throws SQLException {
    String nNodeType = typeOfMed.getText().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> medicineType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    ArrayList<Object> unitMeasurements = new ArrayList<>();
    unitMeasurements.add("g");
    unitMeasurements.add("mg");
    unitMeasurements.add("mcg");
    unitMeasurements.add("mL");
    units.getItems().addAll(unitMeasurements);
    units2.getItems().addAll(unitMeasurements);
    units.setValue("mg");
    units2.getItems().addAll(unitMeasurements);
    units2.setValue("mg");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);

    String currentReqIDM;
    String currentReqIDO;

    locationCol.setCellValueFactory(
        new PropertyValueFactory<medicineDeliveryRequest, String>("nodeID"));
    assignedCol.setCellValueFactory(
        new PropertyValueFactory<medicineDeliveryRequest, String>("assignedEmpID"));
    requestedCol.setCellValueFactory(
        new PropertyValueFactory<medicineDeliveryRequest, String>("requesterEmpID"));
    medTypeCol.setCellValueFactory(
        new PropertyValueFactory<medicineDeliveryRequest, String>("medicine"));
    statusCol.setCellValueFactory(
        new PropertyValueFactory<medicineDeliveryRequest, String>("status"));

    table.setItems(currentTableRows);

    ArrayList<String> currentFields = new ArrayList<>();

    try {

      ResultSet medicineDeliveryRequest = DatabaseManager.getMedicineDAO().get();

      while (medicineDeliveryRequest.next()) {
        ResultSet DeliveryRequest = DatabaseManager.getRequestDAO().get();

        currentReqIDM = medicineDeliveryRequest.getString("reqID");

        while (DeliveryRequest.next()) {
          currentReqIDO = DeliveryRequest.getString("reqID");

          if (currentReqIDO.equals(currentReqIDM)) {
            System.out.println("they are equal :");

            currentFields.add(0, currentReqIDO);
            currentFields.add(1, DeliveryRequest.getString("nodeID"));
            currentFields.add(2, DeliveryRequest.getString("assignedEmployeeID"));
            currentFields.add(3, DeliveryRequest.getString("requesterEmployeeID"));
            currentFields.add(4, DeliveryRequest.getString("status"));
            currentFields.add(5, medicineDeliveryRequest.getString("medID"));

            updateTableFromFields(currentFields);
          } else {

          }
        }
        DeliveryRequest.close();
      }
      medicineDeliveryRequest.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updateTableFromFields(ArrayList<String> fields) {
    currentTableRows.add(
        new medicineDeliveryRequest(
            fields.get(0), // req id
            fields.get(1), // node id
            fields.get(2), // assigned emp id
            fields.get(3), // requester emp id
            fields.get(4), // status
            fields.get(5))); // equip id
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
