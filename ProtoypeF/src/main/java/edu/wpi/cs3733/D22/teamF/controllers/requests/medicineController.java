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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class medicineController extends PageController
    implements Initializable, IRequestController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private JFXComboBox nodeField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private TextField typeOfMed;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private TextField prescribingDoctor;
  @FXML private TextField dosage;
  @FXML private ComboBox units;
  @FXML private ComboBox units2;
  @FXML private TextField totalAmount;
  @FXML private TextField pharmacyAddress;
  @FXML private AnchorPane masterPane;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  @FXML
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeOfMed.clear();
    statusChoice.valueProperty().setValue(null);
    // typeChoice.valueProperty().setValue(null);
    prescribingDoctor.clear();
    dosage.clear();
    units.valueProperty().setValue(null);
    units2.valueProperty().setValue(null);
    totalAmount.clear();
    pharmacyAddress.clear();
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeOfMed.getText().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
    } else {
      requestList.clear();
      // requestList.add("Medicine Request for: " + typeChoice.getValue());
      requestList.add("Assigned Doctor: " + userField.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue());
      serviceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("Medicine");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeOfMed.getText());
      fields.add(prescribingDoctor.getText());
      String catDosage = dosage.getText() + units.getValue().toString();
      fields.add(catDosage);
      String catTotalAmount = totalAmount.getText() + units.getValue().toString();
      fields.add(catTotalAmount);
      fields.add(pharmacyAddress.getText());
      req.placeRequest(fields);

      reset();
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
    RequestSystem req = new RequestSystem("Medicine");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  public String generateReqID() throws SQLException {
    String nNodeType = typeOfMed.getText().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);
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
    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Object> medicineType = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("processing");
    statusDrop.add("done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    ArrayList<Object> unitMeasurements = new ArrayList<>();
    unitMeasurements.add("g");
    unitMeasurements.add("mg");
    unitMeasurements.add("mcg");
    unitMeasurements.add("mL");
    units.getItems().addAll(unitMeasurements);
    units2.getItems().addAll(unitMeasurements);
    units.setValue("mg");
    units2.getItems().addAll(unitMeasurements);
    units2.setValue("mg");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setLandingScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
