package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.entities.location.Location;

public class nodeTempHolder {
  private static Location location;

  public static Location getLocation() {
    return location;
  }

  public static void setLocation(Location location) {
    nodeTempHolder.location = location;
  }
}
