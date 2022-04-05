package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.mealDeliveryRequest.mealDeliveryRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class mealsController extends returnHomePage implements Initializable {

  ArrayList<Object> returnList = new ArrayList<>();
  ArrayList<Object> requestList = new ArrayList<>();
  @FXML private ComboBox<Object> status;

  // submit button sendind delivery
  // @FXML private Button submitButton;
  @FXML private TextField employeeName;
  @FXML private TextField employeeID;
  @FXML private TextField nodeID;
  @FXML private TextField requestType;
  @FXML private TextField deliveryType;
  @FXML private TextField deliveryID;
  @FXML private CheckBox pancakes;
  @FXML private CheckBox scrEggs;
  @FXML private CheckBox friedEggs;
  @FXML private CheckBox oats;
  @FXML private CheckBox fParfait1;
  @FXML private CheckBox toast;
  @FXML private CheckBox bacon;
  @FXML private CheckBox sausage;
  @FXML private CheckBox hash;
  @FXML private CheckBox cup1;
  @FXML private CheckBox water1;
  @FXML private CheckBox orJuice1;
  @FXML private CheckBox coffee1;
  @FXML private CheckBox frSmoothie1;
  @FXML private CheckBox apJuice1;
  //  @FXML private CheckBox turk;
  //  @FXML private CheckBox steak;
  //  @FXML private CheckBox tomato;
  //  @FXML private CheckBox noodle;
  //  @FXML private CheckBox grCheese;
  //  @FXML private CheckBox cup2;
  //  @FXML private CheckBox apple;
  //  @FXML private CheckBox orange;
  //  @FXML private CheckBox onRings;
  //  @FXML private CheckBox frenchFries;
  //  @FXML private CheckBox water2;
  //  @FXML private CheckBox orJuice2;
  //  @FXML private CheckBox coffee2;
  //  @FXML private CheckBox frSmoothie2;
  //  @FXML private CheckBox apJuice2;
  //  @FXML private CheckBox chicken;
  //  @FXML private CheckBox soup;
  //  @FXML private CheckBox chickWrap;
  //  @FXML private CheckBox buffWrap;
  //  @FXML private CheckBox frParfait;
  //  @FXML private CheckBox potatoes;
  //  @FXML private CheckBox rice;
  //  @FXML private CheckBox friedveg;
  //  @FXML private CheckBox hash2;
  //  @FXML private CheckBox cup3;
  //  @FXML private CheckBox water3;
  //  @FXML private CheckBox orJuice3;
  //  @FXML private CheckBox coffee3;
  //  @FXML private CheckBox frSmoothie3;
  //  @FXML private CheckBox apJuice3;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("processing");
    temp.add("done");
    status.getItems().addAll(temp);
    status.setValue("");
  }

  // on press returns fields into delivery object only if required fields aren't empty
  public DeliveryRequest submit() {

    ArrayList<String> foodList = new ArrayList<>();
    if (pancakes.isSelected()) {
      foodList.add(pancakes.getText());
    }
    if (scrEggs.isSelected()) {
      foodList.add(scrEggs.getText());
    }
    if (oats.isSelected()) {
      foodList.add(oats.getText());
    }
    if (fParfait1.isSelected()) {
      foodList.add(fParfait1.getText());
    }
    if (toast.isSelected()) {
      foodList.add(toast.getText());
    }
    if (bacon.isSelected()) {
      foodList.add(bacon.getText());
    }
    if (sausage.isSelected()) {
      foodList.add(sausage.getText());
    }
    if (hash.isSelected()) {
      foodList.add(hash.getText());
    }
    if (cup1.isSelected()) {
      foodList.add(cup1.getText());
    }
    if (water1.isSelected()) {
      foodList.add(water1.getText());
    }
    if (orJuice1.isSelected()) {
      foodList.add(orJuice1.getText());
    }
    if (coffee1.isSelected()) {
      foodList.add(coffee1.getText());
    }
    if (frSmoothie1.isSelected()) {
      foodList.add(frSmoothie1.getText());
    }
    if (apJuice1.isSelected()) {
      foodList.add(apJuice1.getText());
    }
    if (friedEggs.isSelected()) {
      foodList.add(friedEggs.getText());
    }

    returnList.add(status.getAccessibleText());
    returnList.add(foodList);

    System.out.println("Meal Not Sent");

    if (pancakes.isSelected()) {
      pancakes.setSelected(false);
    }
    if (scrEggs.isSelected()) {
      scrEggs.setSelected(false);
    }
    if (oats.isSelected()) {
      oats.setSelected(false);
    }
    if (fParfait1.isSelected()) {
      fParfait1.setSelected(false);
    }
    if (toast.isSelected()) {
      toast.setSelected(false);
    }
    if (bacon.isSelected()) {
      bacon.setSelected(false);
    }
    if (sausage.isSelected()) {
      sausage.setSelected(false);
    }
    if (hash.isSelected()) {
      hash.setSelected(false);
    }
    if (cup1.isSelected()) {
      cup1.setSelected(false);
    }
    if (water1.isSelected()) {
      water1.setSelected(false);
    }
    if (orJuice1.isSelected()) {
      orJuice1.setSelected(false);
    }
    if (coffee1.isSelected()) {
      coffee1.setSelected(false);
    }
    if (frSmoothie1.isSelected()) {
      frSmoothie1.setSelected(false);
    }
    if (apJuice1.isSelected()) {
      apJuice1.setSelected(false);
    }
    if (friedEggs.isSelected()) {
      friedEggs.setSelected(false);
    }

    System.out.print(foodList);

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
      return sendMealRequest;
    } else {

      // String reqID = generateReqID()//TODO

      mealDeliveryRequest sendMealRequest =
          new mealDeliveryRequest(
              null,
              nodeID.getText(),
              employeeID.getText(),
              null, // TODO ADD REQUESTER ID FIELD
              requestType.getText(),
              "Delivery",
              "Meal");
      System.out.println("Meal Sent");

      employeeName.setText("");
      employeeID.setText("");
      nodeID.setText("");
      status.setValue("");
      requestType.setText("");
      deliveryID.setText("");
      deliveryType.setText("");
      return sendMealRequest;
    }
  }

  public String generateReqID(int requestListLength, String nodeID) {
    String reqAbb = "MR";

    return reqAbb + nodeID + (requestListLength + 1);
  }
}
