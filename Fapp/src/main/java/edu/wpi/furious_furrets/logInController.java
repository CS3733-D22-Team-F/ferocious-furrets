package edu.wpi.teamname;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class logInController extends returnHomePage {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label popUpLabel;

  @FXML
  private void logIn() {
    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      popUpLabel.setText("Log In Success");
    } else {
      popUpLabel.setText("Wrong username or password, try again");
    }
  }
}
