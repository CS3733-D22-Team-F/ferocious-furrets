package edu.wpi.furious_furrets.entities.database;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for handling the connection to the DB, ensuring we only have on connection from the app */
public class DatabaseInitializer {

  public Connection dbConnection;

  private DatabaseInitializer() {
    this.dbConnection = this.connectDatabase();
  }

  private static class Helper {
    private static final DatabaseInitializer db = new DatabaseInitializer();
  }

  public static DatabaseInitializer getConnection() {
    return Helper.db;
  }

  /** */
  //  public DatabaseInitializer() {
  //    this.dbConnection = this.connectDatabase();
  //  }

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

  public Connection getDbConnection() {
    return dbConnection;
  }
}
