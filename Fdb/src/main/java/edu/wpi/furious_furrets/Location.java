package edu.wpi.furious_furrets;

/**
   Java object corresponding to Locations SQL database
*/
public class Location {

  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;

  public Location(String nodeID) {
    this.nodeID = nodeID;
  }

  /**
   * Constructor for location object
   *
   * Primary Key, node identifier Xcoord- x coordinate of Location node
   *    *    * Ycoord- y coordinate of Location node Floor- floor Location is on Building- the building in
   *    *    * which the Location is NodeType- denotes the subtype of Location node LongName- name of
   *    *    * Location, 255 char limit ShortName- abbreviated name of Location, 128 char limit
   *
   * @param nodeID
   * @param xcoord
   * @param ycoord
   * @param floor
   * @param building
   * @param nodeType
   * @param LongName
   * @param ShortName
   */
  public Location(
      String nodeID, // primary key
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
    this.longName = LongName;
    this.shortName = ShortName;
  }

  /**
   * gets the ID of the Location Node
   * @return string of ID
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * sets the nodeID of the location
   * @param nodeID
   */
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public int getXcoord() {
    return xcoord;
  }

  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
  }

  public int getYcoord() {
    return ycoord;
  }

  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * @Method generateInsertStatement()
   *
   * @return SQL statement that INSERTs Location object into SQL table
   */
  public String generateInsertStatement() {
    return "INSERT INTO Locations VALUES ('"
        + this.nodeID
        + "',"
        + this.xcoord
        + ","
        + this.ycoord
        + ", '"
        + this.floor
        + "', '"
        + this.building
        + "', '"
        + this.nodeType
        + "', '"
        + this.longName
        + "', '"
        + this.shortName
        + "')";
  }
}
