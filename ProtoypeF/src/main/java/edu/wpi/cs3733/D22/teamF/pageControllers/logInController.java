package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.returnHomePage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controll for log in scene
 *
 * @see returnHomePage
 */
public class logInController extends returnHomePage {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label popUpLabel;

  private Stage stage = new Stage();
  private Parent root;
  private Scene scene;

  @FXML
  private void helpQuit() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }
  /** logs in, or states message the username or password are wrong */
  @FXML
  private void logIn() {
    boolean success = false;
    UserType userType = new UserType();
    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      userType.setUserType("admin");
      success = true;
    } else if (usernameField.getText().equals("nurse") && passwordField.getText().equals("nurse")) {
      userType.setUserType("nurse");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      success = true;
    } else if (usernameField.getText().equals("staff") && passwordField.getText().equals("staff")) {
      userType.setUserType("staff");
      success = true;
    } else if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("ERROR");
      alert.setHeaderText("One or more field is empty");
      alert.setContentText("Please fill out any empty fields.");
      alert.showAndWait();
    } else {
      userType.setUserType("");
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
    if (success) {
      usernameField.clear();
      passwordField.clear();
      StageManager.getInstance().setHomeScreen();
      popUpLabel.setVisible(false);
    } else {
      popUpLabel.setVisible(true);
    }
    ;
  }
}
