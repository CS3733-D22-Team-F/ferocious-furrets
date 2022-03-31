package edu.wpi.furious_furrets.controllers.fxml;

import edu.wpi.furious_furrets.Fapp;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneManager {
  // <> generic doesn't care accepts all key,object type pair
  // smart compiler <> alt + enter
  private HashMap<String, Scene> h_map = new HashMap<>();
  private static SceneManager m_SceneManager = null;
  private String currentScene = "";

  private SceneManager() {}

  /**
   * if instance is null creates
   *
   * @return the sole instance
   */
  public static SceneManager getInstance() {
    // seperation for ease of control i.e intilization method
    if (m_SceneManager == null) m_SceneManager = new SceneManager();
    return m_SceneManager;
  }

  public String getCurrentScene() {
    return currentScene;
  }

  /**
   * Creates a scene and adds to hash table if scene already exists gets it from hash instead
   *
   * @param filename Takes a string to acess fxml file/scene
   * @return
   */
  public Scene setScene(String filename) // acess from anywhere global
      { // higlight tab /shift+tab
    Scene scene = null;

    // checks in hash
    if (!h_map.containsKey(filename)) {
      FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource(filename));
      // alt enter while hover over/cursor in red
      try {
        scene = new Scene(fxmlLoader.load());
      } catch (IOException e) {
        e.printStackTrace();
      }
      h_map.put(filename, scene); // hashing any object hence generic
      System.out.println("Loading Scene");
    } else {
      scene = h_map.get(filename);
      System.out.println("Load from hash map");
    }
    currentScene = filename;
    return scene;
  }
}
