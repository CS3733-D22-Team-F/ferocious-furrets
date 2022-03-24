package edu.wpi.furious_furrets;

import java.io.*;
import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LocationsDAO {

  public void testConnection() throws SQLException, NoSuchElementException {

    System.out.println("Database Setup");

    try {
      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
      e.printStackTrace();
    }

    System.out.println("Driver registered");
    Connection connection = null;

    try {
      connection = DriverManager.getConnection("jdbc:derby:C:/Users/radcl/DB;create=true");
      assert (connection != null);
      //User menu
      menu();

      Statement stm = connection.createStatement();
      // stm.execute("INSERT into Students (sid, name, grade, classnum) values (1, 'Owen Radcliffe',
      // 11, 13)");
      // stm.execute("INSERT into Students values (2, 'Jack Hanlon', 10, 13)");
      // stm.execute("insert into Students values (3, 'Raffi Alexander', 11, 15)");
      // stm.execute("insert into Students values (4, 'Cole Parks', 11, 14)");
      // String q1 = "Select * from Students where grade = 11";
      // ResultSet rset1 = stm.executeQuery(q1);
      // while (rset1.next()) {
      //  System.out.println(rset1.getString("name"));
      // }
      // rset1.close();
      // stm.close();
    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    System.out.println("Derby connection established");
    connection.close();
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
    } catch (NoSuchElementException e) {
      System.out.println("No line found");
      e.printStackTrace();
      return;
    }
  }
  /*
   Option 1, The program displays the list of location nodes along with their attributes.
  Then the menu is displayed again and the user is prompted for the next selection.
   */
  private ResultSet displayLocInfo() {
    System.out.println("Displaying Location Information...");
    return null;
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
