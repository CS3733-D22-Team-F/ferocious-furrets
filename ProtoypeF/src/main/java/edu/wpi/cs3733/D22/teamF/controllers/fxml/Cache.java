package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import java.io.*;
import java.sql.SQLException;

public class Cache {
  // load everything

  public static boolean isDataUpdate;

  public static void loadViews() throws SQLException, IOException {
    DatabaseManager.switchConnection(DatabaseInitializer.ConnType.EMBEDDED);
    try {
      SceneManager.getInstance().loadViews("views/mapPage.fxml");
    } catch (Exception e) {
      System.out.println("Map Page Loading Error");
      e.printStackTrace();
    }

    try {
      SceneManager.getInstance().loadViews("views/equipmentPage.fxml");
    } catch (Exception e) {
      System.out.println("Equipment Page Loading Error");
      e.printStackTrace();
    }

    try {
      SceneManager.getInstance().loadViews("views/giftPage.fxml");
    } catch (Exception e) {
      System.out.println("Gift Page Loading Error");
      e.printStackTrace();
    }

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
    InputStream file = Fapp.class.getResourceAsStream("Map/Icons/MapMenuSVG/home.svg");
    MapIconModifier.iconLoader.loadSvg(file);
  }
}
