package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.io.IOException;
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
    root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /** logs in, or states message the username or password are wrong */
  @FXML
  private void logIn() {
    boolean success = false;
    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("nurse") && passwordField.getText().equals("nurse")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else if (usernameField.getText().equals("staff") && passwordField.getText().equals("staff")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
      success = true;
    } else {
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
    if (success) {
      StageManager stMan = StageManager.getInstance();
      stMan.setHomeScreen();
    }
    ;
  }
}
