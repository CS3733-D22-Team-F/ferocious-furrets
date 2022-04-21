package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.ServiceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.SecurityRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SecurityController extends PageController
    implements Initializable, IRequestController {
  private Stage stage = SceneManager.getInstance().getStage();
  private Scene scene;
  private Parent root;

  @FXML private JFXComboBox nodeField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private ComboBox securityNeeds;
  @FXML private JFXComboBox urgency;
  @FXML private JFXComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML BorderPane masterPane;
  @FXML Rectangle rectangle1;
  @FXML Rectangle bottomRect;
  @FXML JFXTreeTableView treeTable;
  @FXML Pane tablePane;
  //  @FXML TreeTableView<securityRequest> treeTable;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  private String requestID;
  private String urg;
  private String needs;

  @FXML
  public void reset() {
    nodeField.valueProperty().setValue(null);
    employeeIDField.valueProperty().setValue(null);
    userField.valueProperty().setValue(null);
    securityNeeds.valueProperty().setValue(null);
    urgency.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
  }

  @FXML
  public void submit() throws SQLException {
    ArrayList<Object> requestList = new ArrayList<>();
    if (nodeField.getValue().toString().equals("")
        || employeeIDField.getValue().toString().equals("")
        || userField.getValue().toString().equals("")
        || securityNeeds.getValue().toString().equals("")
        || urgency.getValue().toString().equals("")
        || statusChoice.getValue().equals("")) {
      System.out.println("There are still blank field");
    } else {
      requestList.clear();
      requestList.add("Assigned Security Personnel: " + userField.getValue().toString());
      requestList.add("Status: " + statusChoice.getValue().toString());
      ServiceRequestStorage.addToArrayList(requestList);
      RequestSystem req = new RequestSystem("Security");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeField.getValue().toString()));
      fields.add(employeeIDFinder(employeeIDField.getValue().toString()));
      fields.add(employeeIDFinder(userField.getValue().toString()));
      fields.add(statusChoice.getValue().toString());
      fields.add(securityNeeds.getValue().toString());
      fields.add(urgency.getValue().toString());
      req.placeRequest(fields);

      reset();
      startTable();
    }
  }

  public String generateReqID() throws SQLException {
    String nNodeType = securityNeeds.getValue().toString().substring(0, 3);
    System.out.println(securityNeeds.getValue().toString());
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  public void resolveRequest() throws SQLException {
    RequestSystem req = new RequestSystem("Security");
    req.resolveRequest(reqID.getText());
    reqID.clear();
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {

    masterPane.setMinHeight(500);
    masterPane.setMinWidth(500);

    //        treeTable.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    //        treeTable.minHeightProperty().bind(masterPane.heightProperty());

    rectangle1.heightProperty().bind(masterPane.heightProperty());
    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    //    bottomRect.widthProperty().bind(masterPane.widthProperty().divide(2));

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);
    userField.getItems().addAll(employees);
    employeeIDField.setValue("");
    userField.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);
    nodeField.setValue("");

    ArrayList<String> urgencies = new ArrayList<>();
    urgencies.add("Immediate");
    urgencies.add("5-10 minutes");
    urgencies.add("30 minutes to an hour");
    urgencies.add("Pending ambulance arrival");
    urgency.getItems().addAll(urgencies);
    urgency.setValue("");

    ArrayList<String> differentNeeds = new ArrayList<>();
    differentNeeds.add("ARR - Arrest");
    differentNeeds.add("RST - Restraint");
    differentNeeds.add("BPR - Bystander/Patient removal");
    differentNeeds.add("PTW - Patient watch");
    differentNeeds.add("PTG - Patient guard");
    securityNeeds.getItems().addAll(differentNeeds);
    securityNeeds.setValue("");

    ArrayList<Object> statusDrop = new ArrayList<>();
    statusDrop.add("");
    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);
    statusChoice.setValue("");
    try {
      startTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    //        StageManager.getInstance().setLandingScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /////////////////////////////////////
  ////////////////////////////////////
  ///////////////////////////////////

  TreeItem<SecurityRequest> treeRoot = new TreeItem<>(new SecurityRequest(requestID, urg, needs));

  public void startTable() throws SQLException {

    clearTable();

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM securityRequest");
    ArrayList<SecurityRequest> secReqs = new ArrayList<SecurityRequest>();
    SecurityRequest sr;

    while (rset.next()) {
      sr =
          new SecurityRequest(
              rset.getString("reqID"), rset.getString("urgency"), rset.getString("needs"));
      secReqs.add(sr);
    }

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (securityRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(securityRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<SecurityRequest, String> needsColumn = new TreeTableColumn<>("Needs");
    needsColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SecurityRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNeeds()));

    TreeTableColumn<SecurityRequest, String> urgencyColumn = new TreeTableColumn<>("Urgency");
    urgencyColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SecurityRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getUrgency()));

    TreeTableView<SecurityRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(needsColumn, urgencyColumn);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    needsColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
    urgencyColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }
}