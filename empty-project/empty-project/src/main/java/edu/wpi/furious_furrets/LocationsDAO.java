package edu.wpi.furious_furrets;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LocationsDAO {

  Connection connection;

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
      connection = DriverManager.getConnection("jdbc:derby:C:/Users/radcl/DB;create=true");
      assert (connection != null);
      /*
      TODO: Read in CSV file
       */

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

  private void initTable(String csvFilename) throws SQLException {
    Statement stm = connection.createStatement();
    // stm.execute(
    // "Create table Locations (nodeID varchar(16), Name varchar(255), Xcoord int, Ycoord int, Floor
    // int, Building varchar(255), NodeType varchar(255))");
    // stm.execute(
    // "Insert into Locations (nodeID, Name, XCoord, YCoord, Floor, Building, NodeType) values
    // ('FDEPT001', 'CIM', 1617, 825, 1, 'Tower', 'DEPT')");
    stm.close();
  }

  private void menu() throws NoSuchElementException {
    System.out.println("---------------- User Menu ------------------------");
    System.out.println(
        "Please Enter A Number:\n"
            + "1- Location Information\n"
            + "2- Change Floor and Type\n"
            + "3- Enter Location\n"
            + "4- Delete Location\n"
            + "5- Save Locations to CSV File\n"
            + "6- Exit Program");

    Scanner inp = new Scanner(System.in);
    int option = 0;
    try {
      while (option != 6) {
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
    String q = "Select * from Locations";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      System.out.println(
          rset.getString("nodeID")
              + " "
              + rset.getString("name")
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
  private void updateLocation() {
    System.out.println("Updating Location Information...");
  }

  /*
  Option 3, The user is prompted for the ID of the new location node. A new Java Location
  object is created and the node is added to the SQL table. Then the menu is displayed
   again and the user prompted for the next selection.
   */
  private void insertLocation() {
    System.out.println("Adding New Location...");
  }

  /*
   Option 4, The user is prompted for the ID of the location node. The node is removed from
   the SQL table, and the corresponding Java object is deleted. Then the menu is displayed
   again and the user prompted for the next selection.
  */
  private void deleteLocation() {
    System.out.println("Deleting Location...");
  }

  /*
  Option 5, The user is prompted for the name of the CSV file. The program first loads all of the
  contents of the SQL Location table into Java Location objects. Then the CSV file is
  created from the Java objects.
   */
  private void saveLocation() {
    System.out.println("Saving Location Information...");
  }
}
