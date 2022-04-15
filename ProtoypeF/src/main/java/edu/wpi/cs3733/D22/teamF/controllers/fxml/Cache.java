package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import java.io.*;

public class Cache {
  // load everything

  public static boolean isDataUpdate;

  public static void loadViews() {
    SceneManager.getInstance().loadViews("views/mapPage.fxml");
    SceneManager.getInstance().loadViews("views/equipmentPage.fxml");
    SceneManager.getInstance().loadViews("views/giftPage.fxml");
    SceneManager.getInstance().loadViews("views/giftRequestQueue.fxml");
    SceneManager.getInstance().loadViews("views/labRequestPage.fxml");
    SceneManager.getInstance().loadViews("views/landingPage.fxml");
    SceneManager.getInstance().loadViews("views/mealPage.fxml");
    SceneManager.getInstance().loadViews("views/medicalPage.fxml");
    SceneManager.getInstance().loadViews("views/medicinePage.fxml");
    SceneManager.getInstance().loadViews("views/requestListPage.fxml");
    SceneManager.getInstance().loadViews("views/scanPage.fxml");
  }

  public static void loadIcons() throws IOException {
    InputStream file =
        Fapp.class.getResourceAsStream(
            "/edu/wpi/cs3733/D22/teamF/views/Map/Icons/MapMenuSVG/home.svg");
    MapIconModifier.iconLoader.loadSvg(file);
  }
}
