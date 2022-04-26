package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamB.api.*;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InternalPatientController extends PageController implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private BorderPane masterPane;
  @FXML private TextField patientField;
  @FXML private JFXComboBox employeeIDField;
  @FXML private JFXComboBox userField;
  @FXML private ComboBox statusChoice;
  @FXML private Button resetButton;
  @FXML private Button submitButton;
  @FXML private TextField addressField;
  @FXML private ComboBox methodField;
  @FXML private HBox topHBox;
  @FXML private HBox middleHBox;
  @FXML private HBox bottomHBox;
  @FXML private Rectangle rectangle1;
  @FXML private Rectangle rectangle2;
  @FXML private JFXTreeTableView treeTable;
  @FXML private Pane tablePane;
  @FXML private JFXComboBox startNodeField;
  @FXML private JFXComboBox endNodeField;
  @FXML private JFXComboBox prioritiesChoice;
  @FXML private JFXComboBox nodeField;

  @FXML private TextField reqID;
  @FXML private Button resolveReq;

  private String requestID;
  private String employeeID;
  private String start;
  private String finish;
  private String status;
  private int priority;
  private String information;
  private Date timeCreated;
  private Date lastEdited;
  private Date deadline;

  private String address;
  private String method;

  DatabaseController dbc = new DatabaseController();

  TreeItem<Request> treeRoot =
      new TreeItem<>(
          new Request(
              requestID,
              employeeID,
              start,
              finish,
              status,
              priority,
              information,
              timeCreated,
              lastEdited,
              deadline));

  // Status, start, finish, priority, employeeID - All need to be made in the page
  // requestID gets made as request is submitted

  // information, timeCreates, lastEdited, deadline - All dummy variables, not needed

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  public void initialize(URL location, ResourceBundle resources) {

    try {
      populateAPIEmployeeDatabase();
      populateAPILocationsDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    ArrayList<Object> statusDrop = new ArrayList<>();
    ArrayList<Integer> priorities = new ArrayList<>();

    statusDrop.add("Processing");
    statusDrop.add("Done");
    statusChoice.getItems().addAll(statusDrop);

    ArrayList<Object> locations = locationNames();
    nodeField.getItems().addAll(locations);
    startNodeField.getItems().addAll(locations);
    endNodeField.getItems().addAll(locations);

    priorities.add(1);
    priorities.add(2);
    priorities.add(3);
    priorities.add(4);
    priorities.add(5);
    prioritiesChoice.getItems().addAll(priorities);

    ArrayList<Object> employees = employeeNames();
    employeeIDField.getItems().addAll(employees);

    try {
      startTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void submit() throws SQLException {

    ArrayList<Object> requestList = new ArrayList<>();
    if (employeeIDField.getValue().toString().equals("")
        || statusChoice.getValue().equals("")
        || startNodeField.getValue().equals("")
        || endNodeField.getValue().equals("")
        || prioritiesChoice.getValue().equals("")) {
      // Adds alert for empty fields for ext-patient request
      //      AGlobalMethods.showAlert("Empty Field(s)", submitButton);
    } else {
      //            requestList.clear();
      //            requestList.add("External Patient Transport Request for: " +
      // addressField.getText());
      //            requestList.add("Status: " + statusChoice.getValue());
      //            ServiceRequestStorage.addToArrayList(requestList);
      Request transportReq =
          new Request(
              generateReqID(),
              employeeIDFinder(employeeIDField.getValue().toString()),
              nodeIDFinder(startNodeField.getValue().toString()),
              nodeIDFinder(endNodeField.getValue().toString()),
              statusChoice.getValue().toString(),
              (Integer) prioritiesChoice.getValue(),
              "null",
              new Date(),
              new Date(),
              new Date());

      dbc.add(transportReq);

      reset();
    }

    startTable();
  }

  @FXML
  public void reset() {
    employeeIDField.valueProperty().setValue(null);
    statusChoice.valueProperty().setValue(null);
    startNodeField.valueProperty().setValue(null);
    endNodeField.valueProperty().setValue(null);
    prioritiesChoice.valueProperty().setValue(null);
  }

  public void startTable() throws SQLException {

    clearTable();

    LinkedList<Request> allReqs = dbc.listRequests();
    //        ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM
    // externalPatientRequest");
    //        ArrayList<ExtPatientDeliveryRequest> externalPatientReqs =
    //                new ArrayList<ExtPatientDeliveryRequest>();
    //        ExtPatientDeliveryRequest avr;

    //        while (rset.next()) {
    //            avr =
    //                    new ExtPatientDeliveryRequest(
    //                            rset.getString("reqID"), rset.getString("address"),
    // rset.getString("method"));
    //            externalPatientReqs.add(avr);
    //        }
    //        rset.close();

    treeRoot.setExpanded(true);
    allReqs.stream()
        .forEach(
            (request) -> {
              treeRoot.getChildren().add(new TreeItem<>(request));
            });
    final Scene scene = new Scene(new Group(), 400, 400);

    TreeTableColumn<Request, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getRequestID()));

    TreeTableColumn<Request, String> startNodeIDCol = new TreeTableColumn<>("Start Location");
    startNodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                nodeIDToName(param.getValue().getValue().getStartLocation().getNodeID()));
          } catch (SQLException | NullPointerException e) {
            return new ReadOnlyStringWrapper(
                param.getValue().getValue().getStartLocation().getNodeID());
          }
        });

    TreeTableColumn<Request, String> endNodeIDCol = new TreeTableColumn<>("End Location");
    endNodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                nodeIDToName(param.getValue().getValue().getFinishLocation().getNodeID()));
          } catch (SQLException | NullPointerException e) {
            return new ReadOnlyStringWrapper(
                param.getValue().getValue().getFinishLocation().getNodeID());
          }
        });

    TreeTableColumn<Request, String> prioritiesCol = new TreeTableColumn<>("Priority");
    prioritiesCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) ->
            new ReadOnlyStringWrapper("" + param.getValue().getValue().getPriority()));

    TreeTableColumn<Request, String> assignedToCol = new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(
                empIDToFirstName(param.getValue().getValue().getEmployeeID())
                    + " "
                    + empIDToLastName(param.getValue().getValue().getEmployeeID()));
          } catch (SQLException | NullPointerException e) {
            return new ReadOnlyStringWrapper(param.getValue().getValue().getEmployeeID());
          }
        });

    TreeTableColumn<Request, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<Request, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<Request> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView
        .getColumns()
        .setAll(reqIDCol, startNodeIDCol, endNodeIDCol, prioritiesCol, assignedToCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    startNodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    endNodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    prioritiesCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(6));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public String generateReqID() throws SQLException {

    String nameAb = "IPT";
    //        int reqNum = 1;

    //        ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM
    // SERVICEREQUEST");
    //        while (rset.next()) {
    //            reqNum++;
    //        }
    //        rset.close();
    LinkedList<Request> allReqs = dbc.listRequests();

    int reqNum = allReqs.size();

    String nID = "f" + nameAb + reqNum;
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
  public ContextMenu makeContextMenu() {
    return null;
  }

  public String nodeIDToName(String nID) throws SQLException {
    String cmd = String.format("SELECT LONGNAME FROM Locations WHERE nodeID = '%s'", nID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("LONGNAME");
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

  public void populateAPIEmployeeDatabase() throws SQLException {
    LinkedList<IPTEmployee> employees = new LinkedList<>();
    String cmd = "SELECT * FROM EMPLOYEE";
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    while (rset.next()) {
      String firstName = rset.getString("FIRSTNAME");
      String lastName = rset.getString("LASTNAME");
      String id = rset.getString("EMPLOYEEID");
      IPTEmployee employee = new IPTEmployee(id, lastName, firstName, "Foo", "Foo");
      employees.add(employee);
    }
    rset.close();
    for (IPTEmployee employee : employees) {
      dbc.add(employee);
    }
  }

  public void populateAPILocationsDatabase() throws SQLException {
    LinkedList<Location> locations = new LinkedList<>();
    String cmd = "SELECT * FROM LOCATIONS";
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    while (rset.next()) {
      String nodeID = rset.getString("NODEID");
      int xcoord = Integer.parseInt(rset.getString("XCOORD"));
      int ycoord = Integer.parseInt(rset.getString("YCOORD"));
      String floor = rset.getString("FLOOR");
      String building = rset.getString("BUILDING");
      String nodeType = rset.getString("NODETYPE");
      String longName = rset.getString("LONGNAME");
      String shortName = rset.getString("SHORTNAME");
      Location location =
          new Location(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);
      locations.add(location);
    }
    rset.close();
    for (Location location : locations) {
      dbc.add(location);
    }
  }
}
