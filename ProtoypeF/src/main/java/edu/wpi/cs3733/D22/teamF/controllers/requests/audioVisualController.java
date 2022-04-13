package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
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
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
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
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue("");
    objectChoice.valueProperty().setValue("");
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getText().equals("")
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
      fields.add(nodeField.getText());
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(objectChoice.getValue().toString().substring(0, 31));
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
    accessibilityType.add("Deaf/Hard of Hearing");
    accessibilityType.add("Blind/Visually Impaired");
    typeChoice.getItems().addAll(accessibilityType);
    typeChoice.setValue("");
    objectChoice.getItems().add("");
    objectChoice.setValue("");
    ArrayList<Object> employees = new ArrayList<>();
    ResultSet rset = null;
    try {
      rset = DatabaseManager.runQuery("SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE");
      while (rset.next()) {
        String first = rset.getString("FIRSTNAME");
        String last = rset.getString("LASTNAME");
        String name = last + ", " + first;
        employees.add(name);
      }
      rset.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");
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

  /**
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }
}
