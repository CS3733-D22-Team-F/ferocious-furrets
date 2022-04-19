package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.physicalTherapyRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class physicalTherapyController extends PageController
    implements Initializable, IRequestController {

  @FXML JFXComboBox nodeField;
  @FXML JFXComboBox employeeIDField;
  @FXML JFXComboBox userField;
  @FXML JFXComboBox typeChoice; // Lab Type Choice Box
  @FXML TextField durationTime; // duration time field in minutes
  @FXML TextArea notes;
  @FXML JFXComboBox statusChoice; // Status Choice Box
  @FXML BorderPane masterPane;
  @FXML JFXTreeTableView treeTable;
  @FXML Pane tablePane;

  private String typeChoiceS;
  private String durationTimeS;
  private String notesS;

  TreeItem<physicalTherapyRequest> treeRoot =
          new TreeItem<>(new physicalTherapyRequest(typeChoiceS, durationTimeS, notesS));

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("Electric Stim");
    temp1.add("Stretching");
    temp1.add("Heating");
    temp1.add("Ice");
    temp1.add("Resistance Therapy");
    typeChoice.getItems().addAll(temp1);
    typeChoice.setValue("");

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);

    try {
      startTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void startTable() throws SQLException {

    clearTable();

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM PTREQUEST");
    ArrayList<physicalTherapyRequest> secReqs = new ArrayList<physicalTherapyRequest>();
    physicalTherapyRequest sr;

    while (rset.next()) {
      sr =
          new physicalTherapyRequest(
              rset.getString("TYPE"), rset.getString("DURATION"), rset.getString("NOTES"));
      secReqs.add(sr);
    }

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (physicalTherapyRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(physicalTherapyRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<physicalTherapyRequest, String> therapyTypeColumn =
        new TreeTableColumn<>("Therapy Time");
    therapyTypeColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<physicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getTreatmentType()));

    TreeTableColumn<physicalTherapyRequest, String> durationColumn =
        new TreeTableColumn<>("Duration");
    durationColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<physicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getDuration()));

    TreeTableColumn<physicalTherapyRequest, String> notesColumn = new TreeTableColumn<>("Notes");
    notesColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<physicalTherapyRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNotes()));

    TreeTableView<physicalTherapyRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(therapyTypeColumn, durationColumn, notesColumn);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    therapyTypeColumn.minWidthProperty().bind(tablePane.widthProperty().divide(4));
    durationColumn.minWidthProperty().bind(tablePane.widthProperty().divide(4));
    notesColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  @Override
  public void submit() throws SQLException {
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || typeChoice.getValue().equals("")
        || statusChoice.getValue().equals("")
        || durationTime.getText().equals("")
        || notes.getText().equals("")) {
      System.out.println("There are still blank fields");
    } else {
      RequestSystem req = new RequestSystem("PT");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(typeChoice.getValue().toString());
      fields.add(durationTime.getText());
      fields.add(notes.getText());
      req.placeRequest(fields);
    }
    reset();

    startTable();
  }

  @Override
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    typeChoice.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null); // Status Choice Box
    durationTime.clear();
    notes.clear();
  }

  /* helper */

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

  /** clears the table in the request page */
  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
