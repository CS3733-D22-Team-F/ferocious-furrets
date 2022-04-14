package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class audioVisualController extends PageController
    implements Initializable, IRequestController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private BorderPane masterPane;
  @FXML private ImageView backgroundIMG;
  @FXML private JFXComboBox nodeField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private ComboBox typeChoice;
  @FXML private ComboBox objectChoice;
  @FXML private HBox topHBox;
  @FXML private HBox middleHBox;
  @FXML private HBox bottomHBox;
  @FXML private Rectangle rectangle1;
  @FXML private Rectangle rectangle2;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue("");
    objectChoice.valueProperty().setValue("");
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || statusChoice.getValue().equals("")
        || objectChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      requestList.clear();
      requestList.add("Audio/Visual Request for: " + objectChoice.getValue());
      // requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("Audio/Visual");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(objectChoice.getValue().toString().substring(0, 3));
      req.placeRequest(fields);

      reset();
    }
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Audio/Visual");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String generateReqID() throws SQLException {
    String nNodeType = objectChoice.getValue().toString().substring(0, 3);
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
    this.makeMenuBar(masterPane);

    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty().divide(2));
    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    rectangle1.heightProperty().bind(masterPane.heightProperty());
    rectangle2.widthProperty().bind(masterPane.widthProperty().divide(2));
    topHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    middleHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(400));
    middleHBox.maxWidthProperty().bind(rectangle1.widthProperty());
    bottomHBox.layoutXProperty().bind(rectangle1.widthProperty().divide(2).subtract(300));
    bottomHBox.maxWidthProperty().bind(rectangle1.widthProperty());

    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> accessibilityType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    accessibilityType.add("Deaf/Hard of Hearing");
    accessibilityType.add("Blind/Visually Impaired");
    typeChoice.getItems().addAll(accessibilityType);
    typeChoice.setValue("");
    objectChoice.getItems().add("");
    objectChoice.setValue("");

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);
  }

  /**
   * Fills the objectChoiceBox with the correct options based on whether the typeChoice is for
   * Deaf/Hard of Hearing objects or Blind/Visually Impaired objects
   */
  @FXML
  void fillObjectChoiceBox() {
    if (typeChoice.getValue().equals("Deaf/Hard of Hearing")) {
      ArrayList<Object> AccessibilityObjects = new ArrayList<>();
      AccessibilityObjects.add("H&M - Headset + Microphone");
      AccessibilityObjects.add("WIP - Written Info Pamphlets");
      AccessibilityObjects.add("CMT - Computer Transcriber");
      objectChoice.getItems().clear();
      objectChoice.getItems().addAll(AccessibilityObjects);
    } else if (typeChoice.getValue().equals("Blind/Visually Impaired")) {
      ArrayList<Object> AccessibilityObjects = new ArrayList<>();
      AccessibilityObjects.add("LPP - Large Print Info Pamphlets");
      AccessibilityObjects.add("BIP - Braille Info Pamphlets");
      AccessibilityObjects.add("CAN - Cane");
      objectChoice.getItems().clear();
      objectChoice.getItems().addAll(AccessibilityObjects);
    } else {
      objectChoice.getItems().clear();
      objectChoice.getItems().add("");
    }
  }

  /**
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setLandingScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
