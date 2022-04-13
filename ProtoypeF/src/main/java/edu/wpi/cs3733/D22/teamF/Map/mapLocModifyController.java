package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapLocationModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.nodeTempHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * controller for modifying map
 */
public class mapModifyController implements Initializable {

  String floor = "1";

  @FXML ChoiceBox<String> nodeBox;

  @FXML private AnchorPane iconPane;

  @FXML private TextField longField;

  @FXML private Button cancel;

  @FXML private JFXButton selectedIcon;

  @FXML private Label currentNode;
  @FXML private String xValue;
  @FXML private String yValue;
  @FXML private String floorValue;

  @FXML private ImageView mapHolder;
  Image F1 = new Image(getClass().getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(getClass().getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(getClass().getResourceAsStream("FloorMap/Floor3.jpg"));
  Image F4 = new Image(getClass().getResourceAsStream("FloorMap/Floor4.jpg"));
  Image F5 = new Image(getClass().getResourceAsStream("FloorMap/Floor5.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentNode.setText(nodeTempHolder.getLocation().getLongName());
    changeToF1();
    ArrayList<String> temp = new ArrayList<>();
    temp.add("PATI - Patient Room");
    temp.add("STOR - Equipment Storage Room");
    temp.add("DIRT - Dirty Equipment Pickup Locations");
    temp.add("HALL - Hallway");
    temp.add("ELEV - Elevator");
    temp.add("REST - Restroom");
    temp.add("STAI - Staircase");
    temp.add("DEPT - Medical Departments, Clinics, and Waiting Room Areas");
    temp.add("LABS - Labs, Imaging Centers, and Medical Testing Areas");
    temp.add("INFO - Information Desks, Security Desks, Lost and Dound");
    temp.add("CONF - Conference Room");
    temp.add("EXIT - Exit/Entrance");
    temp.add(
        "RETL - Shops, Food, Pay Phone, Areas That Provide Non-medical\n"
            + "Services For Immediate Payment");
    temp.add(
        "SERV - Hospital Non-medical Services, Interpreters, Shuttles, Spiritual Library,\n"
            + "Patient Financial, etc.");
    nodeBox.getItems().addAll(temp);
    nodeBox.setValue("PATI - Patient Room");
  }

  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /** reset fields in add window */
  public void reset() {
    nodeBox.setValue("PATI - Patient Room");
    floorValue = "";
    xValue = "";
    yValue = "";
    longField.clear();
    iconPane.getChildren().remove(selectedIcon);
  }

  /**
   * @throws SQLException
   * @throws IOException
   *     <p>Submit turns the fields of the Add Location window into a new Location object then
   *     passes that object to the LocationDAOImpl class to add to the database
   */
  public void submit() throws SQLException, IOException {
    if (!nodeBox.getValue().equals("")
        && !floorValue.equals("")
        && !xValue.equals("")
        && !yValue.equals("")
        && !longField.getText().equals("")) {
      try {
        String shortName = longField.getText();
        if (longField.getText().length() > 128) {
          shortName = longField.getText().substring(0, 127);
        }
        MapLocationModifier.addLocation(
            nodeBox.getValue(), xValue, yValue, floorValue, longField.getText(), shortName);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Alert");
      alert.setHeaderText("Blank Field");
      String s = "At least one required field is missing!";
      alert.setContentText(s);
      alert.show();
    }
  }

  public void delete() throws SQLException, IOException {
    MapLocationModifier.deleteLocation(nodeTempHolder.getLocation());
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void track(MouseEvent event) {
    xValue = (event.getX() + 2) + "";
    yValue = (event.getY() - 2) + "";
    floorValue = floor;
    addIcon(xValue, yValue);
  }

  public void changeToF1() {
    mapHolder.setImage(F1);
    floor = "1";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    floor = "2";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    floor = "3";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF4() {
    mapHolder.setImage(F4);
    floor = "4";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    floor = "5";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    floor = "L1";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    floor = "L2";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void addIcon(String x, String y) {
    iconPane.getChildren().remove(selectedIcon);
    selectedIcon = new JFXButton("", getIcon());
    selectedIcon.setPrefSize(10, 10);
    selectedIcon.setMinSize(10, 10);
    selectedIcon.setMaxSize(10, 10);
    selectedIcon.setLayoutX(Double.parseDouble(x));
    selectedIcon.setLayoutY(Double.parseDouble(y));
    iconPane.getChildren().add(selectedIcon);
  }

  public ImageView getIcon() {
    Image image = new Image(getClass().getResourceAsStream("Icons/MapIcons/INFO Icon.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(10);
    imageView.setFitWidth(10);
    return imageView;
  }
}
