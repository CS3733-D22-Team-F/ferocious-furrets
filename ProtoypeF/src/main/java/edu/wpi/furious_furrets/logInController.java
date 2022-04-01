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
      popUpLabel.setText("Log In Success");
    } else {
      popUpLabel.setText("Wrong username or password, try again");
    }
  }
}
