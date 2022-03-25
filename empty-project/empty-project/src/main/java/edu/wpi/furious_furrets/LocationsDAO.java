package edu.wpi.furious_furrets;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LocationsDAO {

  private Connection connection;
  private ArrayList<Location> csvLocations = new ArrayList<Location>();
  private ArrayList<String> csvIDS = new ArrayList<String>();

  public void testConnection() throws SQLException, NoSuchElementException {

    System.out.println("Database Setup");

    try {
      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
      e.printStackTrace();
    }

    System.out.println("Driver registered");
    connection = null;

    try {
      connection = DriverManager.getConnection("jdbc:derby:C:/Users/radcl/Locations;create=true");
      // "jdbc:derby:!(memory:){path}[;<;,create={create
      // database:true_box},user={user:param},password={password:param},{:identifier}={:param}>]"
      assert (connection != null);
      initTable("dummy filename");
      // User menu
      menu();
    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    System.out.println("Derby connection established");
    connection.close();
  }

  private void initTable(String locationList) throws SQLException {
    Statement stm = connection.createStatement();
    stm.execute("DROP TABLE Locations");
    stm.execute(
        "Create table Locations (nodeID varchar(16), Xcoord int, Ycoord int, Floor varchar(4), Building varchar(255), NodeType varchar(255), LongName varchar(255), ShortName varchar(128))");
    stm.execute(
        "Insert into Locations (nodeID, XCoord, YCoord, Floor, Building, NodeType, LongName, ShortName) values ('FDEPT001', 1617, 825, '1', 'Tower', 'DEPT', 'CIM', 'CIM')");
    stm.close();
  }

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
            displayLocInfo();
            break;
          case 2:
            updateLocation();
            break;
          case 3:
            insertLocation();
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
  /*
   Option 1, The program displays the list of location nodes along with their attributes.
  Then the menu is displayed again and the user is prompted for the next selection.
   */
  private void displayLocInfo() throws SQLException {
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
  }

  /*
  Option 2, The user is prompted for the ID of the location node and then is prompted for
  the new values of the floor and location type. Then the menu is displayed again and
  the user is prompted for the next selection.
   */
  private void updateLocation() throws SQLException {
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

  /*
  Option 3, The user is prompted for the ID of the new location node. A new Java Location
  object is created and the node is added to the SQL table. Then the menu is displayed
   again and the user prompted for the next selection.
   */
  private void insertLocation() throws SQLException {
    /*
    TODO: Test
     */
    System.out.println("Enter new Location node ID: ");
    Scanner newID = new Scanner(System.in);
    String nID = newID.nextLine();
    // csvIDS.add(nID);
    // Location loc = new Location(nID);
    System.out.println("Adding New Location...");
    Statement stm = connection.createStatement();
    String cmd = "INSERT INTO Locations (nodeID) values ('" + nID + "')";
    stm.execute(cmd);
    stm.close();
  }

  /*
   Option 4, The user is prompted for the ID of the location node. The node is removed from
   the SQL table, and the corresponding Java object is deleted. Then the menu is displayed
   again and the user prompted for the next selection.
  */
  private void deleteLocation() throws SQLException {
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

  /*
  Option 5, The user is prompted for the name of the CSV file. The program first loads all of the
  contents of the SQL Location table into Java Location objects. Then the CSV file is
  created from the Java objects.
   */
  private void saveLocation() {
    System.out.println("Enter CSV File Name");
    Scanner in = new Scanner(System.in);
    String csvName = in.nextLine();

    Statement stm = null;
    try {
      stm = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      for (String id : csvIDS) {
        String nodeID =
            String.valueOf(stm.execute("SELECT nodeID FROM Locations WHERE nodeID = id"));
        String longName =
            String.valueOf(stm.execute("SELECT longName FROM Locations WHERE nodeID = id"));
        String shortName =
            String.valueOf(stm.execute("SELECT shortName FROM Locations WHERE nodeID = id"));
        String xCoord =
            String.valueOf(stm.execute("SELECT xcoord FROM Locations WHERE nodeID = id"));
        String yCoord =
            String.valueOf(stm.execute("SELECT ycoord FROM Locations WHERE nodeID = id"));
        String floor =
            String.valueOf(stm.execute("SELECT nodeID FROM Locations WHERE nodeID = id"));
        String building =
            String.valueOf(stm.execute("SELECT nodeID FROM Locations WHERE nodeID = id"));
        String nodeType =
            String.valueOf(stm.execute("SELECT nodeID FROM Locations WHERE nodeID = id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Saving Location Information...");
  }
}
