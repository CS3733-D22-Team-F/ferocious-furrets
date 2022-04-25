package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamD.API.SanitationReqAPI;
import edu.wpi.cs3733.D22.teamD.backend.Dao;
import edu.wpi.cs3733.D22.teamD.request.IRequest;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

// import javax.swing.text.html.ImageView;

/** Controller for facilities scene */
public class FacilitiesController extends PageController
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
  private IRequest.RequestStatus status;
  private String accessObject;

  private TreeItem<SanitationIRequest> treeRoot =
      new TreeItem<>(
          new SanitationIRequest(
              IRequest.Priority.MEDIUM,
              nodeIDO,
              assignedEmpID,
              requesterEmpID,
              accessObject,
              status));

  //  Timeline timeline =
  //      new Timeline(
  //          new KeyFrame(
  //              Duration.seconds(1),
  //              event -> {
  //                try {
  //                  startTable();
  //                } catch (SQLException e) {
  //                  e.printStackTrace();
  //                } catch (IOException e) {
  //                  e.printStackTrace();
  //                }
  //              }));

  /** Default facilitiesController constructor */
  public FacilitiesController() {}

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
    //
    //    timeline.setCycleCount(Timeline.INDEFINITE);
    //    timeline.play();
  }

  /** @return facilitiesRequest */
  public void submit() throws SQLException, IOException {
    //    RequestSystem req = new RequestSystem("Facilities");
    //    ArrayList<String> fields = new ArrayList<String>();
    //    fields.add(generateReqID());
    //    fields.add(nodeIDFinder(nodeID.getValue().toString()));
    //    fields.add(employeeIDFinder(assigned.getValue().toString()));
    //    fields.add(employeeIDFinder(employeeID.getValue().toString()));
    //    fields.add(statusChoice.getValue().toString());
    String type = "";
    if (requestType.getValue().toString().length() > 16)
      type = requestType.getValue().toString().substring(0, 15);
    else type = requestType.getValue().toString();
    //    System.out.println(fields);
    //    req.placeRequest(fields);

    IRequest.RequestStatus status = IRequest.RequestStatus.REQUESTED;
    if (statusChoice.getValue().toString().equals("Processing"))
      status = IRequest.RequestStatus.IN_PROGRESS;
    else status = IRequest.RequestStatus.COMPLETED;

    SanitationIRequest requestObj =
        new SanitationIRequest(
            IRequest.Priority.MEDIUM,
            nodeIDFinder(nodeID.getValue().toString()),
            employeeIDFinder(assigned.getValue().toString()),
            employeeIDFinder(employeeID.getValue().toString()),
            type,
            status);

    SanitationReqAPI reqAPI = new SanitationReqAPI();

    Dao<SanitationIRequest> request = new Dao(requestObj);
    request.add(requestObj);

    startTable();

    reset();
  }

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

    //    ResultSet facilitiesRequestList =
    //        DatabaseManager.getInstance().getFacilitiesDAO().get(); // CHANGE THIS TO CURRENT DAO
    //    ResultSet servRequest;
    //    ArrayList<FacilitiesRequest> secReqs = new ArrayList<>();
    //    FacilitiesRequest er;
    //    String currentFacilityReq;
    //
    //    while (facilitiesRequestList.next()) {
    //      currentFacilityReq = facilitiesRequestList.getString("reqID");
    //      //      System.out.println(currentFacilityReq);
    //      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
    //      while (servRequest.next()) {
    //        if (servRequest.getString("reqID").equals(currentFacilityReq)) {
    //          er =
    //              new FacilitiesRequest(
    //                  facilitiesRequestList.getString("reqID"),
    //                  servRequest.getString("nodeID"),
    //                  servRequest.getString("assignedEmployeeID"),
    //                  servRequest.getString("requesterEmployeeID"),
    //                  servRequest.getString("status"),
    //                  facilitiesRequestList.getString("accessObject"));
    //          secReqs.add(er);
    //          servRequest.close();
    //          break;
    //        }
    //      }
    //    }
    //
    //    facilitiesRequestList.close();

    SanitationReqAPI sanitationReqAPI = new SanitationReqAPI();
    List<SanitationIRequest> facilitiesRequestList = sanitationReqAPI.getAllRequests();

    treeRoot.setExpanded(true);
    facilitiesRequestList.stream()
        .forEach(
            (facilitiesRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(facilitiesRequest));
            });

    TreeTableColumn<SanitationIRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SanitationIRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<SanitationIRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SanitationIRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getRoomID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<SanitationIRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SanitationIRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                empIDToFirstName(param.getValue().getValue().getAssigneeID())
                    + " "
                    + empIDToLastName(param.getValue().getValue().getAssigneeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getAssigneeID());
        });

    TreeTableColumn<SanitationIRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SanitationIRequest, String> param) ->
            new ReadOnlyObjectWrapper(param.getValue().getValue().getCleanStatus()));

    TreeTableColumn<SanitationIRequest, String> accessObjectCol =
        new TreeTableColumn<>("Facilities Needed");
    accessObjectCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<SanitationIRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getSanitationType()));

    TreeTableView<SanitationIRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(reqIDCol, nodeIDCol, assignedToCol, accessObjectCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    accessObjectCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
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
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  public String nodeIDToName(String nID) throws SQLException {
    String cmd = String.format("SELECT longName FROM Locations WHERE nodeID = '%s'", nID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("longName");
    }
    return lName;
  }

  public String empIDToFirstName(String eID) throws SQLException {
    String cmd = String.format("SELECT firstName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String fName = "";
    while (rset.next()) {
      fName = rset.getString("firstName");
    }
    return fName;
  }

  public String empIDToLastName(String eID) throws SQLException {
    String cmd = String.format("SELECT lastName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("lastName");
    }
    return lName;
  }
}
