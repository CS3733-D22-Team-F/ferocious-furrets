package edu.wpi.teamname;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class equipmentController {

  @FXML private Button backButton;

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  void returnHome(ActionEvent event) throws IOException {
    root = FXMLLoader.load((getClass().getResource("homePage.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1280, 720);
    stage.setScene(scene);
    stage.show();
  }
}
