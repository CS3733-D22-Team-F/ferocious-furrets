package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** its says in title */
public class returnHomePage {
  private Stage stage;
  private Scene scene;
  private Parent root;

  /**
   * goes back to home page
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  private void backToHomePage(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("homePage.fxml");
  }
}
