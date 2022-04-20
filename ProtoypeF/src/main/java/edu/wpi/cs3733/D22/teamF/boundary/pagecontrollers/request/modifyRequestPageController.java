package edu.wpi.cs3733.D22.teamF.boundary.pagecontrollers.request;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.pageControllers.requestListController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class modifyRequestPageController extends PageController implements Initializable {

  @FXML TextField requestTypeField;
  @FXML TextField requestIDField;
  @FXML JFXComboBox statusField;
  @FXML TextField ExtraField1;
  @FXML TextField ExtraField2;
  @FXML TextField ExtraField3;
  @FXML JFXComboBox nodeIDField;
  @FXML JFXComboBox assignedEmpIDField;
  @FXML JFXComboBox requestedEmpIDField;

  @FXML Label ExtraField1Label;
  @FXML Label ExtraField2Label;
  @FXML Label ExtraField3Label;

  private StringProperty reqTypeField;
  private StringProperty reqIDField;

  String reqID;
  String reqType;

  public modifyRequestPageController(StringProperty reqTypeField, StringProperty reqIDField) {
    this.reqTypeField = reqTypeField;
    System.out.println(reqTypeField.get());
    this.reqIDField = reqIDField;
    System.out.println(reqIDField.get());
  }

  public modifyRequestPageController() {}

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  public void initialize(URL location, ResourceBundle resources) {

    // TODO Get all the requestFields including requestType from previous scene......
    // TODO show, hide, edit ExtraField1-3 based on the requestTypeField
    if (reqTypeField.get() == null || reqIDField.get() == null) {
      System.out.println("Ya fucked it kid");
      this.reqID = requestListController.selectedID;
      this.reqType = requestListController.selectedType;
    } else {
      this.reqID = reqIDField.get();
      System.out.println(reqID);
      this.reqType = reqTypeField.get();
      System.out.println();
    }

    requestIDField.setText(reqID);
    requestTypeField.setText(reqType);
    ArrayList<String> statusChoices = new ArrayList<>();
    statusChoices.add("Processing");
    statusChoices.add("Done");
    statusField.getItems().addAll(statusChoices);

    setUpField();

    nodeIDField.getItems().addAll(locationNames());
    assignedEmpIDField.getItems().addAll(employeeNames());
    requestedEmpIDField.getItems().addAll(employeeNames());
  }

  /**
   * submit function for button in request controllers
   *
   * @throws SQLException
   * @throws IOException
   */
  public void submit() throws SQLException {
    RequestSystem req = new RequestSystem(requestTypeField.getText());
    ArrayList<String> fields = new ArrayList<>();
    String requestID = requestIDField.getText();
    fields.add(requestID);
    String nID = nodeIDFinder(nodeIDField.getValue().toString());
    fields.add(nID);
    String assignedID = employeeIDFinder(assignedEmpIDField.getValue().toString());
    fields.add(assignedID);
    String requestedID = employeeIDFinder(requestedEmpIDField.getValue().toString());
    fields.add(requestedID);
    String status = statusField.getValue().toString();
    fields.add(status);
    String extraField1 = ExtraField1.getText();
    fields.add(extraField1);
    String extraField2 = ExtraField2.getText();
    fields.add(extraField2);
    String extraField3 = ExtraField3.getText();
    fields.add(extraField3);
    req.modifyRequest(fields);
    reset();
    nodeIDField.getScene().getWindow().hide();
  }

  /** clears the fields in the request page */
  public void reset() {
    assignedEmpIDField.valueProperty().setValue(null);
    requestedEmpIDField.valueProperty().setValue(null);
    statusField.valueProperty().setValue(null);
    ExtraField1.clear();
    ExtraField2.clear();
    ExtraField3.clear();
    nodeIDField.valueProperty().setValue(null);
  }

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public ContextMenu makeContextMenu() {
    return null;
  }

  public void modifyRequestInDatabase(ArrayList<String> fields) {
    RequestSystem edit = new RequestSystem(requestTypeField.getText());
    try {
      edit.modifyRequest(fields); // todo Make modify functionality for all the request
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  void setUpField() {
    switch (reqType) {
      case ("Meal"):
        ExtraField1Label.setText("Meal:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("PT"):
        ExtraField1Label.setText("Type:");
        ExtraField2Label.setText("Duration:");
        ExtraField3Label.setText("Notes:");
        break;
      case ("Gift"):
        ExtraField1Label.setText("Gift:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Medicine"):
        break;
      case ("Lab"):
        ExtraField1Label.setText("Type:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Scan"):
        ExtraField1Label.setText("Type:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Equipment"):
        ExtraField1Label.setText("Equipment ID:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Maintenance"):
        ExtraField1Label.setText("Equipment ID:");
        ExtraField2Label.setText("Maintenance Type:");
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Audio/Visual"):
        ExtraField1Label.setText("Access Object:");
        ExtraField2Label.setText("Object Type:");
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("Security"):
        ExtraField1Label.setText("Needs:");
        ExtraField2Label.setText("Urgency:");
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case("Facilities"):
        ExtraField1Label.setText("Access Object:");
        ExtraField2Label.setVisible(false);
        ExtraField2.setVisible(false);
        ExtraField3Label.setVisible(false);
        ExtraField3.setVisible(false);
        break;
      case ("ExternalPatient"):
        break;
      default:
        System.out.println("Invalid Type");
        break;
    }
  }
}
