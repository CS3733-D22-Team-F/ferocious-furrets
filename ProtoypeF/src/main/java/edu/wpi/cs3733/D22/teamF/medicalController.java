package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;

/**
 * controller for medical equipment/lab request scene
 *
 * @see returnHomePage
 * @see Initializable
 */
public class medicalController extends returnHomePage implements Initializable {

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
    StageManager.getInstance().setDisplay("labRequestPage.fxml");
  }

  /**
   * switch to the scan scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToScan(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("scanPage.fxml");
  }

  /**
   * inits TODO?
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // BackgroundImage scanImage = new BackgroundImage(new
    // Image(getClass().getResource("Images/Scanimage.jpeg")));
    // scanButton.setStyle("-fx-background-image: 'Images/Scanimage.jpeg'");
  }
}
