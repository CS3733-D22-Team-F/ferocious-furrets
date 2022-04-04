package edu.wpi.furious_furrets.database;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Implementation of the locations DAO including necessary methods for saving, deleting, updating,
 * and adding new locations.
 *
 * @version 1.0
 */
public class LocationsDAOImpl implements LocationDAO {

  private Connection connection;
  private ArrayList<Location> csvLocations = new ArrayList<Location>();
  private ArrayList<Location> updatedLocations = new ArrayList<Location>();
  private ArrayList<String> csvIDS = new ArrayList<String>();
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

  /**
   * Constructor that takes in a Connection object
   *
   * @param dbConn
   */
  public LocationsDAOImpl(Connection dbConn) {
    this.connection = dbConn;
  }

  /**
   * Method: initTable()
   *
   * <p>An ArrayList of all the locations in the embedded CSV file. Provided as a data structure for
   * the Java objects made from the embedded CSV file. The ArrayList is used to create a SQL table
   * by generating INSERT INTO statements, which is done in the Location class. @Design The method
   * creates a SQL statement, and checks to see if the Location table has been created already. If
   * it has, DROP the table and create a new one, just create a new one if it doesn't exist
   * already. @SQLTable nodeID- Primary Key, node identifier Xcoord- x coordinate of Location node
   * Ycoord- y coordinate of Location node Floor- floor Location is on Building- the building in
   * which the Location is NodeType- denotes the subtype of Location node LongName- name of
   * Location, 255 char limit ShortName- abbreviated name of Location, 128 char limit
   *
   * @throws SQLException
   * @throws IOException
   */
  public void initTable() throws SQLException, IOException {
    csvIDS.clear();
    csvLocations.clear();
    updatedLocations.clear();
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                LocationsDAOImpl.class.getResourceAsStream(
                    "/edu/wpi/furious_furrets/TowerLocations.csv"),
                StandardCharsets.UTF_8));
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
    Statement stm = connection.createStatement();
    DatabaseMetaData databaseMetadata = connection.getMetaData();
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

    stm.close();
  }

  public void resetMapFromCSV(String filename) throws IOException, SQLException {
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

    stm.close();
  }

  /**
   * @Method menu() @Purpose Generate a command line UI menu that allows the user to select an
   * option 1-6 @Design A switch statement in a while loop, with each option calling the respective
   * method. The while loop terminates once option 6 is chosen, providing the "exit menu"
   * functionality. If a number not between 1 and 6 is chosen, ask for number 1-6 and go back to
   * starting menu.
   *
   * @deprecated
   */
  /*private void menu() throws NoSuchElementException {
    System.out.println("---------------- User Menu ------------------------");

    Scanner inp = new Scanner(System.in);
    int option = 0;
    try {
      while (option != 6) {
        System.out.println(
            "Please Enter A Number:\n"
                + "1- Location Information\n"
                + "2- Change Floor and Type\n"
                + "3- Enter Location\n"
                + "4- Delete Location\n"
                + "5- Save Locations to CSV File\n"
                + "6- Exit Program");

        System.out.println("Option:");
        option = Integer.parseInt(inp.nextLine());
        switch (option) {
          case 1:
            getAllLocations();
            break;
          case 2:
            updateLocation();
            break;
          case 3:
            addLocation();
            break;
          case 4:
            deleteLocation();
            break;
          case 5:
            saveLocation();
            break;
          case 6:
            System.out.println("Exiting program...");
            return;
          default:
            System.out.println("Please enter an option 1-6");
            break;
        }
      }
    } catch (NoSuchElementException | SQLException e) {
      System.out.println("No line found");
      e.printStackTrace();
    }
  }*/

  /**
   * Taking in a ResultSet object take the locations in the form of new Location objects
   *
   * @param rset ResultSet object to get locations from
   * @throws SQLException
   */
  public void locationsFromRSET(ResultSet rset) throws SQLException {
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
      updatedLocations.add(newL);
    }
  }

  /**
   * Displays the list of location nodes along with their attributes.
   *
   * @throws SQLException
   * @returns ArrayList of type Locations
   * @see Location
   */
  public ArrayList<Location> getAllLocations() throws SQLException {

    Statement stm = connection.createStatement();
    String q = "SELECT * FROM Locations";
    ResultSet rset = stm.executeQuery(q);
    locationsFromRSET(rset);
    rset.close();
    stm.close();
    return updatedLocations; // fix
  }

  /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateLocation() throws SQLException {

    String oldLocNodeID = nodeToUpdate.getText();
    String nFloor = newFloor.getText();
    String newType = newLocType.getText();
    int newX = Integer.parseInt(newXcoord.getText());
    int newY = Integer.parseInt(newYcoord.getText());
    String newLName = newLongName.getText();
    String newSName = newShortName.getText();

    String updatedID = "";

    Statement stm = connection.createStatement();
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

  /**
   * Taking user input for the ID of the new location node. A new Java Location object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   */
  public void addLocation() throws SQLException {

    String nID = newLocNodeID.getText();
    Location loc = new Location(nID);
    Statement stm = connection.createStatement();
    String cmd = "INSERT INTO Locations (nodeID) values ('" + nID + "')";
    stm.execute(cmd);
    stm.close();
  }

  /**
   * Taking user input for the ID of the location node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   */
  public void deleteLocation() throws SQLException {

    String oldID = oldLocNodeID.getText();
    csvIDS.remove(oldID);
    Statement stm = connection.createStatement();
    String q = "Delete from Locations where nodeID = '" + oldID + "'";
    stm.execute(q);
    stm.close();
  }

  /**
   * Taking User input for the name of a CSV file. The program first loads all of the contents of
   * the SQL Location table into Java Location objects. Then the CSV file is created from the Java
   * objects.
   */
  public void saveLocationToCSV() {

    String csvName = "src/main/resources/edu/wpi/furious_furrets/TowerLocations.csv";

    Statement stm = null;
    try {
      stm = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      for (String id : csvIDS) {
        ResultSet rset;
        rset = stm.executeQuery("SELECT * FROM Locations WHERE nodeID = '" + id + "'");

        locationsFromRSET(rset);

        rset.close();
        File newCSV = new File(csvName);
        FileWriter fw = new FileWriter(csvName);
        fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
        for (Location l : csvLocations) {
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
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public ArrayList<Location> getCsvLocations() {
    return csvLocations;
  }

  public void setCsvLocations(ArrayList<Location> csvLocations) {
    this.csvLocations = csvLocations;
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
}
