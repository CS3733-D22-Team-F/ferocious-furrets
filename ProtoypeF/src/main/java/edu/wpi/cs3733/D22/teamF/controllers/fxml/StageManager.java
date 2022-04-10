package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Stage manager class, singleton so there can be one instance */
public class StageManager {

  // atributes
  private static StageManager m_StageManager = null;
  private static Stage m_stage; // the only stage utilized through the app m for manage stage
  @FXML JFXButton cancel;

  private StageManager() {} // private constructor for singleton

  /**
   * if instance is null creates and returns
   *
   * @return sole instance of a stage in app
   */
  public static StageManager getInstance() {
    if (m_StageManager == null) {
      m_stage = new Stage(); //
      m_stage.setTitle("Team F App");
      m_StageManager = new StageManager();
      m_stage
          .getIcons()
          .add(
              new Image(
                  StageManager.class.getResourceAsStream(
                      "/edu/wpi/cs3733/D22/teamF/BWHlogo-new.png")));
    }
    return m_StageManager;
  }

  /** take a string file name then set scene and display scene for StageManager */
  public void setDisplay(String filename) {
    m_stage.setScene(SceneManager.getInstance().setScene("views/" + filename));
    m_stage.show();
  }

  public void setDisplayAndWait(String filename) throws IOException {
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = SceneManager.getInstance().setScene("views/" + filename);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  /** makes current screen the home */
  public void setHomeScreen() {
    m_stage.setScene(SceneManager.getInstance().setScene("landingPage.fxml"));
    m_stage.show();
  }

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }
}
