package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class LocTempHolder {
  private static Location location;

  private static AnchorPane passIconPane;

  private static TableView<Location> locationTable;

  public static Location getLocation() {
    return location;
  }

  public static void setLocation(Location location) {
    LocTempHolder.location = location;
  }

  public static AnchorPane getPassIconPane() {
    return passIconPane;
  }

  public static void setPassIconPane(AnchorPane passIconPane) {
    LocTempHolder.passIconPane = passIconPane;
  }

  public static TableView<Location> getLocationTable() {
    return locationTable;
  }

  public static void setLocationTable(TableView<Location> locationTale) {
    LocTempHolder.locationTable = locationTale;
  }
}
