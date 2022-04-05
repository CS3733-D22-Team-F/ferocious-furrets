package edu.wpi.furious_furrets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * controll for log in scene
 *
 * @see returnHomePage
 */
public class logInController extends returnHomePage {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label popUpLabel;

  /** logs in, or states message the username or password are wrong */
  @FXML
  private void logIn() {
    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
    } else if (usernameField.getText().equals("nurse") && passwordField.getText().equals("nurse")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
    } else if (usernameField.getText().equals("staff") && passwordField.getText().equals("staff")) {
      popUpLabel.setStyle("-fx-text-fill: green;");
      popUpLabel.setText("Login Successful");
    } else {
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
  }
}
