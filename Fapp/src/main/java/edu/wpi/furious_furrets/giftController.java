package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.SceneManager;
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

public class giftController extends returnHomePage implements Initializable {

  private ArrayList<Object> itemList = new ArrayList<>();

  @FXML CheckBox blood;
  @FXML CheckBox urine;
  @FXML CheckBox CAT;
  @FXML CheckBox xray;
  @FXML CheckBox MRI;

  @FXML Label itemField;
  @FXML Label doctorField;
  @FXML Label nameField;
  @FXML Label roomField;

  @FXML Button submitButton;
  @FXML Button previewButton;

  @FXML TextField nameTextField;
  @FXML TextField roomTextField;
  @FXML TextField nurseTextField;

  @FXML ChoiceBox<Object> statueChoice;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    statueChoice.getItems().addAll(temp);
    statueChoice.setValue("");
  }

  public boolean requestLab() {
    ArrayList<Object> returnList = new ArrayList<>(); // List will be returned
    ArrayList<String> serviceList = new ArrayList<>(); // List will show in label
    ArrayList<Object> requestList = new ArrayList<>();
    // If any of the field is missing, pop up a notice
    if (!blood.isSelected()
            && !urine.isSelected()
            && !CAT.isSelected()
            && !xray.isSelected()
            && !MRI.isSelected()
        || nameTextField.getText().isEmpty()
        || roomTextField.getText().isEmpty()
        || nurseTextField.getText().isEmpty()
        || statueChoice.getValue().equals("")) {
      System.out.println("please make a choice");
      return false;
    } else {
      if (blood.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("blood");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("blood");
      }
      if (urine.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("urine");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("urine");
      }
      if (CAT.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("CAT");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("CAT");
      }
      if (xray.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("xray");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("xray");
      }
      if (MRI.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("MRI");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("MRI");
      }

      roomField.setText(roomTextField.getText()); // set room number
      nameField.setText(nameTextField.getText()); // set room number
      itemField.setText(serviceList.toString());

      returnList.add("Lab Request");
      returnList.add(nameField.getText());
      returnList.add(roomField.getText());
      returnList.add(statueChoice.getValue());
      returnList.add(nurseTextField.getText()); // disable when log in process finished
      // returnList.add(doctorField.getText()); //enable when log in process finished

      submitButton.disableProperty().setValue(false);
      previewButton.disableProperty().setValue(true);
      itemList = returnList;

      requestList.add("Gift for patient " + nameField.getText());
      requestList.add("Delivery Nurse: " + nurseTextField.getText());
      requestList.add("Status: " + statueChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      return true;
    }
  }

  // submit the Arraylist that contains the items and doctor
  // Return formula: ['Service Type', 'Service1', 'Service2',..., 'Patient Name', 'Room Number',
  // 'Doctor Name']
  public void submit() {
    receiveData(itemList);
  }

  public void receiveData(ArrayList<Object> list) {
    String doctor = (String) list.get(list.size() - 1); // doctor is the last item
    list.remove(list.size() - 1); // remove the doctor in list
    System.out.println("Doctor Name: " + doctor); // replace with CSV write code

    String statue = (String) list.get(list.size() - 1); // statue is the last item
    list.remove(list.size() - 1); // remove the statue in list
    System.out.println("Statue: " + statue); // replace with CSV write code

    String room = (String) list.get(list.size() - 1); // room is the last item
    list.remove(list.size() - 1); // remove the room in list
    System.out.println("Room Number: " + room); // replace with CSV write code

    String name = (String) list.get(list.size() - 1); // name is the last item
    list.remove(list.size() - 1); // remove the name in list
    System.out.println("Patient Name: " + name); // replace with CSV write code

    String serviceType = (String) list.get(list.size() - 1); // service type is the first item
    list.remove(list.size() - 1); // remove the service type from the list
    System.out.println("Type: " + serviceType); // replace with CSV write code

    // Everything remaining is service detail
    for (Object s : list) {
      ArrayList temp = (ArrayList) s;
      System.out.println("Service: " + temp.get(0));
      System.out.println("Quantity: " + temp.get(1)); // replace with CSV write code
    }
  }

  // enable Preview and disable Submit when change choice
  public void boxChange() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("empty");
    nameField.setText("empty");
    roomField.setText("empty");
  }

  public void showQueueScene(ActionEvent event) throws IOException {
    Scene scene = SceneManager.getInstance().setScene("giftRequestQueue.fxml");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
