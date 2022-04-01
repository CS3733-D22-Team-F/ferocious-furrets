package edu.wpi.furious_furrets.controllers;

import edu.wpi.furious_furrets.Fapp;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * do not use, use one in fxml folder //TODO get rid of
 *
 * @deprecated
 */
public class SceneManager {
  // <> generic doesn;t care accepts all key,object type pair
  // smart compiler <> alt + enter
  private HashMap<String, Scene> h_map = new HashMap<>();
  private static SceneManager m_SceneManager = null;

  private SceneManager() {}

  // sole and only instance of scene manger

  /**
   * @deprecated
   * @return
   */
  private static SceneManager getInstance() {
    // seperation for ease of control i.e intilization method
    if (m_SceneManager == null) m_SceneManager = new SceneManager();
    return m_SceneManager;
  }

  /**
   * deprecated do not use,
   *
   * @deprecated
   * @param filename
   * @return
   */
  public Scene setScene(String filename) // acess from anywhere global
      { // higlight tab /shift+tab
    Scene scene = null;

    if (!h_map.containsKey(filename)) {
      FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource(filename));
      // alt enter while hover over/cursor in red
      try {
        scene = new Scene(fxmlLoader.load());
      } catch (IOException e) {
        e.printStackTrace();
      }
      h_map.put(filename, scene); // hashing any object hence generic

      // sout then enter System.out.println();
      System.out.println("Loading Scene");
    } else {
      scene = h_map.get(filename);
      System.out.println("Load from hash map");
    }
    return scene;
  }
}
