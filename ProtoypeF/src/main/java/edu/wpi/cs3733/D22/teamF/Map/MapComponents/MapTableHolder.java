package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * object for a holder for the map tables
 */
public class MapTableHolder {
  /**
   * converts an equipment object to a temporary location to get the x-y coords to display icon on
   * map
   *
   * @param medList ArrayList </equipment>
   * @return ArrayList </Location>
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
}
