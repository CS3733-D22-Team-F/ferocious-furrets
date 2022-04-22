package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.GiftDeliveryRequest;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

// import javax.swing.text.html.ImageView;

/** Controller for gift scene */
public class GiftResizedController extends PageController
    implements Initializable, IRequestController {
  @FXML Rectangle rectangle1;
  @FXML Rectangle rectangle2;
  @FXML BorderPane masterPane;
  @FXML Pane leftPane;
  @FXML ImageView logo;
  //  @FXML ImageView backgroundIMG;

  @FXML JFXComboBox employeeID;
  @FXML JFXComboBox nodeID;
  @FXML TextField patientName;
  @FXML JFXComboBox assigned;
  @FXML JFXComboBox statusChoice;
  @FXML JFXComboBox giftChoice;

  @FXML TreeTableView table;
  @FXML Pane tablePane;

  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;
  @FXML JFXButton queueButton;

  @FXML HBox leftHbox;

  private String requestID;
  private String nodeIDO;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  private String gift;

  TreeItem<GiftDeliveryRequest> treeRoot =
      new TreeItem<>(
          new GiftDeliveryRequest(requestID, nodeIDO, assignedEmpID, requesterEmpID, status, gift));

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    masterPane.setMinHeight(500);
    masterPane.setMinWidth(500);
    //    rectangle1.heightProperty().bind(masterPane.heightProperty());
    //    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    //    rectangle2.widthProperty().bind(masterPane.widthProperty().add(15).divide(2));
    //    logo.xProperty().bind(rectangle2.widthProperty().subtract(600));
    //    employeeID.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    employeeID.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    nodeID.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    nodeID.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    assigned.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    assigned.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    statusChoice.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    giftChoice.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    leftHbox.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    //    backgroundIMG.maxWidth(736);
    //    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
    //    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty().divide(2));

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

  /**
   * submit the Arraylist that contains the items and doctor Return formula: ['Service Type',
   * 'Service1', 'Service2',..., 'Patient Name', 'Room Number', 'Doctor Name']
   *
   * @return giftDeliveryRequest
   */
  public void submit() throws SQLException, IOException {
    RequestSystem req = new RequestSystem("Gift");
    ArrayList<String> fields = new ArrayList<String>();
    fields.add(generateReqID());
    fields.add(nodeIDFinder(nodeID.getValue().toString()));
    fields.add(employeeIDFinder(assigned.getValue().toString()));
    fields.add(employeeIDFinder(employeeID.getValue().toString()));
    fields.add(statusChoice.getValue().toString());
    if (giftChoice.getValue().toString().length() > 16)
      fields.add(giftChoice.getValue().toString().substring(0, 15));
    else fields.add(giftChoice.getValue().toString());
    req.placeRequest(fields);

    reset();

    startTable();
  }

  @Override
  public void reset() {
    assigned.valueProperty().setValue(null);
    employeeID.valueProperty().setValue(null);
    nodeID.valueProperty().setValue(null);
    statusChoice.valueProperty().set(null);
    giftChoice.valueProperty().set(null);
  }

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet giftRequestTable =
        DatabaseManager.getInstance().getGiftDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<GiftDeliveryRequest> secReqs = new ArrayList<GiftDeliveryRequest>();
    GiftDeliveryRequest er;
    String currentGiftReqID;

    while (giftRequestTable.next()) {
      currentGiftReqID = giftRequestTable.getString("reqID");
      //      System.out.println(currentGiftReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentGiftReqID)) {
          //          System.out.println("matched :)");
          er =
              new GiftDeliveryRequest(
                  giftRequestTable.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  giftRequestTable.getString(
                      "gift")); // ADD YOU UNIQUE FIELD TO THIS (MAKE SURE OBJECT PARAMETERS ARE
          // CORRECT TOO)
          secReqs.add(er);
          servRequest.close();
          break;
        }
      }
    }

    giftRequestTable.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (giftDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(giftDeliveryRequest));
            });

    TreeTableColumn<GiftDeliveryRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<GiftDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<GiftDeliveryRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<GiftDeliveryRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<GiftDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<GiftDeliveryRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                empIDToFirstName(param.getValue().getValue().getAssignedEmpID())
                    + " "
                    + empIDToLastName(param.getValue().getValue().getAssignedEmpID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID());
        });

    TreeTableColumn<GiftDeliveryRequest, String> giftCol = new TreeTableColumn<>("Gift");
    giftCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<GiftDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getGift()));

    TreeTableColumn<GiftDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<GiftDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<GiftDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(reqIDCol, nodeIDCol, assignedToCol, giftCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    giftCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  /* helper */

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
    String nNodeType = giftChoice.getValue().toString().substring(0, 3);
    int reqNum = 1;

    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
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
