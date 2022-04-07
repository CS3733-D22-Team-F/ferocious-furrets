package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.MedEquipDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest.MedDelReqDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest.labRequestDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.scanRequestDAOImpl;
import java.io.IOException;
import java.sql.*;

/**
 * Class for managing instances of the various DAO implementations for the different tables To Use
 * access a DAO call DatabaseManager.getWantedDAO()
 *
 * @see LocationsDAOImpl
 * @see edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer
 */
public class DatabaseManager {

  private static final Connection conn = DatabaseInitializer.getConnection().getDbConnection();
  private static final LocationsDAOImpl locationsDAO = new LocationsDAOImpl();
  private static final MedDelReqDAOImpl medicalEquipmentDeliveryRequestDAO = new MedDelReqDAOImpl();
  private static final MedEquipDAOImpl medicalEquipmentDAO = new MedEquipDAOImpl();
  private static final labRequestDAOImpl labRequestDAO = new labRequestDAOImpl();
  private static final scanRequestDAOImpl scanRequestDAO = new scanRequestDAOImpl();

  private static DatabaseManager DatabaseManager;

  private DatabaseManager() {}

  /**
   * inits the dao objects
   *
   * @return DatabaseManager
   * @throws SQLException
   * @throws IOException
   */
  public static DatabaseManager initalizeDatabaseManager() throws SQLException, IOException {
    locationsDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/TowerLocations.csv");
    medicalEquipmentDeliveryRequestDAO.initTable();
    medicalEquipmentDAO.initTable();
    labRequestDAO.initTable();
    scanRequestDAO.initScanRequestTable();
    return Helper.dbMan;
  }

  /**
   * gets the connection object
   *
   * @return Connection
   */
  public static Connection getConn() {
    return conn;
  }
  /**
   * Runs SQL statement
   *
   * @param statement statement to run
   * @throws SQLException if there is an error with the statement (santax, etc)
   */
  public static void runStatement(String statement) throws SQLException {
    Statement stm = conn.createStatement();
    try {
      stm.execute(statement);
      System.out.println("SQL: " + statement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    stm.close();
  }
  /**
   * Executes query to the apache derby database
   *
   * @param query String to be executed
   * @return return the rset containing the query
   * @throws SQLException when there is a sql problem
   */
  public static ResultSet runQuery(String query) throws SQLException {
    Statement stm = conn.createStatement();
    try {
      return stm.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    stm.close();
    return null;
  }

  public static void dropTableIfExist(String droppingTable) throws SQLException {
    if (conn.getMetaData().getTables(null, null, droppingTable.toUpperCase(), null).next()) {
      runStatement("DROP TABLE " + droppingTable);
      //      System.out.println("Dropping " + droppingTable + " table!");
    } else {
      //      System.out.println(droppingTable + " table does not Exist!");
    }
  }

  /**
   * gets the LocatationDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return LocationsDAOImpl
   */
  public static LocationsDAOImpl getLocationDAO() {
    return locationsDAO;
  }
  /**
   * gets the MedDelReqDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return MedDelReqDAOImpl
   */
  public static MedDelReqDAOImpl getMedEquipDelReqDAO() {
    return medicalEquipmentDeliveryRequestDAO;
  }
  /**
   * Return med equipment DAO object to ad
   *
   * @return medEquipImpl DAO object
   */
  public static MedEquipDAOImpl getMedEquipDAO() {
    return medicalEquipmentDAO;
  }
  /** gets the LabRequestDAO */
  public static labRequestDAOImpl getLabRequestDAO() {
    return labRequestDAO;
  }
  /** gets the scanRequestDAO */
  public static scanRequestDAOImpl getScanRequestDAO() {
    return scanRequestDAO;
  }

  /** helper */
  private static class Helper {
    private static final DatabaseManager dbMan = new DatabaseManager();
  }
  /** helper but singleton */
  private static class SingletonHelper {
    private static final DatabaseManager DatabaseManager = new DatabaseManager();
  }
}
