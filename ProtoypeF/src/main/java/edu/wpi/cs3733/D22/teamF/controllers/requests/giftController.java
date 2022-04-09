package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.returnHomePage;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/** Controller for gift scene */
public class giftController extends returnHomePage implements Initializable, IRequestController {

  private ArrayList<Object> itemList = new ArrayList<>();

  @FXML JFXCheckBox rose;
  @FXML JFXCheckBox teddyBear;
  @FXML JFXCheckBox giftCard;
  @FXML JFXCheckBox jasmine;
  @FXML JFXCheckBox chrys;

  @FXML JFXTextArea employeeField;
  @FXML JFXTextArea nodeField;
  @FXML JFXTextArea itemField;

  @FXML JFXButton submitButton;
  @FXML JFXButton previewButton;

  @FXML TextField employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML TextField assigned;

  @FXML JFXComboBox statusChoice;
  boolean roseB = false;
  boolean teddyB = false;
  boolean giftB = false;
  boolean jasmineB = false;
  boolean chrysB = false;
  String reqType = "";

  /**
   * init
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
  }

  /**
   * Functon for preview button
   *
   * @return boolean
   */
  public boolean previewButton() {
    ArrayList<Object> returnList = new ArrayList<>(); // List will be returned
    ArrayList<String> serviceList = new ArrayList<>(); // List will show in label
    ArrayList<Object> requestList = new ArrayList<>();
    // If any of the field is missing, pop up a notice
    if ((!roseB && !teddyB && !giftB && !jasmineB && !chrysB)
        || employeeID.getText().isEmpty()
        || nodeID.getText().isEmpty()
        || assigned.getText().isEmpty()
        || patientName.getText().isEmpty()) {
      System.out.println("please make a choice");
      return false;
    } else {
      if (roseB) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("rose");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Rose");
        reqType = "Rose: ";
      }
      if (teddyB) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Teddy Bear");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Teddy Bear");
        reqType = reqType + "Teddy Bear: ";
      }
      if (giftB) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Gift Card");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Gift Card");
        reqType = reqType + "Gift Card: ";
      }
      if (jasmineB) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Jasmine");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Jasmine");
        reqType = reqType + "Jasmine: ";
      }
      if (chrysB) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Chrysanthemum");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Chrysanthemum");
        reqType = reqType + "Chrysanthemum: ";
      }

      employeeField.setText(employeeID.getText()); // set employee ID
      nodeField.setText(nodeID.getText()); // set node ID
      itemField.setText(serviceList.toString());

      returnList.add("Gift Request");
      returnList.add(employeeID.getText());
      returnList.add(nodeID.getText());
      returnList.add(statusChoice.getValue());
      returnList.add(serviceList); // disable when log in process finished
      // returnList.add(doctorField.getText()); //enable when log in process finished

      submitButton.disableProperty().setValue(false);
      previewButton.disableProperty().setValue(true);
      itemList = returnList;

      requestList.add("Gift for patient " + patientName.getText());
      requestList.add("Requested by Employee: " + assigned.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      return true;
    }
  }

  public void reset() {}

  /**
   * submit the Arraylist that contains the items and doctor Return formula: ['Service Type',
   * 'Service1', 'Service2',..., 'Patient Name', 'Room Number', 'Doctor Name']
   *
   * @return giftDeliveryRequest
   */
  public void submit() {
    RequestSystem req = new RequestSystem("Gift");
    ArrayList<String> fields = new ArrayList<String>();

    req.placeRequest(fields);

    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    assigned.clear();
    employeeID.clear();
    nodeID.clear();
    statusChoice.valueProperty().set(null);
    patientName.clear();
    if ((rose != null) && roseB) {
      rose.fire();
    }
    if ((teddyBear != null) && teddyB) {
      teddyBear.fire();
    }
    if ((giftCard != null) && giftB) {
      giftCard.fire();
    }
    if ((jasmine != null) && jasmineB) {
      jasmine.fire();
    }
    if ((chrys != null) && chrysB) {
      chrys.fire();
    }
  }

  public void boxChangeRose() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    roseB = !roseB;
  }

  public void boxChangeTeddy() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    teddyB = !teddyB;
  }

  public void boxChangeGift() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    giftB = !giftB;
  }

  public void boxChangeJas() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    jasmineB = !jasmineB;
  }

  public void boxChangeChrys() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    chrysB = !chrysB;
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("giftRequestQueue.fxml");
  }

  public String generateReqID(int requestListLength) {
    String reqAbb = "GR";
    String giftID = "";
    if ((rose != null) && roseB) {
      giftID = giftID + "R01";
    }
    if ((teddyBear != null) && teddyB) {
      giftID = giftID + "TB01";
    }
    if ((giftCard != null) && giftB) {
      giftID = giftID + "GC01";
    }
    if ((jasmine != null) && jasmineB) {
      giftID = giftID + "J01";
    }
    if ((chrys != null) && chrysB) {
      giftID = giftID + "C01";
    }
    return reqAbb + giftID + (requestListLength + 1);
  }
}
