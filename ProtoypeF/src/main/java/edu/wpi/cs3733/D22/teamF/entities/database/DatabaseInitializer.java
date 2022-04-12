package edu.wpi.cs3733.D22.teamF.entities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for handling the connection to the DB, ensuring we only have on connection from the app */
public class DatabaseInitializer {

  private Connection dbConnection;

  public enum ConnType {
    NEITHER,
    EMBEDDED,
    CLIENTSERVER
  }

  static ConnType connType = ConnType.NEITHER;

  /**
   * Constructor
   *
   * @param type boolean run in a embedded db mode or client server mode
   */
  private DatabaseInitializer(ConnType type) {
    this.dbConnection = this.connectDatabase(connType);
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
   * sets the embedded bool and returns a new Helped.db
   *
   * @param connectionType boolean run in an embedded or client server mode
   * @return a new DatabaseInitializer object
   */
  public static DatabaseInitializer switchConnection(ConnType connectionType) throws SQLException {
    connType = connectionType;
    System.out.println(connType.toString());
    System.out.println(Helper.db.dbConnection.getMetaData().getConnection().toString());
    return Helper.db;
  }

  /**
   * Connects to the database returns null object if connection failed
   *
   * @return Connection object
   * @param type
   */
  private Connection connectDatabase(ConnType type) {
    if (type == ConnType.EMBEDDED) {
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
    } else {

      try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      System.out.println("Remote Driver registered");

      dbConnection = null;
      try {
        dbConnection =
            DriverManager.getConnection(
                "jdbc:derby://localhost:1527/csDB;create=true"); // CONNECT TO DATABASE
        assert (dbConnection != null);

      } catch (SQLException e) {
        System.out.println("Remote Connection failed");
        e.printStackTrace();
        return null;
      }

      System.out.println("Derby Remote connection established");
    }
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
    private static final DatabaseInitializer db = new DatabaseInitializer(connType);
  }
}
