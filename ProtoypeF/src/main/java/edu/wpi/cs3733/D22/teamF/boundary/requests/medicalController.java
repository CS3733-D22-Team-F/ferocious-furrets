package edu.wpi.cs3733.D22.teamF.boundary.requests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * controller for medical equipment/lab request scene
 *
 * @see Initializable
 */
public class medicalController {

  private Stage stage;
  private Scene scene;

  @FXML JFXButton lab = new JFXButton();
  @FXML JFXButton scan = new JFXButton();

  // @FXML private JFXButton scanButton;

  /**
   * switch to the lab scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("request/labRequestPage.fxml");
  }

  /**
   * switch to the scan scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToScan(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("request/scanPage.fxml");
  }

  /**
   * Switch to the home scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHome();
  }
}