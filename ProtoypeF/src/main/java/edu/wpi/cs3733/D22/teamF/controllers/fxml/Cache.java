package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import static org.reflections.scanners.Scanners.Resources;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class Cache {
  // load everything

  public static boolean isDataUpdated = false;

  public static void loadViews() throws SQLException, IOException {
    // Views that can't be cached
    ArrayList<String> exceptions =
        new ArrayList<>(List.of("views/logInPage.fxml", "views/cachePage.fxml"));

    Reflections reflections = new Reflections("edu.wpi.cs3733.D22.teamF.views", Scanners.values());
    Set<String> fxmlPaths = reflections.get(Resources.with(".*\\.fxml"));

    for (String path : fxmlPaths) {
      path = path.substring(25); // Strip "edu/wpi/cs3733/D22/teamF/"
      if (exceptions.contains(path)) continue; // Skip pages that can't be cached

      try {
        SceneManager.getInstance().loadViews(path);
      } catch (Exception e) {
        System.out.println("Loading Error: " + path);
        e.printStackTrace();
      }
    }
  }

  public static void loadIcons() throws IOException {
    InputStream file = Fapp.class.getResourceAsStream("Map/Icons/MapMenuSVG/home.svg");
    MapIconModifier.iconLoader.loadSvg(file);
  }
}
