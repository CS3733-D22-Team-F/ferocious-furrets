package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapLocationModifier {
  public static String currentFloor;

  public static Location addLocation(
      String type, String x, String y, String floor, String longName, String shortName) {
    try {
      String nID =
          generateNodeID(type, floor, (int) Double.parseDouble(x), (int) Double.parseDouble(y));
      Location l =
          new Location(
              nID,
              (int) Double.parseDouble(x),
              (int) Double.parseDouble(y),
              floor,
              "Tower",
              type.substring(0, 4),
              longName,
              shortName);
      DatabaseManager.getLocationDAO().addLocation(l);
      mapUserHistory.userHistory.add(new MapOperation("add", l));
      return l;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Location addLocation(Location location) throws SQLException {
    mapUserHistory.userHistory.add(new MapOperation("add", location));
    DatabaseManager.getLocationDAO().addLocation(location);
    mapUserHistory.userHistory.add(new MapOperation("add", location));
    return location;
  }

  public static void deleteLocation(Location location) throws SQLException {
    mapUserHistory.userHistory.add(new MapOperation("delete", location));
    MapIconModifier.deleteIcon(location);
    DatabaseManager.getLocationDAO().deleteLocation(location.getNodeID());
  }

  /**
   * @param nodeType
   * @param floor
   * @param x
   * @param y
   * @return
   * @throws SQLException
   * @throws IOException
   *     <p>Algorithm to create primary key nodeID for Location object following naming standards
   *     specified
   */
  public static String generateNodeID(String nodeType, String floor, int x, int y)
      throws SQLException, IOException {
    String nNodeType = nodeType.substring(0, 4);
    String nFloor = floor;
    int roomNum = 1;
    String rNum;

    ResultSet rset =
        DatabaseManager.runQuery(
            "SELECT * FROM Locations WHERE nodeType = '"
                + nNodeType
                + "' AND floor = '"
                + nFloor
                + "'");
    while (rset.next()) {
      roomNum++;
    }
    rset.close();
    if ((roomNum / 10.0) >= 10) {
      rNum = "" + roomNum;
    } else if ((roomNum / 10.0) >= 1) {
      rNum = "0" + roomNum;
    } else {
      rNum = "00" + roomNum;
    }

    switch (nFloor) {
      case "3":
        nFloor = "03";
        break;
      case "2":
        nFloor = "02";
        break;
      case "1":
        nFloor = "01";
        break;
      case "L1":
        nFloor = "L1";
        break;
      case "L2":
        nFloor = "L2";
        break;
      default:
        break;
    }

    String nID;
    nID = "f" + nNodeType + rNum + nFloor;
    //    stm.close();
    return nID;
  }
}
