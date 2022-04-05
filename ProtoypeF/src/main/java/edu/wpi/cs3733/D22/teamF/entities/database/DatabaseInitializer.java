package edu.wpi.cs3733.D22.teamF.entities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for handling the connection to the DB, ensuring we only have on connection from the app */
public class DatabaseInitializer {

  private Connection dbConnection;

  /** Constructor */
  private DatabaseInitializer() {
    this.dbConnection = this.connectDatabase();
  }

  /**
   * returns a DatabaseInitializer object from the Helper in DatabaseManager
   *
   * @return DatabaseInitializer
   */
  public static DatabaseInitializer getConnection() {
    return Helper.db;
  }

  /**
   * Connects to the database returns null object if connection failed
   *
   * @return Connection object
   */
  private Connection connectDatabase() {
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

  /**
   * gets the bdConnection in the form of a Connection object
   *
   * @return Connection object
   */
  public Connection getDbConnection() {
    return dbConnection;
  }

  /** makes a new DatabaseInitializer */
  private static class Helper {
    private static final DatabaseInitializer db = new DatabaseInitializer();
  }
}
