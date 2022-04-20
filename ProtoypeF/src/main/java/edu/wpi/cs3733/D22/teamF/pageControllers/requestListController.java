package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.requestTree;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * controller for request list
 *
 * @see Initializable
 */
public class requestListController extends PageController implements Initializable {

  @FXML JFXTreeTableView requestList;
  @FXML Pane tablePane;
  @FXML private AnchorPane masterPane;

  private String employee;
  private String nodeID;
  private String reqID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      startTable();
      // System.out.println("Help me please \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  TreeItem<requestTree> treeRoot =
      new TreeItem<>(new requestTree(reqID, nodeID, assignedEmpID, requesterEmpID, status));

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet rset =
        DatabaseManager.getRequestDAO().get(); // .runQuery("SELECT * FROM ServiceRequest");
    ArrayList<requestTree> reqs = new ArrayList<requestTree>();
    requestTree rt;

    while (rset.next()) {
      rt =
          new requestTree(
              rset.getString("reqID"),
              rset.getString("nodeID"),
              rset.getString("assignedEmployeeID"),
              rset.getString("requesterEmployeeID"),
              rset.getString("status"));
      reqs.add(rt);
    }

    treeRoot.setExpanded(true);
    reqs.stream()
        .forEach(
            (requestTree) -> {
              treeRoot.getChildren().add(new TreeItem<>(requestTree));
            });
    // final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<requestTree, String> reqIDColumn = new TreeTableColumn<>("Request ID");
    reqIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<requestTree, String> param) -> {
          System.out.println("req ID: " + param.getValue().getValue().getReqID());
          return new ReadOnlyStringWrapper(param.getValue().getValue().getReqID());
        });

    TreeTableColumn<requestTree, String> nodeIDColumn = new TreeTableColumn<>("Node ID");
    nodeIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<requestTree, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<requestTree, String> assignedEmpIDColumn = new TreeTableColumn<>("Employee ID");
    assignedEmpIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<requestTree, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    TreeTableView<requestTree> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(reqIDColumn, nodeIDColumn, assignedEmpIDColumn);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDColumn.minWidthProperty().bind(tablePane.widthProperty().divide(3));
    nodeIDColumn.minWidthProperty().bind(tablePane.widthProperty().divide(3));
    assignedEmpIDColumn.minWidthProperty().bind(tablePane.widthProperty().divide(3));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  // TreeItem<ArrayList<Object>> treeRoot = new TreeItem<>(new ArrayList<Object>());
  // TreeItem<requestObject> treeRoot = new TreeItem<>(new requestObject(employee, nodeID));

  /** adds items to the list to requestList ArrayList */
  /*public void populateList() {
  clearTable();
  ArrayList<ArrayList<Object>> reqs = serviceRequestStorage.getArrayList();

  treeRoot.setExpanded(true);
  reqs.stream()
      .forEach(
          (ArrayList<Object> list) -> {
            treeRoot.getChildren().add(new TreeItem<ArrayList<Object>>(list));
          });
  final Scene scene = new Scene(new Group(), 400, 400);

  TreeTableColumn<ArrayList<Object>, String> RequestTypeColumn =
      new TreeTableColumn<>("Request Type");
  RequestTypeColumn.setCellValueFactory(
      (TreeTableColumn.CellDataFeatures<ArrayList<Object>, String> param) -> {
        if (param.getValue().getValue().size() > 0)
          return new ReadOnlyStringWrapper(param.getValue().getValue().get(0).toString());
        return new ReadOnlyStringWrapper("");
      });
  /*TreeTableColumn<audioVisualRequest, String> accessObjectColumn =
          new TreeTableColumn<>("Access Object");
  accessObjectColumn.setCellValueFactory(
          (TreeTableColumn.CellDataFeatures<audioVisualRequest, String> param) ->
                  new ReadOnlyStringWrapper(param.getValue().getValue().getAccessObject()));
  */

  /*TreeTableView<ArrayList<Object>> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(RequestTypeColumn);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    // accessObjectColumn.minWidthProperty().bind(tablePane.widthProperty().divide(2));
    RequestTypeColumn.minWidthProperty().bind(tablePane.widthProperty());
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }*/

  /** clears the table in the request page */
  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setHome();
  }
}
