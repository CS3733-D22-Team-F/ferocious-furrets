package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.MedicalRequest;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.scanRequest.catscanRequest;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.scanRequest.mriScanRequest;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.scanRequest.xrayScanRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/** controller for scan scene */
public class scanController extends returnHomePage implements Initializable {

  @FXML TextField nodeField;
  @FXML TextField employeeIDField;
  @FXML TextField userField;

  @FXML ChoiceBox<Object> typeChoice; // Lab Type Choice Box
  @FXML ChoiceBox<Object> statueChoice; // Status Choice Box

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("blank");
    temp.add("processing");
    temp.add("done");
    statueChoice.getItems().addAll(temp);
    statueChoice.setValue("blank");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("CAT");
    temp1.add("xray");
    temp1.add("MRI");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("CAT");
  }

  /**
   * Use Try/Catch when call this function submits a medical request using user inputs
   *
   * @return MedicalRequest object
   */
  public MedicalRequest submit() {
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statueChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
      return null;
    } else {
      if (typeChoice.getValue().equals("CAT")) {
        catscanRequest newRequest =
            new catscanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statueChoice.getValue().toString(),
                "Scan",
                "",
                "");
        return newRequest;
      } else if (typeChoice.getValue().equals("xray")) {
        xrayScanRequest newRequest =
            new xrayScanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statueChoice.getValue().toString(),
                "Scan",
                "",
                "");
        return newRequest;
      } else {
        mriScanRequest newRequest =
            new mriScanRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statueChoice.getValue().toString(),
                "Scan",
                "",
                "");
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
