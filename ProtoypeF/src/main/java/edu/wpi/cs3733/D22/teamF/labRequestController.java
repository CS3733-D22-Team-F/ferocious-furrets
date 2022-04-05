package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest.bloodLabRequest.bloodLabRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest.labRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest.urineLabRequest.urineLabRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    temp1.add("");
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
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
      return null;
    } else {
      if (typeChoice.getValue().equals("blood")) {
        bloodLabRequest newRequest =
            new bloodLabRequest(
                null,
                nodeField.getText(),
                employeeIDField.getText(),
                userField.getText(),
                statusChoice.getValue().toString(),
                "Medical",
                "Lab",
                "Blood");

        requestList.clear();
        requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statusChoice.getValue());
        serviceRequestStorage.addToArrayList(requestList);
        return newRequest;
      } else {
        urineLabRequest newRequest =
            new urineLabRequest(
                null,
                nodeField.getText(),
                employeeIDField.getText(),
                userField.getText(),
                statusChoice.getValue().toString(),
                "Medical",
                "Lab",
                "Urine");

        requestList.clear();
        requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
        requestList.add("Assigned Doctor: " + userField.getText());
        requestList.add("Status: " + statusChoice.getValue());
        serviceRequestStorage.addToArrayList(requestList);
        // TODO
        // DatabaseManager.getlrdao().addRequest(newRequest.getReqID());
        return newRequest;
      }
    }
  }

  @FXML
  public void reset() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
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
