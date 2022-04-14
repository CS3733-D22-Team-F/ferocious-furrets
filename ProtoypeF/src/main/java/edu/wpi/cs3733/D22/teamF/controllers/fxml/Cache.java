package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import static org.reflections.scanners.Scanners.Resources;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class Cache {
  // load everything

  private static boolean isLocationsUpdated = false;
  private static ArrayList<Location> locationsCache = new ArrayList<>();
  private static HashMap<String, Location> locationsCacheMap = new HashMap<>();

  public enum DBType {
    DBT_ALL,
    DBT_LOC
  }

  public static void loadViews() throws SQLException, IOException {
    DatabaseManager.switchConnection(DatabaseInitializer.ConnType.EMBEDDED);

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

  public static void loadIcons(){
    InputStream file = Fapp.class.getResourceAsStream("Map/Icons/MapMenuSVG/home.svg");
    MapIconModifier.iconLoader.loadSvg(file);
  }

  /*
     Database Cache Setters/Getters
  */
  public static ArrayList<Location> getLocationsCache() {
    if (isLocationsUpdated) {
      try {
        updateDBCache(DBType.DBT_LOC);
      } catch (Exception e) {
        System.out.println("ERROR! Couldn't update locations!");
        e.printStackTrace();
        return null;
      }
    }
    return locationsCache;
  }

  public static Location getLocation(String nodeID) {
    if (isLocationsUpdated) {
      try {
        updateDBCache(DBType.DBT_LOC);
      } catch (Exception e) {
        System.out.println("ERROR! Couldn't update locations!");
        e.printStackTrace();
        return null;
      }
    }
    return locationsCacheMap.get(nodeID);
  }

  public static void updateDBCache() throws SQLException {
    updateDBCache(DBType.DBT_ALL);
  }

  /*
     Database Cache Management
  */
  public static void updateDBCache(DBType type) throws SQLException {
    switch (type) {
      case DBT_LOC:
        locationsCache = DatabaseManager.getLocationDAO().getAllLocations();
        isLocationsUpdated = true;
        buildMaps(DBType.DBT_LOC);
      case DBT_ALL:
      default:
        locationsCache = DatabaseManager.getLocationDAO().getAllLocations();
        isLocationsUpdated = true;
        buildMaps(DBType.DBT_ALL);
    }
    System.out.println("Updated Cache");
  }

  private static void buildMaps(DBType type) {
    switch (type) {
      case DBT_LOC:
        locationsCacheMap = new HashMap<>();
        for (Location loc : locationsCache) {
          locationsCacheMap.put(loc.getNodeID(), loc);
        }
      case DBT_ALL:
      default:
        locationsCacheMap = new HashMap<>();
        for (Location loc : locationsCache) {
          locationsCacheMap.put(loc.getNodeID(), loc);
        }
    }
  }
}
