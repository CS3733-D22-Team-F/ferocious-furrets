package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamF.ServiceRequestStorage;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.MealDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MealsController extends PageController implements Initializable, IRequestController {

  ArrayList<Object> returnList = new ArrayList<>();
  ArrayList<Object> requestList = new ArrayList<>();

  // @FXML private Button submitButton;
  @FXML private BorderPane masterPane;
  @FXML private Pane tablePane;
  @FXML private JFXTreeTableView treeTable;

  // TODO use these fxids for the combo boxes, nodes, and status
  @FXML private JFXComboBox nodeID;
  @FXML private ComboBox<Object> assignedEmployee;
  @FXML private ComboBox<Object> requestedEmployee;
  @FXML private ComboBox<Object> status;

  @FXML private TabPane mealMenu;
  @FXML private Tab b;
  @FXML private Tab l;
  @FXML private Tab d;

  // TODO remove these old fxids

  @FXML private JFXComboBox mealChoice;
  @FXML private JFXComboBox breakfastChoice;
  @FXML private JFXComboBox lunchChoice;
  @FXML private JFXComboBox dinnerChoice;

  private String requestID;
  private String nID;
  private String assignedEmpID;
  private String requesterEmpID;
  private String sts;
  private String meal;

  private TreeItem<MealDeliveryRequest> treeRoot =
      new TreeItem<>(
          new MealDeliveryRequest(requestID, nID, assignedEmpID, requesterEmpID, sts, meal));

  public MealsController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<Object> breakfast = new ArrayList<>();
    breakfast.add("French Toast");
    breakfast.add("Oatmeal");
    breakfast.add("Yogurt");
    breakfast.add("Fruit");
    breakfast.add("Quiche");
    breakfast.add("Omelette");
    breakfast.add("Belgian Waffles");
    breakfast.add("Corned Beef Hash");
    breakfastChoice.getItems().addAll(breakfast);
    breakfastChoice.setValue("Choose Breakfast Food");

    ArrayList<Object> lunch = new ArrayList<>();
    lunch.add("Pizza");
    lunch.add("Cod");
    lunch.add("Meatloaf");
    lunch.add("Spinach Salad");
    lunch.add("Cheeseburger");
    lunch.add("Italian Sub");
    lunch.add("Chicken Tenders");
    lunch.add("Meatball Sub");
    lunchChoice.getItems().addAll(lunch);
    lunchChoice.setValue("Choose Lunch Food");

    ArrayList<Object> dinner = new ArrayList<>();
    dinner.add("Grilled Tofu");
    dinner.add("Macaroni and Cheese");
    dinner.add("Chicken Quesadilla");
    dinner.add("Grilled Chicken");
    dinner.add("Spaghetti");
    dinner.add("Chicken Noodle Soup");
    dinner.add("Hamburger");
    dinner.add("Pork Loin");
    dinnerChoice.getItems().addAll(dinner);
    dinnerChoice.setValue("Choose Dinner Food");

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    status.getItems().addAll(temp);
    status.setValue("");

    ArrayList<Object> employees = employeeNames();
    assignedEmployee.getItems().addAll(employees);
    requestedEmployee.getItems().addAll(employees);
    assignedEmployee.setValue("");
    requestedEmployee.setValue("");

    ArrayList<Object> locations = locationNames();
    nodeID.getItems().addAll(locations);

    try {
      startTable();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ContextMenu makeContextMenu() {
    ContextMenu mealsMenu = new ContextMenu();
    return mealsMenu;
  }

  public void reset() {
    nodeID.valueProperty().setValue(null);
    status.valueProperty().setValue(null);
    assignedEmployee.valueProperty().setValue(null);
    requestedEmployee.valueProperty().setValue(null);
    breakfastChoice.valueProperty().setValue("Choose Breakfast Food");
    lunchChoice.valueProperty().setValue("Choose Lunch Food");
    dinnerChoice.valueProperty().setValue("Choose Dinner Food");
  }

  /**
   * Starts the table in the request page
   *
   * @throws SQLException
   * @throws IOException
   */
  public void startTable() throws SQLException, IOException {

    clearTable();

    ResultSet mealRequest =
        DatabaseManager.getInstance().getMealDAO().get(); // CHANGE THIS TO CURRENT DAO
    ResultSet servRequest;
    ArrayList<MealDeliveryRequest> secReqs = new ArrayList<MealDeliveryRequest>();
    MealDeliveryRequest mr;
    String currentEquipDelReqID;

    while (mealRequest.next()) {
      currentEquipDelReqID = mealRequest.getString("reqID");
      //      System.out.println(currentEquipDelReqID);
      servRequest = DatabaseManager.getInstance().getRequestDAO().get();
      while (servRequest.next()) {
        if (servRequest.getString("reqID").equals(currentEquipDelReqID)) {
          mr =
              new MealDeliveryRequest(
                  mealRequest.getString("reqID"),
                  servRequest.getString("nodeID"),
                  servRequest.getString("assignedEmployeeID"),
                  servRequest.getString("requesterEmployeeID"),
                  servRequest.getString("status"),
                  mealRequest.getString("meal"));
          secReqs.add(mr);
          servRequest.close();
          break;
        }
      }
    }

    mealRequest.close();

    treeRoot.setExpanded(true);
    secReqs.stream()
        .forEach(
            (MealDeliveryRequest) -> {
              treeRoot.getChildren().add(new TreeItem<>(MealDeliveryRequest));
            });

    TreeTableColumn<MealDeliveryRequest, String> reqIDCol = new TreeTableColumn<>("Request ID");
    reqIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MealDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getReqID()));

    TreeTableColumn<MealDeliveryRequest, String> nodeIDCol = new TreeTableColumn<>("Location");
    nodeIDCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MealDeliveryRequest, String> param) -> {
          try {
            return new ReadOnlyStringWrapper(nodeIDToName(param.getValue().getValue().getNodeID()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return new ReadOnlyStringWrapper(param.getValue().getValue().getNodeID());
        });

    TreeTableColumn<MealDeliveryRequest, String> assignedToCol =
        new TreeTableColumn<>("Assigned To");
    assignedToCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MealDeliveryRequest, String> param) -> {
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

    TreeTableColumn<MealDeliveryRequest, String> mealCol = new TreeTableColumn<>("Meal");
    mealCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MealDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getMeal()));

    TreeTableColumn<MealDeliveryRequest, String> statusCol = new TreeTableColumn<>("Status");
    statusCol.setCellValueFactory(
        (TreeTableColumn.CellDataFeatures<MealDeliveryRequest, String> param) ->
            new ReadOnlyStringWrapper(param.getValue().getValue().getStatus()));

    TreeTableView<MealDeliveryRequest> treeTableView = new TreeTableView<>(treeRoot);
    treeTableView.getColumns().setAll(reqIDCol, nodeIDCol, assignedToCol, mealCol, statusCol);
    tablePane.minWidthProperty().bind(masterPane.widthProperty().divide(2));
    tablePane.minHeightProperty().bind(masterPane.heightProperty());
    tablePane.getChildren().add(treeTableView);
    reqIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    nodeIDCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    assignedToCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    mealCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    statusCol.minWidthProperty().bind(tablePane.widthProperty().divide(5));
    treeTableView.minHeightProperty().bind(masterPane.heightProperty());
    treeTableView.minWidthProperty().bind(masterPane.widthProperty().divide(2));
  }

  public void submit() throws SQLException, IOException {
    ArrayList<String> foodList = new ArrayList<>();

    returnList.add(status.getAccessibleText());
    returnList.add(foodList);

    System.out.println("Meal Not Sent");

    requestList.clear();
    requestList.add("Meal Request");
    // requestList.add("Assigned Doctor: " + employeeName.getText());
    requestList.add("Status: " + status.getValue());
    ServiceRequestStorage.addToArrayList(requestList);

    if (nodeID.getValue().toString().equals("")
        || status.getValue().toString().equals("")
        || assignedEmployee.getValue().toString().equals("")
        || requestedEmployee.getValue().toString().equals("")) {
      MealDeliveryRequest sendMealRequest = null;
      System.out.println("Meal Not Sent");
    } else {
      String meal = "";
      if (!breakfastChoice.getValue().toString().equals("Choose Breakfast Food")) {
        meal = breakfastChoice.getValue().toString();
      } else if (!lunchChoice.getValue().toString().equals("Choose Lunch Food")) {
        meal = lunchChoice.getValue().toString();
      } else if (!dinnerChoice.getValue().toString().equals("Choose Dinner Food")) {
        meal = dinnerChoice.getValue().toString();
      }
      RequestSystem req = new RequestSystem("Meal");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(generateReqID());
      fields.add(nodeIDFinder(nodeID.getValue().toString()));
      fields.add(employeeIDFinder(assignedEmployee.getValue().toString()));
      fields.add(employeeIDFinder(requestedEmployee.getValue().toString()));
      fields.add(status.getValue().toString());
      fields.add(meal);
      req.placeRequest(fields);

      reset();

      startTable();
    }
  }

  public String generateReqID() throws SQLException {
    String nNodeType = "";
    if (!breakfastChoice.getValue().toString().equals("Choose Breakfast Food")) {
      // breakfast meal requested
      nNodeType = breakfastChoice.getValue().toString();
    } else if (!lunchChoice.getValue().toString().equals("Choose Lunch Food")) {
      nNodeType = lunchChoice.getValue().toString();
    } else if (!dinnerChoice.getValue().toString().equals("Choose Dinner Food")) {
      nNodeType = dinnerChoice.getValue().toString();
    }
    nNodeType = nNodeType.substring(0, 3);
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
    rset.close();

    return lName;
  }

  public String empIDToFirstName(String eID) throws SQLException {
    String cmd = String.format("SELECT firstName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String fName = "";
    while (rset.next()) {
      fName = rset.getString("firstName");
    }
    rset.close();

    return fName;
  }

  public String empIDToLastName(String eID) throws SQLException {
    String cmd = String.format("SELECT lastName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("lastName");
    }
    rset.close();

    return lName;
  }

  public void clearTable() {
    treeRoot.getChildren().remove(0, treeRoot.getChildren().size());
  }
}
