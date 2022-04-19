package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.mealDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class mealsController extends PageController implements Initializable, IRequestController {

  ArrayList<Object> returnList = new ArrayList<>();
  ArrayList<Object> requestList = new ArrayList<>();

  // @FXML private Button submitButton;
  @FXML private BorderPane masterPane;

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

  @FXML private JFXComboBox breakfastChoice;
  @FXML private JFXComboBox lunchChoice;
  @FXML private JFXComboBox dinnerChoice;

  public mealsController() {}

  public mealsController(ContextMenu c_menu, MenuBar m_menu) {
    super(c_menu, m_menu);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);

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
  }

  // on press returns fields into delivery object only if required fields aren't empty
  public String generateReqID(int requestListLength, String nodeID) {
    String reqAbb = "MR";

    return reqAbb + nodeID + (requestListLength + 1);
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
  public void startTable() throws SQLException, IOException {}

  /** clears the table in the request page */
  public void clearTable() {}

  public void submit() throws SQLException {
    ArrayList<String> foodList = new ArrayList<>();

    returnList.add(status.getAccessibleText());
    returnList.add(foodList);

    System.out.println("Meal Not Sent");

    requestList.clear();
    requestList.add("Meal Request");
    // requestList.add("Assigned Doctor: " + employeeName.getText());
    requestList.add("Status: " + status.getValue());
    serviceRequestStorage.addToArrayList(requestList);

    if (nodeID.getValue().toString().equals("")
        || status.getValue().toString().equals("")
        || assignedEmployee.getValue().toString().equals("")
        || requestedEmployee.getValue().toString().equals("")) {
      mealDeliveryRequest sendMealRequest = null;
      System.out.println("Meal Not Sent");
    } else {
      // String reqID = generateReqID()//TODO
      String meal = "";
      if (!breakfastChoice.getValue().toString().equals("Choose Breakfast Food")) {
        // breakfast meal requested
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
      this.reset();
      System.out.println("Meal Sent");
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

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    // TODO
  }
}
