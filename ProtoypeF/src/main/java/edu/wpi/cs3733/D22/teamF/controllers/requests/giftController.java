package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/** Controller for gift scene */
public class giftController extends PageController implements Initializable, IRequestController {

  private ArrayList<Object> itemList = new ArrayList<>();

  @FXML private JFXComboBox giftChoice;

  @FXML JFXTextArea employeeField;
  @FXML JFXTextArea nodeField;
  @FXML JFXTextArea itemField;

  @FXML JFXButton submitButton;
  @FXML JFXButton homeButton;

  @FXML TextField employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML TextField assigned;

  @FXML private AnchorPane masterPane;

  @FXML JFXComboBox statusChoice;

  String reqType = "";

  /**
   * init
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("");
    temp1.add("Teddy Bear");
    temp1.add("Tea");
    temp1.add("Get Well Soon Card");
    temp1.add("Blanket");
    temp1.add("Brigham and Women's T-Shirt");
    giftChoice.getItems().addAll(temp1);
    giftChoice.setValue("");
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
    fields.add(generateReqID());
    fields.add(nodeField.getText());
    fields.add(assigned.getText());
    fields.add(employeeID.getText());
    fields.add(statusChoice.getValue().toString());
    fields.add(itemField.getText());
    req.placeRequest(fields);

    submitButton.disableProperty().setValue(true);
    itemField.setText("Empty");
    employeeField.setText("Empty");
    nodeField.setText("Empty");
    assigned.clear();
    employeeID.clear();
    nodeID.clear();
    statusChoice.valueProperty().set(null);
    patientName.clear();
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
    /*if ((rose != null) && roseB) {
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
    }*/
    return reqAbb + giftID + (requestListLength + 1);
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
