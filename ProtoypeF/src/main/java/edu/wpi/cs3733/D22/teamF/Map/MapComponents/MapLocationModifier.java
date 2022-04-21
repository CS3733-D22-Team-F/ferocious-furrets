package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/** for modifying locations */
public class MapLocationModifier {
  public static String currentFloor;

  /**
   * adds a location taking in individual fields and making an object
   *
   * @param type String type of location
   * @param x String coord
   * @param y String coord
   * @param floor String floor number
   * @param longName String
   * @param shortName String abbrev
   */
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
      DatabaseManager.getInstance().getLocationDAO().addLocation(l);
      MapUserHistory.userHistory.add(new MapOperation("add", l));
      return l;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void modifyLocation(
      String nID, String type, String x, String y, String floor, String longName, String shortName)
      throws SQLException {
    if (type.length() > 4) {
      type = type.substring(0, 4);
    }
    Location l =
        new Location(
            nID,
            (int) Double.parseDouble(x),
            (int) Double.parseDouble(y),
            floor,
            "Tower",
            type,
            longName,
            shortName);
    DatabaseManager.getInstance().getLocationDAO().updateLocation(nID, l);
  }

  /**
   * adds a location taking in a location object
   *
   * @param location Location
   * @throws SQLException
   */
  public static Location addLocation(Location location) throws SQLException {
    MapUserHistory.userHistory.add(new MapOperation("add", location));
    DatabaseManager.getInstance().getLocationDAO().addLocation(location);
    MapUserHistory.userHistory.add(new MapOperation("add", location));
    return location;
  }

  public static void deleteLocation(Location location) throws SQLException {
    MapUserHistory.userHistory.add(new MapOperation("delete", location));
    MapIconModifier.deleteIcon(location);
    DatabaseManager.getInstance().getLocationDAO().deleteLocation(location.getNodeID());
  }

  /**
   * @param nodeType String type of node
   * @param floor String floor id
   * @param x String coord
   * @param y String coord
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
        DatabaseManager.getInstance()
            .runQuery(
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
