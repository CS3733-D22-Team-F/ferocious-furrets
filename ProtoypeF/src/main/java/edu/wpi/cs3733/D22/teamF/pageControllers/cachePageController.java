package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Load;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

public class cachePageController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Task<Void> loader = new Load();
    loader.setOnSucceeded(
        e -> {
          StageManager.getInstance().setDisplay("logInPage.fxml");
        });
    Thread thread = new Thread(loader, "Starting App...");
    thread.start();
  }
}
