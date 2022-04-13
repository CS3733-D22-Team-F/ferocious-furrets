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

    try {
      SceneManager.getInstance().loadViews("views/giftRequestQueue.fxml");
    } catch (Exception e) {
      System.out.println("Gift Queue Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/labRequestPage.fxml");
    } catch (Exception e) {
      System.out.println("Lab Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/landingPage.fxml");
    } catch (Exception e) {
      System.out.println("Landing Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/mealPage.fxml");
    } catch (Exception e) {
      System.out.println("Meal Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/medicalPage.fxml");
    } catch (Exception e) {
      System.out.println("Medical Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/medicinePage.fxml");
    } catch (Exception e) {
      System.out.println("Medicine Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/requestListPage.fxml");
    } catch (Exception e) {
      System.out.println("Request Queue Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/scanPage.fxml");
    } catch (Exception e) {
      System.out.println("Scan Page Loading Error");
      e.printStackTrace();
    }
    try {
      SceneManager.getInstance().loadViews("views/employee/employeePage.fxml");
    } catch (Exception e) {
      System.out.println("Employee Page Loading Error");
      e.printStackTrace();
    }
  }

  public static void loadIcons() throws IOException {
    InputStream file = Fapp.class.getResourceAsStream("Map/Icons/MapMenuSVG/home.svg");
    MapIconModifier.iconLoader.loadSvg(file);
  }
}
