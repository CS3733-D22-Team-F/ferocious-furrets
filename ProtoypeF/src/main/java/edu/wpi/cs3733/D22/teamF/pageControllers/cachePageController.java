package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Load;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class cachePageController implements Initializable {

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Task<Void> loader = new Load();
    loader.setOnSucceeded(
        e -> {
          try {
            Stage stage = SceneManager.getInstance().getStage();
            Scene scene =
                new Scene(FXMLLoader.load(Fapp.class.getResource("views/pageHolder.fxml")));
            stage.setScene(scene);
            stage.show();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        });
    Thread thread = new Thread(loader, "Starting App...");
    thread.start();
  }
}
