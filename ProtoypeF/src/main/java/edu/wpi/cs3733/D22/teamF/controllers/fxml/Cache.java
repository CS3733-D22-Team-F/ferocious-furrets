package edu.wpi.cs3733.D22.teamF.controllers.fxml;

public class Cache {
  // load everything

  public static void loadViews() {
    SceneManager.getInstance().loadViews("mapPage.fxml");
    System.out.println("TEST LOAD MAP");
  }
}
