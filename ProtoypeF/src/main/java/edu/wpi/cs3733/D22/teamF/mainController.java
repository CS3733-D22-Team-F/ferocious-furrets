package edu.wpi.cs3733.D22.teamF;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import lombok.SneakyThrows;

public class mainController implements Initializable {

  @FXML StackPane pageHolder;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Parent root = FXMLLoader.load(homePageController.class.getResource("homePage.fxml"));
    SubScene subscene = new SubScene(root, 1200, 720);
    pageHolder.getChildren().addAll(subscene);
  }
}
