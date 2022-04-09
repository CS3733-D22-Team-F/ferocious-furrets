package edu.wpi.cs3733.D22.teamF.controllers.requests;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
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
public class labRequestController extends returnHomePage
    implements Initializable, IRequestController {

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
    typeChoice.setValue("");
  }

  // Use Try/Catch when call this function

  /**
   * Use Try/Catch when call this function submits a labrequest from user inputs
   *
   * @return labRequest object
   */
  public void submit() {
    ArrayList<Object> returnList = new ArrayList<>(); // List will be returned
    ArrayList<String> serviceList = new ArrayList<>(); // List will show in label
    ArrayList<Object> requestList = new ArrayList<>();
    String sampleType = null;
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {

      if (typeChoice.getValue().equals("blood")) {
        sampleType = "Blood";
      } else {
        sampleType = "Urine";
      }
      // int curLabReqSize = DatabaseManager.getLabRequestDAO().getAllRequests().size();
      // String reqID = generateReqID(curLabReqSize, sampleType, nodeField.getText());
      RequestSystem req = new RequestSystem("Lab");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(nodeField.getText());
      fields.add(employeeIDField.getText());
      fields.add(userField.getText());
      fields.add(statusChoice.getValue().toString());
      req.placeRequest(fields);
      requestList.clear();
      requestList.add("Lab Request of type: " + typeChoice.getValue().toString());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
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

  // TODO make a interaface for all controllers
  public String generateReqID(int requestListLength, String sampleType, String nodeID) {
    String reqAbb = "LR";
    String sAb = "";
    if (sampleType.equals("Urine")) {
      sAb = "U";
    } else if (sampleType.equals("Blood")) {
      sAb = "B";
    }
    return reqAbb + sAb + (requestListLength + 1) + nodeID;
  }
}
