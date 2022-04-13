package edu.wpi.cs3733.D22.teamF;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class mainController implements Initializable {

  @FXML StackPane pageHolder;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // pageHolder.getChildren().add(StageManager.getInstance().setDisplay("logInPage.fxml"));
  }
}
