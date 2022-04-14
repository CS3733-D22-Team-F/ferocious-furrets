package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;

public class mainController implements Initializable {

  @FXML StackPane pageHolder;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    SubScene scene = SceneManager.getInstance().setScene("views/mealPage.fxml");

    pageHolder.getChildren().addAll(scene);
  }
}
