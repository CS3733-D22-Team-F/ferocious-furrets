package edu.wpi.cs3733.D22.teamF.entities.location;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

/**
 * Java object corresponding to Locations SQL database
 *
 * <p>Primary Key, node identifier Xcoord- x coordinate of Location node Ycoord- y coordinate of
 * Location node Floor- floor Location is on Building- the building in which the Location is
 * NodeType- denotes the subtype of Location node LongName- name of Location, 255 char limit
 * ShortName- abbreviated name of Location, 128 char limit
 */
public class Location extends RecursiveTreeObject<Location> {

  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;

  /**
   * Constructor
   *
   * @param nodeID primary key
   */
  public Location(String nodeID) {
    this.nodeID = nodeID;
  }

  /**
   * Constructor for location object
   *
   * <p>Primary Key, node identifier Xcoord- x coordinate of Location node * * Ycoord- y coordinate
   * of Location node Floor- floor Location is on Building- the building in * * which the Location
   * is NodeType- denotes the subtype of Location node LongName- name of * * Location, 255 char
   * limit ShortName- abbreviated name of Location, 128 char limit
   *
   * @param nodeID
   * @param xcoord
   * @param ycoord
   * @param floor
   * @param building
   * @param nodeType
   * @param LongName
   * @param ShortName
   * @see Location
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
   *
   * @return string of ID
   * @see Location
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * sets the nodeID of the location
   *
   * @param nodeID String
   * @see Location
   */
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  /**
   * gets Xcoord
   *
   * @return int
   * @see Location
   */
  public int getXcoord() {
    return xcoord;
  }

  /**
   * sets xcoord
   *
   * @param xcoord
   * @see Location
   */
  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
  }

  /**
   * gets y coord
   *
   * @return int
   * @see Location
   */
  public int getYcoord() {
    return ycoord;
  }

  /**
   * Sets y coord
   *
   * @param ycoord int
   * @see Location
   */
  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
  }

  /**
   * gets floor
   *
   * @return String
   * @see Location
   */
  public String getFloor() {
    return floor;
  }

  /**
   * Sets floor
   *
   * @param floor String
   * @see Location
   */
  public void setFloor(String floor) {
    this.floor = floor;
  }

  /**
   * gets Building
   *
   * @return String
   * @see Location
   */
  public String getBuilding() {
    return building;
  }

  /**
   * sets building
   *
   * @param building String
   * @see Location
   */
  public void setBuilding(String building) {
    this.building = building;
  }

  /**
   * gets the nodeType
   *
   * @return String
   * @see Location
   */
  public String getNodeType() {
    return nodeType;
  }

  /**
   * sets the nodeType
   *
   * @param nodeType String
   * @see Location
   */
  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  /**
   * gets the longname
   *
   * @return String
   * @see Location
   */
  public String getLongName() {
    return longName;
  }

  /**
   * sets the long name
   *
   * @param longName String
   */
  public void setLongName(String longName) {
    this.longName = longName;
  }

  /**
   * gets the short name
   *
   * @return String
   * @see Location
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * sets the shortName
   *
   * @param shortName String
   * @see Location
   */
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * @Method generateInsertStatement() SQL statement that INSERTs Location object into SQL table
   *
   * @return String
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
