package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import edu.wpi.furious_furrets.entities.request.deliveryRequest.giftDeliveryRequest;
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

/** Controller for gift scene */
public class giftController extends returnHomePage implements Initializable {

  private ArrayList<Object> itemList = new ArrayList<>();

  @FXML CheckBox rose;
  @FXML CheckBox teddyBear;
  @FXML CheckBox giftCard;
  @FXML CheckBox jasmine;
  @FXML CheckBox chrys;

  @FXML Label employeeField;
  @FXML Label doctorField;
  @FXML Label nodeField;
  @FXML Label itemField;

  @FXML Button submitButton;
  @FXML Button previewButton;

  @FXML TextField employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML TextField assigned;

  @FXML ChoiceBox<Object> statusChoice;
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
    if ((!rose.isSelected()
            && !teddyBear.isSelected()
            && !giftCard.isSelected()
            && !jasmine.isSelected()
            && !chrys.isSelected())
        || employeeID.getText().isEmpty()
        || nodeID.getText().isEmpty()
        || assigned.getText().isEmpty()
        || patientName.getText().isEmpty()) {
      System.out.println("please make a choice");
      return false;
    } else {
      if (rose.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("rose");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("rose");
        reqType = "Rose: ";
      }
      if (teddyBear.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Teddy Bear");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Teddy Bear");
        reqType = reqType + "Teddy Bear: ";
      }
      if (giftCard.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Gift Card");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Gift Card");
        reqType = reqType + "Gift Card: ";
      }
      if (jasmine.isSelected()) {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Jasmine");
        temp.add("1");
        returnList.add(temp);
        serviceList.add("Jasmine");
        reqType = reqType + "Jasmine: ";
      }
      if (chrys.isSelected()) {
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

  /**
   * submit the Arraylist that contains the items and doctor Return formula: ['Service Type',
   * 'Service1', 'Service2',..., 'Patient Name', 'Room Number', 'Doctor Name']
   *
   * @return giftDeliveryRequest
   */
  public giftDeliveryRequest submitButton() {
    giftDeliveryRequest giftRequest =
        new giftDeliveryRequest(
            assigned.getText(),
            Integer.parseInt(employeeID.getText()),
            nodeID.getText(),
            statusChoice.getValue().toString(),
            reqType,
            null,
            null);
    //    System.out.println(giftRequest.getAssign());
    //    System.out.println(giftRequest.getnID());
    //    System.out.println(giftRequest.getSts());
    //    System.out.println(giftRequest.getReqType());
    return giftRequest;
  }

  /** enable Preview and disable Submit when change choice */
  public void boxChange() {
    submitButton.disableProperty().setValue(true);
    previewButton.disableProperty().setValue(false);
    itemField.setText("empty");
    employeeField.setText("empty");
    nodeField.setText("empty");
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    Scene scene = SceneManager.getInstance().setScene("giftRequestQueue.fxml");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
