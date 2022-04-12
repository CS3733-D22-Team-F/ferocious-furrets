package edu.wpi.cs3733.D22.teamF.controllers.requests;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class audioVisualController implements Initializable, IRequestController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private BorderPane masterPane;
  @FXML private ImageView backgroundIMG;
  @FXML private TextField nodeField;
  @FXML private TextField employeeIDField;
  @FXML private TextField userField;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private ComboBox typeChoice;
  @FXML private ComboBox objectChoice;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML
  public void reset() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    statusChoice.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    userField.clear();
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || statusChoice.getValue().equals("")
        || objectChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      requestList.clear();
      requestList.add("Audio/Visual Request for: " + objectChoice.getValue());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("Audio/Visual");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeField.getText());
      fields.add(employeeIDField.getText());
      fields.add(userField.getText());
      fields.add(statusChoice.getValue().toString());
      fields.add(objectChoice.getValue().toString().substring(0,3));
      req.placeRequest(fields);

      nodeField.clear();
      employeeIDField.clear();
      userField.clear();
      statusChoice.valueProperty().setValue(null);
      typeChoice.valueProperty().setValue("Accessibility Object Type");
      objectChoice.valueProperty().setValue("");
      userField.clear();
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
    backgroundIMG.maxWidth(736);
    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty().divide(2));
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> accessibilityType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    accessibilityType.add("Accessibility Object Type");
    accessibilityType.add("Deaf/Hard of Hearing");
    accessibilityType.add("Blind/Visually Impaired");
    typeChoice.getItems().addAll(accessibilityType);
    typeChoice.setValue("Accessibility Object Type");
    objectChoice.getItems().add("");
    objectChoice.setValue("");
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
    StageManager.getInstance().setHomeScreen();
  }
}
