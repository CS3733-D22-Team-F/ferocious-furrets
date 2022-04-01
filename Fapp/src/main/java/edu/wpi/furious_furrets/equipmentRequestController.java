package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.entitites.request.deliveryRequest.equipmentDeliveryRequest;
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

  @FXML private TextField nodeField;
  @FXML private TextField employeeIDField;
  @FXML private TextField userField;
  @FXML private ChoiceBox typeChoice;
  @FXML private ChoiceBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;

  @FXML
  void resetFunction() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    userField.clear();
  }

  @FXML
  public equipmentDeliveryRequest submit() {
    ArrayList<Object> requestList = new ArrayList<>();

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

      requestList.clear();
      requestList.add("Equipment Request of type: " + typeChoice.getValue());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);


      return equipServiceRequest;
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> equipmentType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    equipmentType.add("Patient Bed");
    equipmentType.add("Recliner");
    equipmentType.add("X-RAY Machine");
    equipmentType.add("Infusion Pump");
    typeChoice.getItems().addAll(equipmentType);
  }
}
