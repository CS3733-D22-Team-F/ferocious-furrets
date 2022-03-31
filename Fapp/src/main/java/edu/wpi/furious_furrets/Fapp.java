package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.StageManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Fapp extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
