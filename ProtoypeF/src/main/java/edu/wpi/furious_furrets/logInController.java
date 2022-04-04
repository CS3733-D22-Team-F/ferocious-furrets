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
      // popUpLabel.setTextFill(Color.color(71.0, 236.0, 48.0));
      popUpLabel.setText("Login Successful");

    } else {
      // popUpLabel.setTextFill(Color.color(228.0, 70.0, 42.0));
      popUpLabel.setText("Wrong username or password, try again");
    }
  }
}
