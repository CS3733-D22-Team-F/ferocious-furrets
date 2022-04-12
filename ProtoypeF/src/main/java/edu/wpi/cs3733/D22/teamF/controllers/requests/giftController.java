package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

  @FXML JFXButton submitButton;
  @FXML JFXButton homeButton;
  @FXML JFXButton queueButton;

  @FXML TextField employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML TextField assigned;
  @FXML JFXComboBox statusChoice;
  @FXML JFXComboBox giftChoice;

  @FXML private AnchorPane masterPane;

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

    submitButton.disableProperty().setValue(false);

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("");
    temp1.add("TED - Teddy Bear");
    temp1.add("TEA - Tea");
    temp1.add("GWS - Get Well Soon Card");
    temp1.add("BLA - Blanket");
    temp1.add("TSH - Brigham and Women's T-Shirt");
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
  public void submit() throws SQLException {
    RequestSystem req = new RequestSystem("Gift");
    ArrayList<String> fields = new ArrayList<String>();
    fields.add(generateReqID());
    fields.add(nodeID.getText());
    fields.add(assigned.getText());
    fields.add(employeeID.getText());
    fields.add(statusChoice.getValue().toString());
    fields.add(giftChoice.getValue().toString());
    req.placeRequest(fields);

    employeeID.setText("Empty");
    nodeID.setText("Empty");
    assigned.clear();
    employeeID.clear();
    nodeID.clear();
    statusChoice.valueProperty().set(null);
    giftChoice.valueProperty().set(null);
    patientName.clear();
  }

  /*public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Gift");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }*/

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("giftRequestQueue.fxml");
  }

  public String generateReqID() throws SQLException {
    String nNodeType = giftChoice.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM GIFTREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
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
