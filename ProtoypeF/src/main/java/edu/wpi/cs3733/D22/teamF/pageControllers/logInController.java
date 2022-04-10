package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  /*
   * method to send user to the homepage after a successful authentication of username and password
   */
  public void loginSuccess() throws IOException {
    StageManager.getInstance().setDisplay("homePage.fxml");
  }

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
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("nurse") && passwordField.getText().equals("nurse")) {
      userType.setUserType("nurse");
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("staff") && passwordField.getText().equals("staff")) {
      userType.setUserType("staff");
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else {
      userType.setUserType("");
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
    if (success) {
      StageManager.getInstance().setHomeScreen();
    }
    ;
  }
}
