package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest.bloodLabRequest;
import edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest.labRequest;
import edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest.urineLabRequest;
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

/**
 * lab request controller
 *
 * @see returnHomePage
 * @see Initializable
 */
public class labRequestController extends returnHomePage implements Initializable {

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
    temp.add("");
    temp.add("processing");
    temp.add("done");
    statueChoice.getItems().addAll(temp);
    statueChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("blood");
    temp1.add("urine");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("blood");
  }

  // Use Try/Catch when call this function

  /**
   * Use Try/Catch when call this function submits a labrequest from user inputs
   *
   * @return labRequest object
   */
  public labRequest submit() {
    ArrayList<Object> returnList = new ArrayList<>(); // List will be returned
    ArrayList<String> serviceList = new ArrayList<>(); // List will show in label
    ArrayList<Object> requestList = new ArrayList<>();
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statueChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
      return null;
    } else {
      if (typeChoice.getValue().equals("blood")) {
        bloodLabRequest newRequest =
            new bloodLabRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statueChoice.getValue().toString(),
                "Lab",
                "",
                "",
                typeChoice.getValue().toString(),
                null);

        requestList.clear();
        requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statueChoice.getValue());
        serviceRequestStorage.addToArrayList(requestList);
        return newRequest;
      } else {
        urineLabRequest newRequest =
            new urineLabRequest(
                userField.getText(),
                Integer.parseInt(employeeIDField.getText()),
                nodeField.getText(),
                statueChoice.getValue().toString(),
                "Lab",
                "",
                "",
                typeChoice.getValue().toString(),
                null); // TODO

        requestList.clear();
        requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statueChoice.getValue());
        serviceRequestStorage.addToArrayList(requestList);
        // TODO
        // DatabaseManager.getlrdao().addRequest(newRequest.getReqID());
        return newRequest;
      }
    }
  }

  /**
   * shows the queue scene for lab requests
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
