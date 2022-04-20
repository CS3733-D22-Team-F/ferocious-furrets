package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.facilitiesRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

// import javax.swing.text.html.ImageView;

/** Controller for facilities scene */
public class facilitiesController extends PageController
    implements Initializable, IRequestController {
  @FXML Rectangle rectangle1;
  @FXML Rectangle rectangle2;
  @FXML BorderPane masterPane;
  @FXML Pane leftPane;
  @FXML ImageView logo;
  @FXML ImageView backgroundIMG;

  @FXML JFXComboBox employeeID;
  @FXML JFXComboBox nodeID;
  @FXML TextField patientName;
  @FXML JFXComboBox assigned;
  @FXML JFXComboBox statusChoice;
  @FXML JFXComboBox requestType;

  @FXML JFXButton submitButton;
  @FXML JFXButton homeButton;
  @FXML JFXButton queueButton;
  @FXML JFXButton resetButton;

  @FXML HBox leftHbox;

  @FXML Pane tablePane;
  @FXML JFXTreeTableView table;

  private String requestID;
  private String nodeIDO;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String accessObject;

  private TreeItem<facilitiesRequest> treeRoot =
      new TreeItem<>(
          new facilitiesRequest(
              requestID, nodeIDO, assignedEmpID, requesterEmpID, status, accessObject));

  public void initialize(URL location, ResourceBundle resources) {
    // this.makeMenuBar(masterPane);

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("");
    temp1.add("Hazardous Waste Removal");
    temp1.add("General Waste Removal");
    temp1.add("Equipment Sterilization");
    temp1.add("Repair Request");
    temp1.add("Other Facilities Request");
    requestType.getItems().addAll(temp1);
    requestType.setValue("");

    ArrayList<Object> employees = employeeNames();
    assigned.getItems().addAll(employees);
    employeeID.getItems().addAll(employees);
    assigned.setValue("");
    employeeID.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeID.getItems().addAll(locations);

    try {
      startTable();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /** @return facilitiesRequest */
  public void submit() throws SQLException, IOException {
    RequestSystem req = new RequestSystem("Facilities");
    ArrayList<String> fields = new ArrayList<String>();
    fields.add(generateReqID());
    fields.add(nodeIDFinder(nodeID.getValue().toString()));
    fields.add(employeeIDFinder(assigned.getValue().toString()));
    fields.add(employeeIDFinder(employeeID.getValue().toString()));
    fields.add(statusChoice.getValue().toString());
    if (requestType.getValue().toString().length() > 16)
      fields.add(requestType.getValue().toString().substring(0, 15));
    else fields.add(requestType.getValue().toString());
    System.out.println(fields);
    req.placeRequest(fields);

    startTable();

    reset();
  }

  /** Default facilitiesController constructor */
  public facilitiesController() {}

  @Override
  public void reset() {
    assigned.valueProperty().setValue(null);
    employeeID.valueProperty().setValue(null);
    nodeID.valueProperty().setValue(null);
    statusChoice.valueProperty().set(null);
    requestType.valueProperty().set(null);
  }

  @Override
  public void startTable() throws SQLException, IOException {
    clearTable();

    ResultSet facilitiesRequestList =
        DatabaseManager.getFacilitiesDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<facilitiesRequest> secReqs = new ArrayList<>();
    facilitiesRequest er;
    String currentFacilityReq;

    while (facilitiesRequestList.next()) {
      currentFacilityReq = facilitiesRequestList.getString("reqID");
      System.out.println(currentFacilityReq);
      servRequest = DatabaseManager.getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentFacilityReq)) {
          er =
              new facilitiesRequest(
                  facilitiesRequestList.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  facilitiesRequestList.getString("accessObject"));
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    facilitiesRequestList.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (facilitiesRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(facilitiesRequest));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<facilitiesRequest, String> nodeIDCol = new TreeTableColumn<>("Location:");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<facilitiesRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<facilitiesRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To:");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<facilitiesRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableColumn<facilitiesRequest, String> requestedByCol =
        new TreeTableColumn<>("Requested By:");
    requestedByCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<facilitiesRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequesterEmpID()));

    TreeTableColumn<facilitiesRequest, String> statusCol = new TreeTableColumn<>("Status:");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<facilitiesRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableColumn<facilitiesRequest, String> accessObjectCol =
        new TreeTableColumn<>("accessObject:");
    accessObjectCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<facilitiesRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAccessObject()));

    TreeTableView<facilitiesRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(nodeIDCol, accessObjectCol, assignedToCol, requestedByCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    accessObjectCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    requestedByCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    // StageManager.getInstance().setDisplay("giftRequestQueue.fxml");
  }

  public String generateReqID() throws SQLException {
    String nNodeType = requestType.getValue().toString().substring(0, 3).toUpperCase(Locale.ROOT);
    int reqNum = 0;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    if (reqNum == 0) {
      reqNum = 1;
    }

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setLandingScreen();
  }
}
