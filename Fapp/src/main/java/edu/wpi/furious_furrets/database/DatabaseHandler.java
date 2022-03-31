package edu.wpi.furious_furrets.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for handling the connection to the DB, ensuring we only have on connection from the app */
public class DatabaseHandler {

  /** */
  public DatabaseHandler() {}

  public Connection dbConnection;

  /**
   * Connects to the database returns null object if connection failed
   *
   * @return Connection object
   */
  public Connection connectDatabase() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
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
