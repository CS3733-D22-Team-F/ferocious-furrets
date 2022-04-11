package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class MapTableHolder {
  /**
   * converts an equipment object to a temporary location to get the x-y coords to display icon on
   * map
   *
   * @param medList
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> equipToLocation(ArrayList<equipment> medList)
      throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    for (equipment med : medList) {
      specificID = med.getNodeID();
      Statement stm = DatabaseManager.getConn().createStatement();
      String cmd = "SELECT * FROM Locations WHERE nodeID = '" + specificID + "'";
      ResultSet rset = stm.executeQuery(cmd);
      while (rset.next()) {
        x = rset.getInt(2);
        y = rset.getInt(3);
        floor = rset.getString(4);
      }
      rset.close();
      Location tempLocation =
          new Location(med.getNodeID(), x, y, floor, "N/A", med.getEquipType(), "Equipment", "N/A");
      returnList.add(tempLocation);
    }
    return returnList;
  }

  public static void loadMap(TableView<Location> table, AnchorPane iconPane) throws SQLException {
    wipeMap();
    loadTable(table);
    displayMap(table, iconPane);
  }

  public static void loadTable(TableView<Location> table) throws SQLException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    oldLocs.addAll(eLocations);
    ObservableList<Location> nlocationList = FXCollections.observableList(oldLocs);
    table.setItems(nlocationList);
  }

  public static void wipeMap() throws SQLException {
    ArrayList<Location> oldLocs = new ArrayList<>(MapIconModifier.locationIconList.keySet());
    for (Location loc : oldLocs) {
      MapIconModifier.deleteIcon(loc);
    }
  }

  public static void displayMap(TableView<Location> table, AnchorPane iconPane)
      throws SQLException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();

    ArrayList<Location> nLocations = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eLocations = MapTableHolder.equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    nLocations.addAll(eLocations);
    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    for (Location lo : nLocations) {
      try {
        MapIconModifier.addIcon(table, iconPane, lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    MapIconModifier.showAllIcon();
  }
}
