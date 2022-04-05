package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.entities.request.deliveryRequest.medicineDeliveryRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class medicineController extends returnHomePage implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private TextField nodeField;
  @FXML private TextField employeeIDField;
  @FXML private TextField userField;
  @FXML private TextField typeOfMed;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private ComboBox typeChoice;

  @FXML
  void resetFunction() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeOfMed.clear();
    statusChoice.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    userField.clear();
  }

  @FXML
  public medicineDeliveryRequest submit() {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeOfMed.getText().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
      return null;
    } else {
      requestList.clear();
      requestList.add("Medicine Request for: " + typeChoice.getValue());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      medicineDeliveryRequest medicineDeliveryRequest =
          new medicineDeliveryRequest(
              userField.getText(),
              Integer.parseInt(employeeIDField.getText()),
              nodeField.getText(),
              statusChoice.getValue().toString(),
              typeOfMed.getText(),
              null,
              null,
              null); // TODO
      nodeField.clear();
      employeeIDField.clear();
      userField.clear();
      typeOfMed.clear();
      statusChoice.valueProperty().setValue(null);
      typeChoice.valueProperty().setValue(null);
      userField.clear();

      return medicineDeliveryRequest;
    }
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> medicineType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    medicineType.add("Steroids");
    medicineType.add("Anti-inflammatory");
    medicineType.add("Pain-Killers");
    medicineType.add("Capsules");
    medicineType.add("Tablet");
    typeChoice.getItems().addAll(medicineType);
  }
}
