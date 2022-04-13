package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.sql.SQLException;

public class MapEquipmentModifier {

  public static void modifyEquipment(String nodeID, String status, String equipID)
      throws SQLException {
    String cmd =
        String.format(
            "UPDATE MEDICALEQUIPMENT SET nodeID = '%s', status = '%s' WHERE EQUIPID = '%s'",
            nodeID, status, equipID);
    DatabaseManager.runStatement(cmd);
  }

  public static void deleteEquipment() throws SQLException {
    // mapUserHistory.userHistory.add(new MapOperation("delete", location));
    Location l = locTempHolder.getLocation();
    MapIconModifier.deleteIcon(l);
    DatabaseManager.getMedEquipDAO().deleteMedEquip(l.getShortName());
  }
}
