package edu.wpi.cs3733.D22.teamF.controllers.requests;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.mealDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class mealsController extends PageController implements Initializable, IRequestController {

  ArrayList<Object> returnList = new ArrayList<>();
  ArrayList<Object> requestList = new ArrayList<>();
  @FXML private ComboBox<Object> status;

  // submit button sendind delivery
  // @FXML private Button submitButton;
  @FXML private AnchorPane masterPane;
  @FXML private BorderPane menuPane;
  @FXML private TextField employeeName;
  @FXML private TextField employeeID;
  @FXML private TextField nodeID;
  @FXML private TextField requestType;
  @FXML private TextField deliveryType;
  @FXML private TextField deliveryID;

  public mealsController() {}

  public mealsController(ContextMenu c_menu, MenuBar m_menu) {
    super(c_menu, m_menu);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    this.makeMenuBar(masterPane);

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    status.getItems().addAll(temp);
    status.setValue("");
  }

  public void reset() {
    employeeName.setText("");
    employeeID.setText("");
    nodeID.setText("");
    status.setValue("");
    requestType.setText("");
    deliveryID.setText("");
    deliveryType.setText("");
  }

  // on press returns fields into delivery object only if required fields aren't empty

  public String generateReqID(int requestListLength, String nodeID) {
    String reqAbb = "MR";

    return reqAbb + nodeID + (requestListLength + 1);
  }

  public void submit() {
    ArrayList<String> foodList = new ArrayList<>();

    returnList.add(status.getAccessibleText());
    returnList.add(foodList);

    System.out.println("Meal Not Sent");

    requestList.clear();
    requestList.add("Meal Request");
    requestList.add("Assigned Doctor: " + employeeName.getText());
    requestList.add("Status: " + status.getValue());
    serviceRequestStorage.addToArrayList(requestList);

    if (employeeName.getText().equals("")
        || employeeID.getText().equals("")
        || nodeID.getText().equals("")
        || status.getValue().toString().equals("")
        || requestType.getText().equals("")) {
      mealDeliveryRequest sendMealRequest = null;
      System.out.println("Meal Not Sent");
    } else {
      // String reqID = generateReqID()//TODO
      RequestSystem req = new RequestSystem("Meal");
      ArrayList<String> fields = new ArrayList<String>();
      fields.add(nodeID.getText());
      fields.add(employeeName.getText());
      fields.add("Requested Employee");
      fields.add(status.getValue().toString());
      fields.add(foodList.get(0));

      req.placeRequest(fields);
      this.reset();
      System.out.println("Meal Sent");
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }

  @Override
  public ContextMenu makeContextMenu() {
    ContextMenu mealsMenu = new ContextMenu();
    return mealsMenu;
  }
}