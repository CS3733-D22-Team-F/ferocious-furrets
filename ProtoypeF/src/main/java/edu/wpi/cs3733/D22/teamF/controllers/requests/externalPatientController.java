package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.extPatientDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class externalPatientController extends PageController
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

  TreeItem<extPatientDeliveryRequest> treeRoot =
      new TreeItem<>(new extPatientDeliveryRequest(requestID, address, method));

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {

    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    rectangle1.heightProperty().bind(masterPane.heightProperty());
    rectangle2.widthProperty().bind(masterPane.widthProperty().divide(2));
    topHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    middleHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(400));
    middleHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    bottomHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(300));
    bottomHBox.maxWidthProperty().bind(rectangle1.widthProperty());

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
    }
  }

  @FXML
  public void submit() throws SQLException {

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
      serviceRequestStorage.addToArrayList(requestList);
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

  public void startTable() throws SQLException {

    clearTable();

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM externalPatientRequest");
    ArrayList<extPatientDeliveryRequest> externalPatientReqs =
        new ArrayList<extPatientDeliveryRequest>();
    extPatientDeliveryRequest avr;

    while (rset.next()) {
      avr =
          new extPatientDeliveryRequest(
              rset.getString("reqID"), rset.getString("address"), rset.getString("method"));
      externalPatientReqs.add(avr);
    }
    rset.close();

    treeRoot.setExpanded(true);
    externalPatientReqs.stream()
        .forEach(
            (extPatientDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(extPatientDeliveryRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<extPatientDeliveryRequest, String> methodColumn =
        new TreeTableColumn<>("Delivery Method");
    methodColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<extPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMethod()));

    TreeTableColumn<extPatientDeliveryRequest, String> addressColumn =
        new TreeTableColumn<>("Address");
    addressColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<extPatientDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAddress()));

    TreeTableView<extPatientDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(methodColumn, addressColumn);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    addressColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
    methodColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
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

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
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
}
