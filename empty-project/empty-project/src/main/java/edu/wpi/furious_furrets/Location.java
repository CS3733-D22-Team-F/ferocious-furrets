package edu.wpi.furious_furrets;

/*
   Java object corresponding to Locations SQL database
*/
public class Location {
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private String nodeType;
  private String LongName;
  private String ShortName;

  public Location(String nodeID) {
    this.nodeID = nodeID;
  }

  public Location(
      String nodeID,
      int xcoord,
      int ycoord,
      String floor,
      String building,
      String nodeType,
      String LongName,
      String ShortName) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.LongName = LongName;
    this.ShortName = ShortName;
  }
}
