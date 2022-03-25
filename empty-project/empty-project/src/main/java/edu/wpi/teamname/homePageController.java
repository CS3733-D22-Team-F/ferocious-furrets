package edu.wpi.teamname;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homePageController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("labRequestPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToEquipment(ActionEvent event) throws IOException {
    root = FXMLLoader.load((getClass().getResource("equipmentPage.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1280, 720);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToMeals(ActionEvent event) throws IOException {}
}
