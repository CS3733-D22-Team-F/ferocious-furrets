package edu.wpi.furious_furrets.controllers.general;

import edu.wpi.furious_furrets.entities.database.DatabaseInitializer;
import edu.wpi.furious_furrets.entities.location.LocationsDAOImpl;
import edu.wpi.furious_furrets.entities.medicalEquipment.MedEquipDAOImpl;
import edu.wpi.furious_furrets.entities.request.deliveryRequest.equipmentDeliveryRequest.MedDelReqDAOImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for managing instances of the various DAO implementations for the different tables To Use
 * access a DAO call DatabaseManager.getWantedDAO()
 *
 * @see LocationsDAOImpl
 * @see edu.wpi.furious_furrets.entities.database.DatabaseInitializer
 */
public class DatabaseManager {

  private static final Connection conn = DatabaseInitializer.getConnection().getDbConnection();
  private static final LocationsDAOImpl locationsDAO = new LocationsDAOImpl();
  private static final MedDelReqDAOImpl medicalEquipmentDeliveryRequestDAO = new MedDelReqDAOImpl();
  private static final MedEquipDAOImpl medicalEquipmentDAO = new MedEquipDAOImpl();
  //  private static final labReqDAOImpl lrdao = new labReqDAOImpl(conn);

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
    locationsDAO.initTable();
    medicalEquipmentDeliveryRequestDAO.initTable();
    medicalEquipmentDAO.initTable();
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
    } catch (SQLException e) {
      e.printStackTrace();
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

  public static MedEquipDAOImpl getMedEquipDAO() {
    return medicalEquipmentDAO;
  }

  /**
   * gets the labReqDAOImpl object Use to use the a ddLocation, update, delete, etc
   *
   * @return labReqDAOImpl
   */
  //  public static labReqDAOImpl getlrdao() {
  //    return lrdao;
  //  }

  /** helper */
  private static class Helper {
    private static final DatabaseManager dbMan = new DatabaseManager();
  }

  /** helper but singleton */
  private static class SingletonHelper {
    private static final DatabaseManager DatabaseManager = new DatabaseManager();
  }
}
