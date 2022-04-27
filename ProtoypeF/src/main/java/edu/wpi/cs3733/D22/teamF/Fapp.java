package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.arduino.ArduinoConnection;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/** its pronounced Fapp not fap */
@Slf4j
public class Fapp extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  /**
   * can't stop, won't stop. except for on May 3rd when we'll stop and never start again
   *
   * @param primaryStage
   * @throws IOException
   * @throws SQLException
   */
  @Override
  public void start(Stage primaryStage) throws IOException, SQLException, InterruptedException {
    ArduinoConnection.getArduinoConnection().startConnectionSearchPorts();

    FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource("views/logInPage.fxml"));
    Cache.startDB(true);
    UserType.setUserType("admin");
    Scene scene = null;
    try {
      scene = new Scene(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    SceneManager.getInstance().setStage(primaryStage);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
