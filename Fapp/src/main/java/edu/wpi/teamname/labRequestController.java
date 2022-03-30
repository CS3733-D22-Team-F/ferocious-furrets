package edu.wpi.teamname;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class labRequestController extends returnHomePage {

  private ArrayList<String> itemList = new ArrayList<String>();

  @FXML CheckBox blood;
  @FXML CheckBox urine;
  @FXML CheckBox CAT;
  @FXML CheckBox xray;
  @FXML CheckBox MRI;

  @FXML Label itemField;
  @FXML Label priceField;
  @FXML Label doctorField;
  @FXML Label nameField;
  @FXML Label roomField;

  @FXML Button submitButton;
  @FXML Button previewButton;

  @FXML TextField nameTextField;
  @FXML TextField roomTextField;

  public void requestLab() {
    ArrayList<String> returnList =
        new ArrayList<String>(); // List will be returned: items and doctor
    // If any of the field is missing, pop up a notice
    if (!blood.isSelected()
            && !urine.isSelected()
            && !CAT.isSelected()
            && !xray.isSelected()
            && !MRI.isSelected()
        || nameTextField.getText().isEmpty()
        || roomTextField.getText().isEmpty()) {
      System.out.println("please make a choice");
    } else {
      if (blood.isSelected()) {
        returnList.add("blood");
      }
      if (urine.isSelected()) {
        returnList.add("urine");
      }
      if (CAT.isSelected()) {
        returnList.add("CAT");
      }
      if (xray.isSelected()) {
        returnList.add("xray");
      }
      if (MRI.isSelected()) {
        returnList.add("MRI");
      }
      roomField.setText(roomTextField.getText()); // set room number
      nameField.setText(nameTextField.getText()); // set room number
      itemField.setText(returnList.toString());

      returnList.add("doctor"); // disable when log in process finished
      // returnList.add(doctorField.getText()); //enable when log in process finished

      submitButton.disableProperty().setValue(false);
      previewButton.disableProperty().setValue(true);
      itemList = returnList;
    }
  }

  // submit the Arraylist that contains the items and doctor
  public void submit() {
    for (String s : itemList) {
      System.out.println(s);
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
}
