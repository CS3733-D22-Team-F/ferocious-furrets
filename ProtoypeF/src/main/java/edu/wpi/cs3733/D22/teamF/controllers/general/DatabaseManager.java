package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.*;
import edu.wpi.cs3733.D22.teamF.entities.database.*;
import edu.wpi.cs3733.D22.teamF.entities.employees.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipmentDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest.maintenanceSRDAOImpl;
import java.io.IOException;
import java.sql.*;

/**
 * Class for managing instances of the various DAO implementations for the different tables To Use
 * access a DAO call DatabaseManager.getWantedDAO() Uses Singleton Design pattern to ensure only one
 * connection is made to the database
 *
 * @see LocationsDAOImpl
 * @see edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer
 */
public class DatabaseManager {

  private static Connection conn; // = DatabaseInitializer.getConnection().getDbConnection();
  private static final RequestDAOImpl RequestDAO = new RequestDAOImpl();
  private static final LocationsDAOImpl locationsDAO = new LocationsDAOImpl();
  private static final equipmentDeliveryDAOImpl medicalEquipmentDeliveryRequestDAO =
      new equipmentDeliveryDAOImpl();
  private static final equipmentDAOImpl medicalEquipmentDAO = new equipmentDAOImpl();
  private static final labDAOImpl labRequestDAO = new labDAOImpl();
  private static final scanDAOImpl scanRequestDAO = new scanDAOImpl();
  private static final floralDAOImpl floralDAO = new floralDAOImpl();
  private static final giftDAOImpl giftDAO = new giftDAOImpl();
  private static final mealDAOImpl mealDAO = new mealDAOImpl();
  private static final patientDAOImpl patientDAO = new patientDAOImpl();
  private static final medicineDAOImpl medicineDAO = new medicineDAOImpl();
  private static final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
  private static final audioVisualDAOImpl audioVisualDAO = new audioVisualDAOImpl();
  private static final maintenanceSRDAOImpl maintenanceDAO = new maintenanceSRDAOImpl();
  private static final physicalTherapyDAOImpl ptDAO = new physicalTherapyDAOImpl();
  private static final facilitiesDAOImpl facilitiesDAO = new facilitiesDAOImpl();
  private static final securityDAOImpl securityDAO = new securityDAOImpl();

  private static DatabaseManager DatabaseManager;

  enum connType {
    embedded,
    clientserver
  }

  private DatabaseManager() {}

  /**
   * first backs up the current database to csvs then makes a new DatabaseInitializer object with a
   * connection to a client-server database
   *
   * @param type boolean true to run embedded database
   * @return Connection object
   * @throws SQLException
   * @throws IOException
   */
  public static Connection switchConnection(DatabaseInitializer.ConnType type)
      throws SQLException, IOException {
    // backUpDatabaseToCSV();
    DatabaseInitializer.switchConnection(type);
    conn = DatabaseInitializer.getConnection().getDbConnection();
    DatabaseManager dbMan = initializeDatabaseManager();
    return conn;
  }

  /**
   * inits the dao objects
   *
   * @return DatabaseManager
   * @throws SQLException
   * @throws IOException
   */
  public static DatabaseManager initializeDatabaseManager() throws SQLException, IOException {

    dropAllTables();
    employeeDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/employees.csv");
    locationsDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/TowerLocations.csv");
    RequestDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/serviceRequest.csv");
    medicalEquipmentDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/equipment.csv");
    medicalEquipmentDeliveryRequestDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/MedEquipReq.csv");
    // medicineDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/medicine.csv");
    MedicineRequest.initializeDatabase("/apiCSV/medicine.csv", "/apiCSV/employees.csv");
    giftDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/gifts.csv");
    labRequestDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/labs.csv");
    scanRequestDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/scans.csv");
    mealDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/meals.csv");
    audioVisualDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/audioVis.csv");
    maintenanceDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/request/maintenanceSR.csv");
    ptDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/physicaltherapy.csv");
    facilitiesDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/facilities.csv");
    securityDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/security.csv");
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
    // System.out.println("SQL: " + statement);
    try {
      stm.execute(statement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // System.out.println(statement);
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
    // System.out.println("SQL: " + query);
    try {
      return stm.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    stm.close();
    return null;
  }

  public static void dropAllTables() throws SQLException {

    // DROP ALL REQUEST
    dropTableIfExist("securityRequest");
    dropTableIfExist("PTREQUEST");
    dropTableIfExist("audioVisualRequest");
    dropTableIfExist("facilitiesRequest");
    dropTableIfExist("ScanRequest");
    dropTableIfExist("LabRequest");
    dropTableIfExist("GIFTREQUEST");
    dropTableIfExist("MEALREQUEST");
    dropTableIfExist("MEDICINEREQUEST");
    dropTableIfExist("EquipmentDeliveryRequest");
    dropTableIfExist("MaintenanceRequest");
    // DROP BIG TABLES
    dropTableIfExist("ServiceRequest");
    dropTableIfExist("MedicalEquipment");
    dropTableIfExist("Locations");
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
   * Functions to run before exiting the program
   *
   * @throws SQLException
   * @throws IOException
   */
  public static void backUpDatabaseToCSV() throws SQLException, IOException {
    locationsDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/TowerLocations.csv");
    medicalEquipmentDAO.backUpToCSV(
        "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/equipment.csv");
    medicalEquipmentDeliveryRequestDAO.backUpToCSV(
        "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/MedEquipReq.csv");
    giftDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/gifts.csv");
    labRequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/labs.csv");
    mealDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/meals.csv");
    medicineDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/medicine.csv");
    MedicineRequest.backUpDatabase(
        "src/main/resources/apiCSV/medicine.csv", "src/main/resources/apiCSV/employees.csv");
    scanRequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/scans.csv");
    RequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/serviceRequest.csv");
    audioVisualDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/audioVis.csv");
    ptDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/physicaltherapy.csv");
    securityDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/security.csv");
    facilitiesDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/facilities.csv");
    System.out.println("Locations table updated to csv :)");
    System.out.println("MedEquip table updated to csv :)");
    System.out.println("MedicalEquipmentDeliveryRequest table updated to csv :)");
    System.out.println("Backed up to CSV :)");
  }

  /**
   * gets the LocatationDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return LocationsDAOImpl
   */
  public static LocationsDAOImpl getLocationDAO() {
    return locationsDAO;
  }

  public static RequestDAOImpl getRequestDAO() {
    return RequestDAO;
  }
  /**
   * gets the MedDelReqDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return MedDelReqDAOImpl
   */
  public static equipmentDeliveryDAOImpl getMedEquipDelReqDAO() {
    return medicalEquipmentDeliveryRequestDAO;
  }
  /**
   * Return med equipment DAO object to ad
   *
   * @return medEquipImpl DAO object
   */
  public static equipmentDAOImpl getMedEquipDAO() {
    return medicalEquipmentDAO;
  }
  /** gets the LabRequestDAO */
  public static labDAOImpl getLabRequestDAO() {
    return labRequestDAO;
  }
  /** gets the scanRequestDAO */
  public static scanDAOImpl getScanRequestDAO() {
    return scanRequestDAO;
  }

  public static mealDAOImpl getMealDAO() {
    return mealDAO;
  }

  public static floralDAOImpl getFloralDAO() {
    return floralDAO;
  }

  public static giftDAOImpl getGiftDAO() {
    return giftDAO;
  }

  public static medicineDAOImpl getMedicineDAO() {
    return medicineDAO;
  }

  public static EmployeeDAOImpl getEmployeeDAO() {
    return employeeDAO;
  }

  public static patientDAOImpl getPatientDAO() {
    return patientDAO;
  }

  public static audioVisualDAOImpl getAudioVisDAO() {
    return audioVisualDAO;
  }

  public static maintenanceSRDAOImpl getMaintenanceDAO() {
    return maintenanceDAO;
  }

  public static facilitiesDAOImpl getFacilitiesDAO() {
    return facilitiesDAO;
  }

  public static physicalTherapyDAOImpl getPTDAO() {
    return ptDAO;
  }

  public static securityDAOImpl getSecurityDAO() {
    return securityDAO;
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
