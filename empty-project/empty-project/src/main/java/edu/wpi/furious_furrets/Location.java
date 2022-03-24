package edu.wpi.furious_furrets;

/*
    Java object corresponding to Locations SQL database
 */
public class Location {
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private int floor;
  private String building;
  private String nodeType;
  private String name;

  public Location(
      String nodeID,
      int xcoord,
      int ycoord,
      int floor,
      String building,
      String nodeType,
      String name) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.name = name;
  }
}
