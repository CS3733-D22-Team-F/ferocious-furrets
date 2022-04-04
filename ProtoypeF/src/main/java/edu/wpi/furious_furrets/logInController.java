package edu.wpi.furious_furrets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
      popUpLabel.setTextFill(Color.color(71, 236, 48));
      popUpLabel.setText("Login Successful");

    } else {
      // popUpLabel.setTextFill(Color.color(228, 70, 42));
      popUpLabel.setText("Wrong username or password, try again");
    }
  }
}
