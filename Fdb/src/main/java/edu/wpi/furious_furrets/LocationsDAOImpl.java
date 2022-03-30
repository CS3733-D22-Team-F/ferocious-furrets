package edu.wpi.furious_furrets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LocationsDAOImpl implements LocationDAO {

  private Connection connection;
  private ArrayList<Location> csvLocations = new ArrayList<Location>();
  private ArrayList<Location> updatedLocations = new ArrayList<Location>();
  private ArrayList<String> csvIDS = new ArrayList<String>();

  public LocationsDAOImpl(Connection dbConn) {
    this.connection = dbConn;
  }

  /**
   * @throws SQLException
   * @throws NoSuchElementException @Method testConnection() @Design Creates connection with Apache
   *     Derby database. Uses BufferedReader to read in embedded CSV file, parsing it into Location
   *     objects which will be INSERTed into SQL table in initTable() method. After parsing into
   *     Location object, the object is added to csvLocations, an array list of all the Locations in
   *     the csv file. This method then calls initTable(), passing csvLocations in as a param, and
   *     menu() to display the user menu and prompt for action. Upon exit from the menu, the
   *     connection is closed.
   */
  //  public void testConnection() throws SQLException, NoSuchElementException {
  //
  //    System.out.println("Database Setup");
  //
  //    try {
  //      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
  //    } catch (ClassNotFoundException e) {
  //      System.out.println("Driver not found");
  //      e.printStackTrace();
  //    }
  //
  //    System.out.println("Driver registered");
  //    connection = null;
  //
  //    try {
  //      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
  //
  //      assert (connection != null);
  //      //
  //      //      BufferedReader lineReader =
  //      //          new BufferedReader(
  //      //              new InputStreamReader(
  //      //
  //      // Main.class.getResourceAsStream("/edu/wpi/furious_furrets/TowerLocations.csv"),
  //      //                  StandardCharsets.UTF_8));
  //      //      String lineText = null;
  //      //      lineReader.readLine(); // skip header line
  //      //
  //      //      while ((lineText = lineReader.readLine()) != null) {
  //      //        String[] data = lineText.split(",");
  //      //        String nID = data[0];
  //      //        int x = Integer.parseInt(data[1]);
  //      //        int y = Integer.parseInt(data[2]);
  //      //        String floor = data[3];
  //      //        String building = data[4];
  //      //        String nodeType = data[5];
  //      //        String longName = data[6];
  //      //        String shortName = data[7];
  //      //        Location l = new Location(nID, x, y, floor, building, nodeType, longName,
  //      // shortName);
  //      //        csvLocations.add(l);
  //      //        csvIDS.add(l.getNodeID());
  //      //      }
  //
  //      initTable();
  //
  //      // User menu
  //      menu();
  //    } catch (SQLException e) {
  //      System.out.println("Connection failed");
  //      e.printStackTrace();
  //      return;
  //    } catch (FileNotFoundException e) {
  //      e.printStackTrace();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //
  //    System.out.println("Derby connection established");
  //    connection.close();
  //  }

  /**
   * Method: initTable(ArrayList<Location>)
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
   */
  public void initTable() throws SQLException, IOException {
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                Main.class.getResourceAsStream("/edu/wpi/furious_furrets/TowerLocations.csv"),
                StandardCharsets.UTF_8));
    String lineText = null;
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

  /**
   * @Method menu() @Purpose Generate a command line UI menu that allows the user to select an
   * option 1-6 @Design A switch statement in a while loop, with each option calling the respective
   * method. The while loop terminates once option 6 is chosen, providing the "exit menu"
   * functionality. If a number not between 1 and 6 is chosen, ask for number 1-6 and go back to
   * starting menu.
   */
  private void menu() throws NoSuchElementException {
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
  }
  /**
   * Option 1, The program displays the list of location nodes along with their attributes. Then the
   * menu is displayed again and the user is prompted for the next selection.
   */
  public ArrayList<Location> getAllLocations() throws SQLException {
    System.out.println("Displaying Location Information...");
    System.out.print("nodeID\tname\txcoord\tycoord\tfloor\tbuilding\tnodeType\n");
    Statement stm = connection.createStatement();
    String q = "SELECT * FROM Locations";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      System.out.println(
          rset.getString("nodeID")
              + " "
              + rset.getString("LongName")
              + " "
              + rset.getString("xcoord")
              + " "
              + rset.getString("ycoord")
              + " "
              + rset.getString("floor")
              + " "
              + rset.getString("building")
              + " "
              + rset.getString("nodeType"));
    }
    rset.close();
    stm.close();
    return null; // fix
  }

  /**
   * Option 2, The user is prompted for the ID of the location node and then is prompted for the new
   * values of the floor and location type. Then the menu is displayed again and the user is
   * prompted for the next selection.
   */
  public void updateLocation() throws SQLException {
    /*
    TODO: Alter table
     */
    System.out.println("Enter Location ID");
    Scanner s = new Scanner(System.in);
    String idOfLoc = s.nextLine();
    // read in from sql
    System.out.println("Editing node " + idOfLoc);
    System.out.println("Enter New Floor Value");
    String newFloor = s.nextLine();
    System.out.println("Enter New Location Type");
    String newLocType = s.nextLine();

    Statement stm = connection.createStatement();
    String cmd =
        "UPDATE Locations SET floor = '"
            + newFloor
            + "', nodeType = '"
            + newLocType
            + "' WHERE nodeID = '"
            + idOfLoc
            + "'";
    System.out.println(cmd);
    stm.execute(cmd);
    stm.close();

    System.out.println("Updating Location Information...");
  }

  /**
   * Option 3, The user is prompted for the ID of the new location node. A new Java Location object
   * is created and the node is added to the SQL table. Then the menu is displayed again and the
   * user prompted for the next selection.
   */
  public void addLocation() throws SQLException {
    /*
    TODO: Test
     */
    System.out.println("Enter new Location node ID: ");
    Scanner newID = new Scanner(System.in);
    String nID = newID.nextLine();
    csvIDS.add(nID);
    Location loc = new Location(nID);
    System.out.println("Adding New Location...");
    Statement stm = connection.createStatement();
    String cmd = "INSERT INTO Locations (nodeID) values ('" + nID + "')";
    stm.execute(cmd);
    stm.close();
  }

  /**
   * Option 4, The user is prompted for the ID of the location node. The node is removed from the
   * SQL table, and the corresponding Java object is deleted. Then the menu is displayed again and
   * the user prompted for the next selection.
   */
  public void deleteLocation() throws SQLException {
    /*
    TODO: Delete from
     */
    System.out.println("Enter Location ID to delete:");
    Scanner newID = new Scanner(System.in);
    String nID = newID.nextLine();
    csvIDS.remove(nID);
    Statement stm = connection.createStatement();
    String q = "Delete from Locations where nodeID = '" + nID + "'";
    stm.execute(q);
    stm.close();
  }

  /**
   * Option 5, The user is prompted for the name of the CSV file. The program first loads all of the
   * contents of the SQL Location table into Java Location objects. Then the CSV file is created
   * from the Java objects.
   */
  private void saveLocation() {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter CSV File Name");
    String csvName = in.nextLine();

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

        while (rset.next()) {
          String nodeID = rset.getString("nodeID");
          String longName = rset.getString("LongName");
          String shortName = rset.getString("ShortName");
          int xCoord = rset.getInt("xcoord");
          //            Integer.parseInt(
          //                String.valueOf(
          //                    stm.execute("SELECT xcoord FROM Locations WHERE nodeID = '" + id +
          // "'")));
          int yCoord = rset.getInt("ycoord");
          //            Integer.parseInt(
          //                String.valueOf(
          //                    stm.execute("SELECT ycoord FROM Locations WHERE nodeID = '" + id +
          // "'")));
          String floor = rset.getString("floor");
          //            String.valueOf(stm.execute("SELECT floor FROM Locations WHERE nodeID = '" +
          // id
          // + "'"));
          String building = rset.getString("building");
          //            String.valueOf(
          //                stm.execute("SELECT building FROM Locations WHERE nodeID = '" + id +
          // "'"));
          String nodeType = rset.getString("nodeType");
          //            String.valueOf(
          //                stm.execute("SELECT nodeType FROM Locations WHERE nodeID = '" + id +
          // "'"));
          Location newL =
              new Location(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
          updatedLocations.add(newL);
        }
        rset.close();
        File newCSV = new File(csvName);
        FileWriter fw = new FileWriter(csvName);
        fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName");
        for (Location l : updatedLocations) {
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

    System.out.println("Saving Location Information...");
  }
}
