package edu.wpi.cs3733.D22.teamF.boundary.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.boundary.PageController;
import edu.wpi.cs3733.D22.teamF.boundary.serviceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
  @FXML private ImageView backgroundIMG;
  @FXML private ImageView logo;
  @FXML private JFXButton resolveReq;
  @FXML private Button resetButton;
  @FXML private Button submitButton;

  public equipmentRequestController() {}

  public equipmentRequestController(ContextMenu cm, MenuBar mb) {
    super(cm, mb);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);

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
    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty().divide(2));
    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
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
  }

  @FXML
  void resetFunction() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
  }

  @FXML
  public void submit() throws SQLException {

    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
    } else {
      requestList.clear();
      requestList.add("Equipment Request of type: " + typeChoice.getValue().toString());
      requestList.add("Assigned Doctor: " + userField.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);

      RequestSystem req = new RequestSystem("Equipment");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(getAvailableEquipment());
      req.placeRequest(fields);

      resetFunction();
    }
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

  @Override
  public void reset() {}

  // TODO make a interaface for all controllers
  public String generateReqID(int requestListLength, String equipID, String nodeID) {
    String reqAbb = "ER";

    return reqAbb + equipID + (requestListLength + 1) + nodeID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setLandingScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}