package edu.wpi.cs3733.D22.teamF.Map;

import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapLocationModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.coordTempHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for adding stuff to the map
 */
public class mapAddController implements Initializable {

  @FXML ChoiceBox<String> nodeBox;

  @FXML private TextField floorField;
  @FXML private TextField xField;
  @FXML private TextField yField;
  @FXML private TextField longField;
  @FXML private TextField shortField;

  @FXML private Button cancel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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
    floorField.clear();
    xField.clear();
    yField.clear();
    longField.clear();
    shortField.clear();
  }

  /**
   * @param event is the click of the submit button
   * @throws SQLException
   * @throws IOException
   *     <p>Submit turns the fields of the Add Location window into a new Location object then
   *     passes that object to the LocationDAOImpl class to add to the database
   */
  public void submit(ActionEvent event) throws SQLException, IOException {

    if (!nodeBox.getValue().equals("")
        && !floorField.getText().equals("")
        && !xField.getText().equals("")
        && !yField.getText().equals("")
        && !longField.getText().equals("")
        && !shortField.getText().equals("")) {
      try {
        MapLocationModifier.addLocation(
            nodeBox.getValue(),
            xField.getText(),
            yField.getText(),
            floorField.getText(),
            longField.getText(),
            shortField.getText());
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

  /**
   * popup window to click a location on map and automatically load x-y coords to window
   *
   * @throws IOException
   */
  public void popUpTracker() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapTrackerPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    xField.setText(coordTempHolder.getxValue());
    yField.setText(coordTempHolder.getyValue());
    floorField.setText(coordTempHolder.getFloorValue());
  }
}
