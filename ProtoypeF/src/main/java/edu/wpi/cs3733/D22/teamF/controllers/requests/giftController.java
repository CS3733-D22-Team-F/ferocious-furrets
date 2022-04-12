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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

// import javax.swing.text.html.ImageView;

/** Controller for gift scene */
public class giftController extends PageController implements Initializable, IRequestController {

  private ArrayList<Object> itemList = new ArrayList<>();

  @FXML JFXButton submitButton;
  @FXML JFXButton homeButton;
  @FXML JFXButton queueButton;

  @FXML JFXComboBox employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML JFXComboBox assigned;
  @FXML JFXComboBox statusChoice;
  @FXML JFXComboBox giftChoice;

  @FXML ImageView backgroundIMG;

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
    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty());
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
    temp1.add("TSH - Brigham and Womens T-Shirt");
    giftChoice.getItems().addAll(temp1);
    giftChoice.setValue("");

    ArrayList<Object> employees = new ArrayList<>();
    ResultSet rset = null;
    try {
      rset = DatabaseManager.runQuery("SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE");
      while (rset.next()) {
        String first = rset.getString("FIRSTNAME");
        String last = rset.getString("LASTNAME");
        String name = last + ", " + first;
        employees.add(name);
      }
      rset.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assigned.getItems().addAll(employees);
    employeeID.getItems().addAll(employees);
    assigned.setValue("");
    employeeID.setValue("");
  }

  public void reset() {
    assigned.valueProperty().setValue(null);
    employeeID.valueProperty().setValue(null);
    nodeID.clear();
    statusChoice.valueProperty().set(null);
    giftChoice.valueProperty().set(null);
    patientName.clear();
  }

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
    fields.add(employeeIDFinder(assigned.getValue().toString()));
    fields.add(employeeIDFinder(employeeID.getValue().toString()));
    fields.add(statusChoice.getValue().toString());
    fields.add(giftChoice.getValue().toString().substring(0, 15));
    req.placeRequest(fields);

    reset();
  }

  public String employeeIDFinder(String name) throws SQLException {
    String empID = "";
    String[] employeeName = name.split(",");
    String last = employeeName[0];
    String first = employeeName[1];
    last = last.strip();
    first = first.strip();
    String cmd =
        String.format(
            "SELECT EMPLOYEEID FROM EMPLOYEE WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'",
            first, last);
    ResultSet rset = DatabaseManager.runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
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
    System.out.println(nNodeType);
    int reqNum = 0;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    if (reqNum == 0) {
      reqNum = 1;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHome();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
