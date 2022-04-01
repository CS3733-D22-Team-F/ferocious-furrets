package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.entitites.request.deliveryRequest.equipmentDeliveryRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class equipmentRequestController extends returnHomePage implements Initializable {
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

  @FXML private TextField nodeField;
  @FXML private TextField employeeIDField;
  @FXML private TextField userField;
  @FXML private ChoiceBox typeChoice;
  @FXML private ChoiceBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private Button backButton;
  @FXML private ChoiceBox statueChoice;

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
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    userField.clear();
  }

  /** TODO ? */
  @FXML
  public equipmentDeliveryRequest submit() {
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
      return null;
    } else {
      equipmentDeliveryRequest equipServiceRequest =
          new equipmentDeliveryRequest(
              userField.getText(),
              Integer.parseInt(employeeIDField.getText()),
              nodeField.getText(),
              statusChoice.getValue().toString(),
              typeChoice.getValue().toString(),
              null,
              null);
      nodeField.clear();
      employeeIDField.clear();
      userField.clear();
      typeChoice.valueProperty().setValue(null);
      statusChoice.valueProperty().setValue(null);
      userField.clear();
      return equipServiceRequest;
    }

  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> equipmentType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    equipmentType.add("Respirator");
    equipmentType.add("Defibrillator");
    equipmentType.add("Ventilator");
    equipmentType.add("IV");
    equipmentType.add("EKG");
    typeChoice.getItems().addAll(equipmentType);
  }
}
