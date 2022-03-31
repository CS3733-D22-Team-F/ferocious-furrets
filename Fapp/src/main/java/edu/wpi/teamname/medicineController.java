package edu.wpi.teamname;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

public class medicineController extends returnHomePage {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private Button newMedRequest;
  @FXML private Button cancelRequest;
  @FXML private Button resetMedRequest;
  @FXML private Button submitButton;
  @FXML private TextField patientName;
  @FXML private TextField patientRoom;
  @FXML private TextField reqMedication;
  @FXML private TextField reqDosage;
  @FXML private TextField additionalInfo;
  @FXML private TextField employeeName;

  @FXML
  void showPopUp(ActionEvent event) throws IOException {
    try {
      root =
          FXMLLoader.load(
              getClass()
                  .getResource(
                      "../../../../../../../../../../../../../Downloads/medRequestPopup.fxml"));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void resetMedRequest(ActionEvent event) {
    patientName.clear(); //
    patientRoom.clear(); //
    reqDosage.clear(); //
    reqMedication.clear(); //
    employeeName.clear(); //
    additionalInfo.clear(); //
  }

  @FXML
  void submitMedRequest(ActionEvent event) {
    String patientNameText = patientName.getText();
    String patientRoomText = patientRoom.getText();
    String medication = reqMedication.getText();
    String dosage = patientRoom.getText();
    String additional = additionalInfo.getText();
    String employee = employeeName.getText();

    System.out.print(patientNameText);
    System.out.print(patientRoomText);
    System.out.print(medication);
    System.out.print(dosage);
    System.out.print(additional);
    System.out.print(employee);
  }

  @FXML
  private void cancelMedRequest(ActionEvent event) throws IOException {
    try {
      root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
