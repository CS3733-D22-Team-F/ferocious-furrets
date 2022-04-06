package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.scanRequest;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controller for scan scene
 *
 * @see returnHomePage
 * @see Initializable
 */
public class scanController extends returnHomePage implements Initializable {

  @FXML TextField nodeField;
  @FXML TextField employeeIDField;
  @FXML TextField userField;
  @FXML Button reset;

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
    temp1.add("CAT");
    temp1.add("xray");
    temp1.add("MRI");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("CAT");
  }

  public void reset() {
    nodeField.clear();
    employeeIDField.clear();
    userField.clear();
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
  }

  /**
   * Use Try/Catch when call this function submits a medical request using user inputs
   *
   * @return MedicalRequest object
   */
  public MedicalRequest submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    String reqID =
        generateReqID(
            DatabaseManager.getScanRequestDAO().getAllRequests().size(),
            typeChoice.getValue().toString(),
            nodeField.getText());
    String scanType = typeChoice.getValue().toString();
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getText().equals("")
        || userField.getText().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
      return null;
    } else {
      scanRequest newRequest =
          new scanRequest(
              reqID,
              nodeField.getText(),
              employeeIDField.getText(),
              userField.getText(),
              statusChoice.getValue().toString(),
              scanType); // TODO
      requestList.clear();
      requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
      requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue().toString());
      serviceRequestStorage.addToArrayList(requestList);
      DatabaseManager.getScanRequestDAO()
          .addRequest(
              newRequest.getReqID(),
              newRequest.getNodeID(),
              newRequest.getRequesterEmpID(),
              newRequest.getAssignedEmpID(),
              newRequest.getStatus(),
              newRequest.getScanType());
      return newRequest;
    }
  }

  /**
   * shows the queue scene
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

  public String generateReqID(int requestListLength, String scanType, String nodeID) {
    String reqAbb = "SR";
    String sAb = "";
    if (scanType.equals("CAT")) {
      sAb = "C";
    } else if (scanType.equals("xray")) {
      sAb = "X";
    } else if (scanType.equals("MRI")) {
      sAb = "M";
    }
    return reqAbb + sAb + (requestListLength + 1) + nodeID;
  }
}
