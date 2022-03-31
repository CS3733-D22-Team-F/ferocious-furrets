package edu.wpi.furious_furrets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class medicineController extends returnHomePage implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private Button newMedRequest;
  @FXML private ChoiceBox locations;
  @FXML private ChoiceBox medRequestStatus;
  @FXML private Button cancelRequest;
  @FXML private Button resetMedRequest;
  @FXML private Button submitButton;
  @FXML private TextField patientName; //
  @FXML private TextField patientRoom; //
  @FXML private TextField reqMedication; //
  @FXML private TextField reqDosage; //
  @FXML private TextField floorNum;
  @FXML private TextField employeeID;
  ArrayList medRequestList = new ArrayList<>();
  ArrayList<Object> requestList = new ArrayList<>();

  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> locationDrop = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    medRequestStatus.getItems().addAll(statusDrop);
    medRequestStatus.setValue("");
    locationDrop.add("Patient Room - PATI");
    locationDrop.add("Equipment Storage Room - STOR");
    locationDrop.add("Dirty Equipment - DIRT");
    locationDrop.add("Hallway - HALL");
    locationDrop.add("elevator - ELEV");
    locationDrop.add("restroom - REST");
    locationDrop.add("Staircase - STAI");
    locationDrop.add("Medical Departments and Waiting Rooms - DEPT");
    locationDrop.add("Labs, Imaging, Testing - LABS");
    locationDrop.add("Information and Security Desks - INFO");
    locationDrop.add("Conference Room - CONF");
    locationDrop.add("Exit or Entrance - EXIT");
    locationDrop.add("Shops, Food, Payphone, etc. - RETL");
    locationDrop.add("Hospital Non-Medical Services - SERV");
    locations.getItems().addAll(locationDrop);
    locations.setValue("");
  }

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
    employeeID.clear(); //
    floorNum.clear(); //
  }

  @FXML
  void submitMedRequest(ActionEvent event) {
    String patientNameText = patientName.getText();
    String patientRoomText = patientRoom.getText();
    String medication = reqMedication.getText();
    String dosage = reqDosage.getText();
    String floorNumText = floorNum.getText();
    String employee = employeeID.getText();

    medRequestList.add(patientNameText);
    medRequestList.add(patientRoomText);
    medRequestList.add(medication);
    medRequestList.add(dosage);
    medRequestList.add(floorNumText);
    medRequestList.add(employee);
    medRequestList.add(locations);

    // System.out.print(patientNameText);
    // System.out.print(patientRoomText);
    // System.out.print(medication);
    // System.out.print(dosage);
    // System.out.print(additional);
    // System.out.print(employee);
    // System.out.print(locations);

    requestList.clear();
    requestList.add("Medicine Request for patient: " + patientNameText);
    requestList.add("Room Number: " + patientRoomText);
    requestList.add("Assigned Doctor: " + employee);
    requestList.add("Status: " + medRequestStatus.getValue());
    requestList.add("Location: " + locations.getValue());
    requestList.add("Floor: " + floorNumText);
    serviceRequestStorage.addToArrayList(requestList);
    System.out.println(requestList);

    patientName.clear();
    patientRoom.clear();
    reqMedication.clear();
    patientRoom.clear();
    floorNum.clear();
    employeeID.clear();
    reqDosage.clear();
    medRequestStatus.setValue("");
    locations.setValue("");
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
