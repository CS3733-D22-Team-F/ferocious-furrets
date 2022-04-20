package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import edu.wpi.cs3733.D22.teamF.Fapp;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.stage.Stage;

/** Scene manager class, singleton so there can only be one instance */
public class SceneManager {
  // <> generic doesn't care accepts all key,object type pair
  // smart compiler <> alt + enter
  private HashMap<String, SubScene> h_map = new HashMap<>();
  private static SceneManager m_SceneManager = null;
  private String currentScene = "";
  private Stage currentStage;

  private SceneManager() {}

  /**
   * if instance is null creates
   *
   * @return the sole instance
   */
  public static SceneManager getInstance() {
    // separation for ease of control i.e initialization method
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

  /*
   * Singleton implementation with global access
   */
  public SubScene setScene(String filename) throws IOException { // highlight tab /shift+tab
    SubScene scene = null;
    /*
     * Checks the hashmap to see if filename already exists.
     */
    if (!h_map.containsKey(filename)) {
      FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource(filename));
      // alt enter while hover over/cursor in red
      scene = new SubScene(fxmlLoader.load(), 1220, 660);
      h_map.put(filename, scene); // Hashes the filename path into a scene object
      //      System.out.println("Loading Scene: " + filename);
    } else {
      scene = h_map.get(filename);
      //      System.out.println("Load: " + filename + " from hash map");
    }
    currentScene = filename;
    return scene;
  }

  public void loadViews(String path) {
    FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource(path));
    // alt enter while hover over/cursor in red
    try {
      SubScene scene = new SubScene(fxmlLoader.load(), 1220, 660);
      h_map.put(path, scene); // Hashes the filename path into a scene object
    } catch (IOException e) {
      e.printStackTrace();
    }
    //    System.out.println("Loading Scene: " + path);
  }

  public Stage getStage() {
    return currentStage;
  }

  public void setStage(Stage stage) {
    currentStage = stage;
  }
}
