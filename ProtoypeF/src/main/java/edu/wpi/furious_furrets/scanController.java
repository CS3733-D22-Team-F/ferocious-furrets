package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;
import edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.catscanRequest;
import edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.mriScanRequest;
import edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.xrayScanRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** controller for scan scene */
public class scanController extends returnHomePage implements Initializable {

  @FXML TextField nodeField;
  @FXML TextField employeeIDField;
  @FXML TextField userField;
  @FXML Button reset;

  @FXML ComboBox typeChoice; // Lab Type Choice Box
  @FXML ComboBox statusChoice; // Status Choice Box

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("CAT");
    temp1.add("xray");
    temp1.add("MRI");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("CAT");
  }

  public void reset() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
  }

  /**
   * Use Try/Catch when call this function submits a medical request using user inputs
   *
   * @return MedicalRequest object
   */
  public MedicalRequest submit() {
    ArrayList<Object> requestList = new ArrayList<>();
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
      return null;
    } else {
      if (typeChoice.getValue().equals("CAT")) {
        catscanRequest newRequest =
            new catscanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statusChoice.getValue().toString(),
                "Scan",
                "",
                "",
                null); // TODO
        requestList.clear();
        requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statusChoice.getValue().toString());
        serviceRequestStorage.addToArrayList(requestList);
        return newRequest;
      } else if (typeChoice.getValue().equals("xray")) {
        xrayScanRequest newRequest =
            new xrayScanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statusChoice.getValue().toString(),
                "Scan",
                "",
                "",
                null); // TODO
        requestList.clear();
        requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statusChoice.getValue().toString());
        serviceRequestStorage.addToArrayList(requestList);
        return newRequest;
      } else {
        mriScanRequest newRequest =
            new mriScanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statusChoice.getValue().toString(),
                "Scan",
                "",
                "",
                null); // TODO
        requestList.clear();
        requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statusChoice.getValue().toString());
        serviceRequestStorage.addToArrayList(requestList);
        return newRequest;
      }
    }
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    Scene scene = SceneManager.getInstance().setScene("labRequestQueue.fxml");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
