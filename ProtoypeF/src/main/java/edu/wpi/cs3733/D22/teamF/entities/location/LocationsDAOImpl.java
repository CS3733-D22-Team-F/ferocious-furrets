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
    DatabaseManager.dropTableIfExist("Locations");
    DatabaseManager.runStatement(
        "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    List<String> lines = CSVReader.readResourceFilepath(Filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      Location addedLocation = makeObjectFromString(currentLine);
      locations.add(addedLocation);
    }

    for (Location currentLocation : locations) {
      DatabaseManager.runStatement(currentLocation.generateInsertStatement());
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
    DatabaseManager.dropTableIfExist("Locations");
    DatabaseManager.runStatement(
        "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      Location addedLocation = makeObjectFromString(currentLine);
      locations.add(addedLocation);
    }

    for (Location currentLocation : locations) {
      DatabaseManager.runStatement(currentLocation.generateInsertStatement());
    }
    Cache.updateDBCache(Cache.DBType.DBT_LOC);
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
    Statement stm = DatabaseManager.getConn().createStatement();
    String q = "SELECT * FROM LOCATIONS";
    ResultSet rset = stm.executeQuery(q);
    ArrayList<Location> allLocations = locationsFromRSET(rset);
    rset.close();
    stm.close();
    return allLocations;
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
    DatabaseManager.runStatement(newLocation.generateInsertStatement());
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
    DatabaseManager.runStatement(String.format("DELETE FROM Locations WHERE nodeID = '%s'", nID));
  }

  /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateLocation(String oldNodeID, Location updatedLocation) throws SQLException {
    deleteLocation(oldNodeID);
    addLocation(updatedLocation);
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

  /*
  /**
   * Taking User input for the name of a CSV file. The program first loads all of the contents of
   * the SQL Location table into Java Location objects. Then the CSV file is created from the Java
   * objects.
   * @deprecated
  public void saveLocationToCSV() throws SQLException {

    String csvName = "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/TowerLocations.csv";

    Statement stm = null;
    try {
      stm = DatabaseManager.getConn().createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {

      ResultSet rset;
      rset = stm.executeQuery("SELECT * FROM Locations");

      ArrayList<Location> allLocations = locationsFromRSET(rset);

      rset.close();
      File newCSV = new File(csvName);
      FileWriter fw = new FileWriter(csvName);
      fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
      for (Location l : allLocations) {
        fw.write(
                l.getNodeID()
                        + ","
                        + l.getXcoord()
                        + ","
                        + l.getYcoord()
                        + ","
                        + l.getFloor()
                        + ","
                        + l.getBuilding()
                        + ","
                        + l.getNodeType()
                        + ","
                        + l.getLongName()
                        + ","
                        + l.getShortName()
                        + "\n");
      }
      fw.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    stm.close();
  }
  */
  /*
   public ArrayList<Location> getLocations() {
     return Locations;
   }

   public void setLocations(ArrayList<Location> locations) {
     this.Locations = locations;
   }

   public ArrayList<Location> getUpdatedLocations() {
     return updatedLocations;
   }

   public void setUpdatedLocations(ArrayList<Location> updatedLocations) {
     this.updatedLocations = updatedLocations;
   }

   public ArrayList<String> getCsvIDS() {
     return csvIDS;
   }

   public void setCsvIDS(ArrayList<String> csvIDS) {
     this.csvIDS = csvIDS;
   }

   public TextField getNewCSVName() {
     return newCSVName;
   }

   public void setNewCSVName(TextField newCSVName) {
     this.newCSVName = newCSVName;
   }

   public TextField getNewLocNodeID() {
     return newLocNodeID;
   }

   public void setNewLocNodeID(TextField newLocNodeID) {
     this.newLocNodeID = newLocNodeID;
   }

   public TextField getOldLocNodeID() {
     return oldLocNodeID;
   }

   public void setOldLocNodeID(TextField oldLocNodeID) {
     this.oldLocNodeID = oldLocNodeID;
   }

   public TextField getNodeToUpdate() {
     return nodeToUpdate;
   }

   public void setNodeToUpdate(TextField nodeToUpdate) {
     this.nodeToUpdate = nodeToUpdate;
   }

   public TextField getNewFloor() {
     return newFloor;
   }

   public void setNewFloor(TextField newFloor) {
     this.newFloor = newFloor;
   }

   public TextField getNewLocType() {
     return newLocType;
   }

   public void setNewLocType(TextField newLocType) {
     this.newLocType = newLocType;
   }

   public TextField getNewXcoord() {
     return newXcoord;
   }

   public void setNewXcoord(TextField newXcoord) {
     this.newXcoord = newXcoord;
   }

   public TextField getNewYcoord() {
     return newYcoord;
   }

   public void setNewYcoord(TextField newYcoord) {
     this.newYcoord = newYcoord;
   }

   public TextField getNewLongName() {
     return newLongName;
   }

   public void setNewLongName(TextField newLongName) {
     this.newLongName = newLongName;
   }

   public TextField getNewShortName() {
     return newShortName;
   }

   public void setNewShortName(TextField newShortName) {
     this.newShortName = newShortName;
   }

  */
  /* JACKS OLD CODE
  /**
   * Replicates the initTable method but with a user specified filename, drops old Locations table
   * and recreates with specified file
   *
   * @param filename csv file that contains the location nodes replacing the old map
   * @throws IOException
   * @throws SQLException
   * @deprecated
  private void resetMapFromCSV(String filename) throws IOException, SQLException {
    csvLocations.clear();
    csvIDS.clear();
    updatedLocations.clear();
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                LocationsDAOImpl.class.getResourceAsStream(filename), StandardCharsets.UTF_8));
    String lineText;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String nID = data[0];
      int x = Integer.parseInt(data[1]);
      int y = Integer.parseInt(data[2]);
      String floor = data[3];
      String building = data[4];
      String nodeType = data[5];
      String longName = data[6];
      String shortName = data[7];
      Location l = new Location(nID, x, y, floor, building, nodeType, longName, shortName);
      csvLocations.add(l);
      csvIDS.add(l.getNodeID());
    }
    Statement stm = DatabaseManager.getConn().createStatement();
    DatabaseMetaData databaseMetadata = DatabaseManager.getConn().getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "LOCATIONS", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE LOCATIONS");
    }
    stm.execute(
        "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    for (Location currentLocation : csvLocations) {
      stm.execute(currentLocation.generateInsertStatement());
    }
    resultSet.close();
    stm.close();
  }

  public void initTable(File file) throws IOException, SQLException {
    csvLocations.clear();
    csvIDS.clear();
    updatedLocations.clear();
    BufferedReader lineReader = new BufferedReader(new FileReader(file));
    String lineText;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String nID = data[0];
      int x = Integer.parseInt(data[1]);
      int y = Integer.parseInt(data[2]);
      String floor = data[3];
      String building = data[4];
      String nodeType = data[5];
      String longName = data[6];
      String shortName = data[7];
      Location l = new Location(nID, x, y, floor, building, nodeType, longName, shortName);
      csvLocations.add(l);
      csvIDS.add(l.getNodeID());
    }
    Statement stm = DatabaseManager.getConn().createStatement();
    DatabaseMetaData databaseMetadata = DatabaseManager.getConn().getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "LOCATIONS", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE LOCATIONS");
    }
    stm.execute(
        "CREATE TABLE Locations (nodeID varchar(16) PRIMARY KEY, Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");

    for (Location currentLocation : csvLocations) {
      stm.execute(currentLocation.generateInsertStatement());
    }
    resultSet.close();
    stm.close();
  }

   */
  /*
    /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
  public void updateLocation() throws SQLException {

    String oldLocNodeID = nodeToUpdate.getText();
    String nFloor = newFloor.getText();
    String newType = newLocType.getText();
    int newX = Integer.parseInt(newXcoord.getText());
    int newY = Integer.parseInt(newYcoord.getText());
    String newLName = newLongName.getText();
    String newSName = newShortName.getText();

    String updatedID = "";

    Statement stm = DatabaseManager.getConn().createStatement();
    String cmd =
            "UPDATE Locations SET floor = '"
                    + newFloor
                    + "', nodeType = '"
                    + newLocType
                    + "', nodeID = '"
                    + updatedID
                    + "' WHERE nodeID = '"
                    + oldLocNodeID
                    + "'";
    stm.execute(cmd);
    stm.close();
  }
   */

}
