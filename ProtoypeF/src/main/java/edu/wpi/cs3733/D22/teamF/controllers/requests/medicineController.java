package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.medicineDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/** ARCHIVED, NOW HANDLED THROUGH TEAM F MEDICINE REQUEST API */
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
  @FXML private BorderPane masterPane;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML private TreeTableView table;
  @FXML private Pane tablePane;

  private String requestID;
  private String nodeID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String medicine;
  private String RxDoctor;
  private String Dosage;
  private String totalAmountO;
  private String pharmacyAddressO;

  TreeItem<medicineDeliveryRequest> treeRoot =
      new TreeItem<>(
          new medicineDeliveryRequest(
              requestID,
              nodeID,
              assignedEmpID,
              requesterEmpID,
              status,
              medicine,
              RxDoctor,
              Dosage,
              totalAmountO,
              pharmacyAddressO));

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
  public void submit() throws SQLException, IOException {
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

    startTable();
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

    try {
      startTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet medicineReq = DatabaseManager.getMedicineDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<medicineDeliveryRequest> secReqs = new ArrayList<medicineDeliveryRequest>();
    medicineDeliveryRequest er;
    String currentMedEquipDelReqID;

    while (medicineReq.next()) {
      currentMedEquipDelReqID = medicineReq.getString("reqID");
      //      System.out.println(currentMedEquipDelReqID);
      servRequest = DatabaseManager.getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentMedEquipDelReqID)) {
          //          System.out.println("matched :)");
          er =
              new medicineDeliveryRequest(
                  medicineReq.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  medicineReq.getString("medicine"),
                  medicineReq.getString("RxDoctor"),
                  medicineReq.getString("dosage"),
                  medicineReq.getString("totalAmount"),
                  medicineReq.getString("pharmacyAddress"));
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }
    medicineReq.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (medicineDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(medicineDeliveryRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<medicineDeliveryRequest, String> nodeIDCol = new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<medicineDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<medicineDeliveryRequest, String> requestedByCol =
        new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<medicineDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableColumn<medicineDeliveryRequest, String> medicineCol =
        new TreeTableColumn<>("Medicine: ");
    medicineCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMedicine()));

    TreeTableColumn<medicineDeliveryRequest, String> RxDoctorCol =
        new TreeTableColumn<>("RxDoctor: ");
    RxDoctorCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRxDoctor()));

    TreeTableColumn<medicineDeliveryRequest, String> DosageCol = new TreeTableColumn<>("Dosage: ");
    DosageCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getDosage()));

    TreeTableColumn<medicineDeliveryRequest, String> totalAmountCol =
        new TreeTableColumn<>("Total Amount: ");
    totalAmountCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getTotalAmount()));

    TreeTableColumn<medicineDeliveryRequest, String> pharmaryAddressCol =
        new TreeTableColumn<>("Pharmacy Address: ");
    pharmaryAddressCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<medicineDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getPharmacyAddress()));

    TreeTableView<medicineDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(
            nodeIDCol,
            assignedToCol,
            requestedByCol,
            statusCol,
            medicineCol,
            RxDoctorCol,
            DosageCol,
            totalAmountCol,
            pharmaryAddressCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    medicineCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    RxDoctorCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    DosageCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    pharmaryAddressCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    totalAmountCol.minWidthProperty().bind(tablePane.widthProperty().divide(9));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(9));
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
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
