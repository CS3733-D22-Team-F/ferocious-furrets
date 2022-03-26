package edu.wpi.teamname;

import fxml.controllers.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class giftController {

  private Button backButton;
  private boolean isSent = true;

  @FXML
  // a boundry function for the checkbox sends a print if sent
  public void sendGift(ActionEvent click_me) {
    if (isSent) System.out.println("Sending Item!");
    isSent = !isSent;
  }

  public void returnHome(ActionEvent e) {
    // set scene using scene manager
    Scene scene = SceneManager.getInstance().setScene("homePage.fxml");
    // Creates a stage and sets it to the current window for manipulation
    // Needs ABSTRACTION next 3 lines stage process, will ballon to soon in code
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
