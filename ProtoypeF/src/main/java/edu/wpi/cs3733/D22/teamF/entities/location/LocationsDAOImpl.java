package edu.wpi.cs3733.D22.teamF.entities.location;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Implementation of the locations DAO including necessary methods for saving, deleting, updating,
 * and adding new locations.
 *
 * @version 1.0
 */
public class LocationsDAOImpl implements LocationDAO {

  // private ArrayList<Location> Locations = new ArrayList<Location>(); // LIST OF LOCATIONS RIGHT
  // NOW

  @FXML private TextField newCSVName;
  @FXML private TextField newLocNodeID;
  @FXML private TextField oldLocNodeID;
  @FXML private TextField nodeToUpdate;
  @FXML private TextField newFloor;
  @FXML private TextField newLocType;
  @FXML private TextField newXcoord;
  @FXML private TextField newYcoord;
  @FXML private TextField newLongName;
  @FXML private TextField newShortName;

  /** Constructor that takes in a Connection object */
  public LocationsDAOImpl() {}

  /**
   * Method that initalizes all the tables for SQL and makes objects and adds them to the arrayList
   *
   * @param Filepath String filepath of csv file from resources
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String Filepath) throws SQLException, IOException {
    Cache.clearLocations();
    ArrayList<Location> locations = new ArrayList<>();
    DatabaseManager.getInstance().dropTableIfExist("Locations");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    List<String> lines = CSVReader.readResourceFilepath(Filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      Location addedLocation = makeObjectFromString(currentLine);
      locations.add(addedLocation);
    }

    for (Location currentLocation : locations) {
      DatabaseManager.getInstance().runStatement(currentLocation.generateInsertStatement());
    }
    Cache.updateDBCache(Cache.DBType.DBT_LOC);
  }
  /**
   * Method that initalizes all the tables for SQL and makes objects and adds them to the arrayList
   *
   * @param file file of chosen file
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(File file) throws SQLException, IOException {
    Cache.clearLocations();
    ArrayList<Location> locations = new ArrayList<>();
    DatabaseManager.getInstance().dropTableIfExist("Locations");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      Location addedLocation = makeObjectFromString(currentLine);
      locations.add(addedLocation);
    }

    for (Location currentLocation : locations) {
      DatabaseManager.getInstance().runStatement(currentLocation.generateInsertStatement());
    }
    Cache.updateDBCache(Cache.DBType.DBT_LOC);
  }

  public String nodeIDToName(String nID) throws SQLException {
    String cmd = String.format("SELECT longName FROM Locations WHERE nodeID = '%s'", nID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("longName");
    }
    rset.close();
    return lName;
  }

  public String NameToLocation(String longName) throws SQLException {
    String cmd = String.format("SELECT * FROM Locations WHERE longName = '%s'", longName);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String nID = "";
    while (rset.next()) {
      nID = rset.getString("nodeID");
    }
    rset.close();
    return nID;
  }

  /**
   * Saves the Locations' table to a csv file
   *
   * @param fileDir is the name of the file the map will be backed up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException {

    ArrayList<String> toAdd = new ArrayList<>();
    toAdd.add("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
    for (Location l : Cache.getLocationsCache()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s,%s,%s",
              l.getNodeID(),
              l.getXcoord(),
              l.getYcoord(),
              l.getFloor(),
              l.getBuilding(),
              l.getNodeType(),
              l.getLongName(),
              l.getShortName()));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File filename) throws SQLException, IOException {

    ArrayList<String> toAdd = new ArrayList<>();
    toAdd.add("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
    for (Location l : Cache.getLocationsCache()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s,%s,%s",
              l.getNodeID(),
              l.getXcoord(),
              l.getYcoord(),
              l.getFloor(),
              l.getBuilding(),
              l.getNodeType(),
              l.getLongName(),
              l.getShortName()));
    }

    CSVWriter.writeAll(filename, toAdd);
  }

  /**
   * Taking in a ResultSet object take the locations in the form of new Location object
   *
   * @param rset ResultSet object to get locations from
   * @throws SQLException
   */
  public ArrayList<Location> locationsFromRSET(ResultSet rset) throws SQLException {
    ArrayList<Location> allLocations = new ArrayList<Location>();
    while (rset.next()) {
      String nodeID = rset.getString("nodeID");
      String longName = rset.getString("LongName");
      String shortName = rset.getString("ShortName");
      int xCoord = rset.getInt("xcoord");
      int yCoord = rset.getInt("ycoord");
      String floor = rset.getString("floor");
      String building = rset.getString("building");
      String nodeType = rset.getString("nodeType");
      Location newL =
          new Location(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
      allLocations.add(newL);
    }
    rset.close();
    return allLocations;
  }

  /**
   * Displays the list of location nodes along with their attributes.
   *
   * @throws SQLException
   * @returns ArrayList of type Locations
   * @see Location
   */
  public ArrayList<Location> getAllLocationsFromDB() throws SQLException {
    Statement stm = DatabaseManager.getInstance().getDatabaseConnection().createStatement();
    String q = "SELECT * FROM LOCATIONS";
    ResultSet rset = stm.executeQuery(q);
    ArrayList<Location> allLocations = locationsFromRSET(rset);
    rset.close();
    stm.close();
    return allLocations;
  }

  public ResultSet get() throws SQLException {
    Statement stm = DatabaseManager.getInstance().getDatabaseConnection().createStatement();
    String q = "SELECT * FROM LOCATIONS";
    ResultSet rset = stm.executeQuery(q);
    return rset;
  }

  /**
   * Taking user input for the ID of the new location node. A new Java Location object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   * @param newLocation
   */
  public void addLocation(Location newLocation) throws SQLException {
    Cache.addLocation(newLocation);
    DatabaseManager.getInstance().runStatement(newLocation.generateInsertStatement());
  }

  /**
   * Taking user input for the ID of the location node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   * @param nID
   */
  public void deleteLocation(String nID) throws SQLException {
    Cache.remLocation(nID);
    DatabaseManager.getInstance()
        .runStatement(String.format("DELETE FROM Locations WHERE nodeID = '%s'", nID));
  }

  /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateLocation(String oldNodeID, Location updatedLocation) throws SQLException {
    String cmd =
        String.format(
            "UPDATE LOCATIONS SET NODEID = '%s', XCOORD = %s, YCOORD = %s, FLOOR = '%s', BUILDING = '%s', NODETYPE = '%s', LONGNAME = '%s', SHORTNAME = '%s' WHERE NODEID = '%s'",
            updatedLocation.getNodeID(),
            updatedLocation.getXcoord(),
            updatedLocation.getYcoord(),
            updatedLocation.getFloor(),
            updatedLocation.getBuilding(),
            updatedLocation.getNodeType(),
            updatedLocation.getLongName(),
            updatedLocation.getShortName(),
            oldNodeID);
    DatabaseManager.getInstance().runStatement(cmd);
    Cache.updateDBCache(Cache.DBType.DBT_LOC);
  }

  /**
   * Updates arrayList in class from rset (EXPENSIVE FUNCTION)
   *
   * @throws SQLException
   */
  public void updateLocationsListFromDatabase() throws SQLException {
    Cache.updateDBCache(Cache.DBType.DBT_LOC);
  }

  /**
   * Make an location object from String (with commas) (Helper function)
   *
   * @param currentLine Line in CSV to take in as parameters for an object
   * @return return a Location object
   */
  public Location makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");
    String nID = currentLineSplit[0];
    int x = Integer.parseInt(currentLineSplit[1]);
    int y = Integer.parseInt(currentLineSplit[2]);
    String floor = currentLineSplit[3];
    String building = currentLineSplit[4];
    String nodeType = currentLineSplit[5];
    String longName = currentLineSplit[6];
    String shortName = currentLineSplit[7];
    return new Location(nID, x, y, floor, building, nodeType, longName, shortName);
  }
}
