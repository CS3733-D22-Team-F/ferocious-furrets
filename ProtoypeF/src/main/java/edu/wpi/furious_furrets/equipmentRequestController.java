package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import edu.wpi.furious_furrets.entities.request.deliveryRequest.equipmentDeliveryRequest;
import java.net.URL;
import java.sql.SQLException;
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
  public equipmentDeliveryRequest submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
      return null;
    } else {
      requestList.clear();
      requestList.add("Equipment Request of type: " + typeChoice.getValue().toString());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      int reqID = (int) (Math.random() * 1000) + 1;
      equipmentDeliveryRequest equipServiceRequest =
          new equipmentDeliveryRequest(
              userField.getText(),
              Integer.parseInt(employeeIDField.getText()),
              nodeField.getText(),
              statusChoice.getValue().toString(),
              typeChoice.getValue().toString(),
              Integer.toString(reqID),
              null);
      nodeField.clear();
      employeeIDField.clear();
      userField.clear();
      typeChoice.valueProperty().setValue(null);
      statusChoice.valueProperty().setValue(null);
      userField.clear();

      DatabaseManager.getMdao()
          .addRequest(
              equipServiceRequest.getDeliveryID(),
              equipServiceRequest.getnID(),
              equipServiceRequest.getAssign(),
              equipServiceRequest.getSts());

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
    equipmentType.add("Respirator");
    equipmentType.add("Defibrillator");
    equipmentType.add("Ventilator");
    equipmentType.add("IV");
    equipmentType.add("EKG");
    typeChoice.getItems().addAll(equipmentType);
  }
}
