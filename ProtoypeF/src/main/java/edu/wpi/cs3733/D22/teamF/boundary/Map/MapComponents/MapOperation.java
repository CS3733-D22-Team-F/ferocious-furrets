package edu.wpi.cs3733.D22.teamF.boundary.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.entities.location.Location;

/** object for different map operatoins */
public class MapOperation {
  private String type;
  private Location location;

  /**
   * Contructor
   *
   * @param type String
   * @param location Location
   */
  public MapOperation(String type, Location location) {
    this.type = type;
    this.location = location;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
