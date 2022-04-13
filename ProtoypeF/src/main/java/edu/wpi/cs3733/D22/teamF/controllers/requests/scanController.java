package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
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

/**
 * controller for scan scene
 *
 * @see Initializable
 */
public class scanController extends PageController implements Initializable, IRequestController {

  @FXML AnchorPane masterPane;
  @FXML TextField nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML Button reset;
  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML ComboBox typeChoice; // Lab Type Choice Box
  @FXML ComboBox statusChoice; // Status Choice Box

  public scanController() {}

  public scanController(ContextMenu c_menu, MenuBar m_menu) {
    super(c_menu, m_menu);
  }

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
    temp1.add("CAT");
    temp1.add("xray");
    temp1.add("MRI");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("CAT");

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
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");
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

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  public void reset() {
    nodeField.clear();
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  void showSceneQueue(ActionEvent event) throws IOException {
    switchScene("labRequestQueue.fxml");
  }

  /**
   * Use Try/Catch when call this function submits a medical request using user inputs
   *
   * @return MedicalRequest object
   */
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    //    String reqID =
    //        generateReqID(
    //            DatabaseManager.getScanRequestDAO().getAllRequests().size(),
    //            typeChoice.getValue().toString(),
    //            nodeField.getText());
    String scanType = typeChoice.getValue().toString();
    // If any of the field is missing, pop up a notice
    if (nodeField.getText().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      RequestSystem req = new RequestSystem("Scan");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeField.getText());
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeChoice.getValue().toString());
      req.placeRequest(fields);

      requestList.clear();
      requestList.add("Scan Request of type: " + typeChoice.getValue().toString());
      // requestList.add("Assigned Doctor: " + userField.getText());
      requestList.add("Status: " + statusChoice.getValue().toString());
      serviceRequestStorage.addToArrayList(requestList);
      this.reset();
    }
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

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Scan");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String generateReqID() throws SQLException {
    String nNodeType = typeChoice.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setLandingScreen();
  }
}
