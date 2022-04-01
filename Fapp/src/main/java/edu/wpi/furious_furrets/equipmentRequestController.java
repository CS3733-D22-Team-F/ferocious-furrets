package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.StageManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controll for equipment requests
 *
 * @see Initializable
 */
public class equipmentRequestController implements Initializable {

  // TODO remove
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private TextField patientNameTextField;
  @FXML private TextField patientNumber;
  @FXML private ChoiceBox<String> patientGender;
  @FXML private ChoiceBox<String> providerType;
  @FXML private TextField item1;
  @FXML private TextField item2;
  @FXML private TextField item3;
  @FXML private TextArea medField;
  @FXML private TextField requesterSig;
  @FXML private DatePicker requestDate;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private Button backButton;
  @FXML private ChoiceBox statueChoice;

  /** genderTypes array to populate the patient gender choice box */
  private String[] genderTypes = {"Male", "Female", "Non-binary", "Prefer not to say"};

  /** providerTypes array to populate the patient gender choice box */
  private String[] providerTypes = {"Doctor", "Nurse", "BHW Staff", "Medical Student"};

  /**
   * RETURN HOME FUNCTION FOR THE BACK BUTTON
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void returnHome(ActionEvent event) throws IOException {

    StageManager.getInstance().setHomeScreen();
  }

  /** clear input fields */
  @FXML
  void resetFunction() {
    patientNameTextField.clear();
    patientNumber.clear();
    patientGender.valueProperty().setValue(null);
    providerType.valueProperty().setValue(null);
    item1.clear();
    item2.clear();
    item3.clear();
    medField.clear();
    requesterSig.clear();
    requestDate.valueProperty().setValue(null);
  }

  /** TODO ? */
  @FXML
  void submitFunction() {}

  /** TODO ? */
  public equipmentRequestController() {}

  /**
   * submits the inputs fields into an arraylist
   *
   * @return ArrayList
   */
  @FXML
  public ArrayList<Object> submit() {
    String patientName = patientNameTextField.getText();
    String patientNumberText = patientNumber.getText();
    String patientGenderText = patientGender.getValue();
    String providerTypeText = providerType.getValue();
    String item1Text = item1.getText();
    String item2Text = item2.getText();
    String item3Text = item3.getText();
    String medicalTextArea = medField.getText();
    String requestSignatureText = requesterSig.getText();
    String dateText = String.valueOf(requestDate.getValue());

    ArrayList<Object> requestList = new ArrayList<>();

    System.out.println(patientName);
    System.out.println(patientNumberText);
    System.out.println(patientGenderText);
    System.out.println(providerTypeText);
    System.out.println(item1Text);
    System.out.println(item2Text);
    System.out.println(item3Text);
    System.out.println(medicalTextArea);
    System.out.println(requestSignatureText);
    System.out.println(dateText);

    requestList.clear();
    requestList.add("Equipment Request for patient " + patientName);
    requestList.add("Assigned Doctor: " + requestSignatureText);
    requestList.add("Status: " + statueChoice.getValue());
    serviceRequestStorage.addToArrayList(requestList);

    return null;
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    patientGender.getItems().addAll(genderTypes);
    providerType.getItems().addAll(providerTypes);
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    statueChoice.getItems().addAll(temp);
    statueChoice.setValue("");
  }
}
