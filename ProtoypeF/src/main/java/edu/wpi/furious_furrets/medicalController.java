package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * controller for medical scene
 *
 * @see returnHomePage
 */
public class medicalController extends returnHomePage {

  private Stage stage;
  private Scene scene;

  /**
   * switch to the lab scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("labRequestPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to the scan scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToScan(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("scanPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
