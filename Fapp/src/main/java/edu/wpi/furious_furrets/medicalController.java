package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class medicalController extends returnHomePage {

  private Stage stage;
  private Scene scene;

  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("labRequestPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToScan(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("scanPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
