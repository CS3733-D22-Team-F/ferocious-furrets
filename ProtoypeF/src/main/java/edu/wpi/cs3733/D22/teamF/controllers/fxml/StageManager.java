package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** Stage manager class, singleton so there can be one instance */
public class StageManager {

  // atributes
  private static StageManager m_StageManager = null;
  private static Stage m_stage; // the only stage utilized through the app m for manage stage

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
    m_stage.setScene(SceneManager.getInstance().setScene(filename));
    m_stage.show();
  }

  /**
   * takes a scene and displays it
   *
   * @param filename
   */
  public void setDisplay(Scene filename) {
    m_stage.setScene(filename);
    m_stage.show();
  }

  /** makes current screen the home */
  public void setHomeScreen() {
    m_stage.setScene(SceneManager.getInstance().setScene("homePage.fxml"));
    m_stage.show();
  }
}
