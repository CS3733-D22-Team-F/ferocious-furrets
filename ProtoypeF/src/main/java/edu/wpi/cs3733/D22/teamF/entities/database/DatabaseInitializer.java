package edu.wpi.cs3733.D22.teamF.entities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class for handling the connection to the DB, ensuring we only have on connection from the app */
public class DatabaseInitializer {

  // default embedded
  static ConnType connType = ConnType.EMBEDDED;
  protected String defaultUR = "jdbc:derby://localhost:1527/csDB;create=true";
  private Connection dbConnection;
  /**
   * Constructor
   *
   * @param type boolean run in a embedded db mode or client server mode
   */
  private DatabaseInitializer(ConnType type) {
    System.out.println("int db init conn type: " + type.toString());
    connType = type;
    this.dbConnection = this.connectDatabase(type);
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
    System.out.println("Set Connection To Type: " + connType.toString());
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
    System.out.println("Connecting to Database Type:" + type.toString());
    dbConnection = null;
    if (type == ConnType.EMBEDDED) {
      dbConnection = connectEmbedded();
    } else {
      dbConnection = connectRemote(defaultUR);

      if (dbConnection == null) {
        System.out.println("Connecting to Embedded Instead");
        dbConnection = connectEmbedded();
      }
    }
    return dbConnection;
  }

  /**
   * Connects to the database returns null object if connection failed
   *
   * @return Connection object
   */
  private Connection connectDatabase(ConnType type, String url) {
    System.out.println("Connecting to Database Type:" + type.toString());
    dbConnection = null;
    if (type == ConnType.EMBEDDED) {
      dbConnection = connectEmbedded();
    } else {
      dbConnection = connectRemote(url);

      if (dbConnection == null) {
        System.out.println("Connecting to Embedded Instead");
        dbConnection = connectEmbedded();
      }
    }
    return dbConnection;
  }

  private Connection connectRemote(String url) {
    Connection tempConn = null;

    System.out.println("Attempting Connection to Remote: " + url);
    try {
      Class.forName("org.apache.derby.jdbc.ClientDriver");

      System.out.println("Remote Driver Registered");

      tempConn = DriverManager.getConnection(url); // CONNECT TO DATABASE
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("Embedded Driver not Found");
      e.printStackTrace();
    }
    if (tempConn != null) {
      System.out.println("Derby Remote Connection Established");
    } else {
      System.out.println("Derby Remote Connection Failed");
    }
    return tempConn;
  }

  private Connection connectEmbedded() {
    Connection tempConn = null;

    System.out.println("Attempting Connection to Embedded");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

      System.out.println("Embedded Driver Registered");
      tempConn = null;

      tempConn = DriverManager.getConnection("jdbc:derby:myDB;create=true"); // CONNECT TO DATABASE
      assert (tempConn != null);

    } catch (ClassNotFoundException e) {
      System.out.println("Remote Driver not Found");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("Embedded Connection Failed");
      System.out.println("You done ****ed up your project");
      e.printStackTrace();
      return null;
    }
    if (tempConn != null) {
      System.out.println("Derby Embedded Connection Established");
    } else {
      System.out.println("Derby Embedded Connection Failed");
    }
    return tempConn;
  }

  /**
   * gets the bdConnection in the form of a Connection object
   *
   * @return Connection object
   */
  public Connection getDbConnection() {
    return dbConnection;
  }

  public static ConnType getConnType() {
    return connType;
  }

  public enum ConnType {
    EMBEDDED,
    CLIENTSERVER
  }

  /** makes a new DatabaseInitializer */
  private static class Helper {
    private static final DatabaseInitializer db = new DatabaseInitializer(connType);
  }
}
