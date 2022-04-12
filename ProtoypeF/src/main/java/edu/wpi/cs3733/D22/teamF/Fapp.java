package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/** its pronounces Fapp not fap */
@Slf4j
public class Fapp extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    StageManager.getInstance().setDisplay("logInPage.fxml");
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
