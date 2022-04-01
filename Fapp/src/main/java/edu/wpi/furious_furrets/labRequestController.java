package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.SceneManager;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.labRequest.bloodLabRequest;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.labRequest.labRequest;
import edu.wpi.furious_furrets.entitites.request.medicalRequest.labRequest.urineLabRequest;
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

public class labRequestController extends returnHomePage implements Initializable {

  @FXML TextField nodeField;
  @FXML TextField employeeIDField;
  @FXML TextField userField;

  @FXML ChoiceBox<Object> typeChoice; // Lab Type Choice Box
  @FXML ChoiceBox<Object> statueChoice; // Status Choice Box

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("blank");
    temp.add("processing");
    temp.add("done");
    statueChoice.getItems().addAll(temp);
    statueChoice.setValue("blank");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("blood");
    temp1.add("urine");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("blood");
  }

  // Use Try/Catch when call this function
  public labRequest submit() {
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statueChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
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
                typeChoice.getValue().toString());
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
                typeChoice.getValue().toString());
        return newRequest;
      }
    }
  }

  public void showQueueScene(ActionEvent event) throws IOException {
    Scene scene = SceneManager.getInstance().setScene("labRequestQueue.fxml");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
