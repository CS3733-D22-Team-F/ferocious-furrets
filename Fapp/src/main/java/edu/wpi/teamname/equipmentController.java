package edu.wpi.teamname;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class equipmentController implements Initializable {

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

  /*
   * genderTypes array to populate the patient gender choice box
   */
  private String[] genderTypes = {"Male", "Female", "Non-binary", "Prefer not to say"};

  /*
   * providerTypes array to populate the patient gender choice box
   */
  private String[] providerTypes = {"Doctor", "Nurse", "BHW Staff", "Medical Student"};

  /*
   * RETURN HOME FUNCTION FOR THE BACK BUTTON
   */

  @FXML
  void returnHome(ActionEvent event) throws IOException {
    root = FXMLLoader.load((getClass().getResource("homePage.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1280, 720);
    stage.setScene(scene);
    stage.show();
  }

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

  @FXML
  void submitFunction() {}

  public equipmentController() {}

  @FXML
  public ArrayList<String> submit() {

    ArrayList<String> equipmentRequest = new ArrayList<String>();

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

    equipmentRequest.add(patientName);
    equipmentRequest.add(patientNumberText);
    equipmentRequest.add(patientGenderText);
    equipmentRequest.add(providerTypeText);
    equipmentRequest.add(item1Text);
    equipmentRequest.add(item2Text);
    equipmentRequest.add(item3Text);
    equipmentRequest.add(medicalTextArea);
    equipmentRequest.add(requestSignatureText);
    equipmentRequest.add(dateText);

    System.out.println(equipmentRequest);
    return null;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    patientGender.getItems().addAll(genderTypes);
    providerType.getItems().addAll(providerTypes);
  }
}
