package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import edu.wpi.cs3733.D22.teamF.reports.GenerateReport;
import edu.wpi.cs3733.D22.teamF.reports.PDFConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.docx4j.openpackaging.exceptions.Docx4JException;

/**
 * controller for request list
 *
 * @see Initializable
 */
public class RequestListController extends PageController implements Initializable {

  public static String selectedID = "";
  public static String selectedType = "";
  //  @FXML ListView requestList;
  @FXML JFXTreeTableView requestList;
  @FXML Pane tablePane;
  @FXML private AnchorPane masterPane;
  @FXML private JFXButton reportButton;

  TreeTableView<RequestTree> treeTableView = new TreeTableView<>();

  private String selectedRequestIDForReport;

  private String employee;
  private String nodeID;
  private String reqID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String status;
  @FXML private JFXButton filterButton;
  @FXML private TextField filterEmployee;

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
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  TreeItem<RequestTree> treeRoot =
      new TreeItem<>(new RequestTree(reqID, nodeID, assignedEmpID, requesterEmpID, status));

  public String employeeIDFinder(String name) throws SQLException {
    String empID = "";
    String[] employeeName = name.split(",");
    String last = employeeName[0].trim();
    String first = employeeName[1].trim();
    last = last.strip();
    first = first.strip();
    String cmd =
        String.format(
            "SELECT EMPLOYEEID FROM EMPLOYEE WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'",
            first, last);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
  }

  public void f() throws SQLException, IOException {
    if (filterEmployee.getText().equals("ALL")) {
      startTable();
      return;
    }
    startFilteredTable(filterEmployee.getText());
  }

  public void startFilteredTable(String employeeName) throws SQLException, IOException {

    clearTable();

    ResultSet rset = DatabaseManager.getInstance().getRequestDAO().get();
    employeeName = filterEmployee.getText();

    ArrayList<RequestTree> reqs = new ArrayList<RequestTree>();
    RequestTree rt;
    String empID = employeeIDFinder(employeeName);

    String cmd =
        String.format("SELECT * FROM ServiceRequest WHERE assignedEmployeeID = '%s'", empID);
    ResultSet filteredReq = DatabaseManager.getInstance().runQuery(cmd);

    //    while(employee.next()){
    //      currentEmployeeFirstName = employee.getString("FIRSTNAME");
    //      currentEmployeeLastName = employee.getString("LASTNAME");
    //      if(currentEmployeeFirstName.contains(employeeName) ||
    // currentEmployeeLastName.contains(employeeName)){
    //        ArrayList<String>rset.getString("assignedEmployeeID");
    //      }
    //    }

    while (filteredReq.next()) {
      rt =
          new RequestTree(
              filteredReq.getString("reqID"),
              filteredReq.getString("nodeID"),
              filteredReq.getString("assignedEmployeeID"),
              filteredReq.getString("requesterEmployeeID"),
              filteredReq.getString("status"));
      reqs.add(rt);
    }

    filteredReq.close();

    treeRoot.setExpanded(true);
    reqs.stream()
        .forEach(
            (requestTree) -> {
              treeRoot.getChildren().add(new TreeItem<>(requestTree));
            });
    // final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<RequestTree, String> reqIDColumn = new TreeTableColumn<>("Request ID");
    reqIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<RequestTree, String> nodeIDColumn = new TreeTableColumn<>("Node ID");
    nodeIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID()));

    TreeTableColumn<RequestTree, String> assignedEmpIDColumn = new TreeTableColumn<>("Employee ID");
    assignedEmpIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID()));

    treeTableView = new TreeTableView<>(treeRoot);
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

  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet rset =
        DatabaseManager.getInstance()
            .runQuery(
                "SELECT * FROM ServiceRequest WHERE UPPER(status) = 'PROCESSING'"); // .runQuery("SELECT
    // * FROM
    // ServiceRequest");
    ArrayList<RequestTree> reqs = new ArrayList<RequestTree>();
    RequestTree rt;

    while (rset.next()) {
      rt =
          new RequestTree(
              rset.getString("reqID"),
              rset.getString("nodeID"),
              rset.getString("assignedEmployeeID"),
              rset.getString("requesterEmployeeID"),
              rset.getString("status"));
      reqs.add(rt);
    }
    rset.close();
    treeRoot.setExpanded(true);
    reqs.stream()
        .forEach(
            (requestTree) -> {
              treeRoot.getChildren().add(new TreeItem<>(requestTree));
            });
    // final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<RequestTree, String> reqIDColumn = new TreeTableColumn<>("Request ID");
    reqIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) -> {
          return new ReadOnlyStringWrapper(param.getValue().getValue().getReqID());
        });

    TreeTableColumn<RequestTree, String> nodeIDColumn = new TreeTableColumn<>("Node ID");
    nodeIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) -> {
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<RequestTree, String> assignedEmpIDColumn = new TreeTableColumn<>("Employee ID");
    assignedEmpIDColumn.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<RequestTree, String> param) -> {
          return new ReadOnlyStringWrapper(param.getValue().getValue().getAssignedEmpID());
        });

    treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(reqIDColumn, nodeIDColumn, assignedEmpIDColumn);
    treeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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

  void onChangePress() throws IOException {
    // TODO replace "new" items with defined items
    TreeTableView<RequestTree> table = requestList;
    TreeItem<RequestTree> selectedItem = table.getSelectionModel().getSelectedItem();
    selectedID = selectedItem.getValue().getReqID();
    selectedType = selectedItem.getValue().getReqType();
    popUpModifyReq();
    // requestListController.selectedID;
    // requestListController.selectedType;
  }

  void popUpModifyReq() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(Fapp.class.getResource("Map/mapEquipModifyPage.fxml")));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }

  public void generateReport() {

    // TODO Format Word template
    if (treeTableView.getSelectionModel().getSelectedItem() == null) {
      showAlert("Please select a request from the table!", masterPane);
      return;
    }
    FileChooser fChoose = new FileChooser();
    fChoose.setTitle("Save to:");
    Stage stage = (Stage) tablePane.getScene().getWindow();
    File file = fChoose.showSaveDialog(stage);
    String filepath = file.getPath() + ".docx";

    TreeItem<RequestTree> req = treeTableView.getSelectionModel().getSelectedItem();
    if (req != null) {
      RequestTree request = req.getValue();

      GenerateReport rep =
          new GenerateReport(
              request.getReqID(),
              request.getReqType(),
              request.getNodeID(),
              request.getAssignedEmpID(),
              request.getRequesterEmpID(),
              request.getStatus());
      try {
        rep.generateGenericServiceRequestReport(filepath);
        showAlert("Report created!", tablePane);
      } catch (Throwable e) {
        System.out.println("Report failed");
        showAlert("Failed to create report!", tablePane);
        e.printStackTrace();
      }
      PDFConverter pdfConverter = new PDFConverter(filepath, file.getPath() + ".pdf");
      try {
        pdfConverter.convertToPDF();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (Docx4JException e) {
        showAlert(
            "Sorry, this feature is not currently available on systems without MS Word:(",
            tablePane);
        e.printStackTrace();
      }
    }
  }
}
