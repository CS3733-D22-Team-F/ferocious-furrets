package edu.wpi.furious_furrets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

  public Connection dbConnection;

  public DatabaseHandler() {}

  public Connection connectDatabase() {
    try {
      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
      e.printStackTrace();
    }

    System.out.println("Driver registered");
    dbConnection = null;

    try {
      dbConnection =
          DriverManager.getConnection("jdbc:derby:myDB;create=true"); // CONNECT TO DATABASE
      assert (dbConnection != null);
      // initTable();

    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return null;
    }

    System.out.println("Derby connection established");
    return dbConnection;
  }
}
