package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.*;
import edu.wpi.cs3733.D22.teamF.entities.database.*;
import edu.wpi.cs3733.D22.teamF.entities.database.MaintenanceSRDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.employees.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.EquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for managing instances of the various DAO implementations for the different tables To Use
 * access a DAO call DatabaseManager.getWantedDAO() Uses Singleton Design pattern to ensure only one
 * connection is made to the database
 *
 * @see LocationsDAOImpl
 */
public class DatabaseManager {

  /** Add DAOImplementation objects here */
  private final RequestDAOImpl RequestDAO = new RequestDAOImpl();

  private final LocationsDAOImpl locationsDAO = new LocationsDAOImpl();
  private final EquipmentDeliveryDAOImpl medicalEquipmentDeliveryRequestDAO =
      new EquipmentDeliveryDAOImpl();
  private final EquipmentDAOImpl medicalEquipmentDAO = new EquipmentDAOImpl();
  private final LabDAOImpl labRequestDAO = new LabDAOImpl();
  private final ScanDAOImpl scanRequestDAO = new ScanDAOImpl();
  private final FloralDAOImpl floralDAO = new FloralDAOImpl();
  private final GiftDAOImpl giftDAO = new GiftDAOImpl();
  private final MealDAOImpl mealDAO = new MealDAOImpl();
  private final PatientDAOImpl patientDAO = new PatientDAOImpl();
  private final MedicineDAOImpl medicineDAO = new MedicineDAOImpl();
  private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
  private final AudioVisualDAOImpl audioVisualDAO = new AudioVisualDAOImpl();
  private final MaintenanceSRDAOImpl maintenanceDAO = new MaintenanceSRDAOImpl();
  private final PhysicalTherapyDAOImpl ptDAO = new PhysicalTherapyDAOImpl();
  private final FacilitiesDAOImpl facilitiesDAO = new FacilitiesDAOImpl();
  private final SecurityDAOImpl securityDAO = new SecurityDAOImpl();
  private final ExtPatientDAOImpl extPatDAO = new ExtPatientDAOImpl();
  private final ThemeDAOImpl themeDAO = new ThemeDAOImpl();

  /** DatabaseManager variables */
  private ConnType connType = ConnType.EMBEDDED;

  protected String defaultUR = "jdbc:derby://localhost:1527/csDB;create=true";
  private Connection dbConnection;

  /** Singleton instance of Database Manager */
  private static DatabaseManager databaseManager;

  private DatabaseManager() {
    connectDatabase(connType);
  }

  public static DatabaseManager getInstance() {
    if (databaseManager == null) {
      databaseManager = new DatabaseManager();
    }
    return databaseManager;
  }

  /**
   * Connects to the database returns null object if connection failed
   *
   * @return Connection object
   * @param type
   */
  private void connectDatabase(ConnType type) {
    System.out.println("Connecting to Database Type:" + type.toString());
    if (type == ConnType.EMBEDDED) {
      dbConnection = connectEmbedded();
    } else {
      dbConnection = connectRemote(defaultUR);

      if (dbConnection == null) {
        System.out.println("Connecting to Embedded Instead");
        dbConnection = connectEmbedded();
      }
    }
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
      // Class.forName("org.apache.derby.jdbc.ClientDriver");

      System.out.println("Remote Driver Registered");

      tempConn = DriverManager.getConnection(url); // CONNECT TO DATABASE
    } catch (SQLException e) {
      System.out.println("Remote Driver not Found");
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
      // Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

      System.out.println("Embedded Driver Registered");
      tempConn = null;

      tempConn = DriverManager.getConnection("jdbc:derby:myDB;create=true"); // CONNECT TO DATABASE
      assert (tempConn != null);

      //    } catch (ClassNotFoundException e) {
      //      System.out.println("Embedded Driver not Found");
      //      e.printStackTrace();
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
   * first backs up the current database to csvs then makes a new DatabaseInitializer object with a
   * connection to a client-server database
   *
   * @param type boolean true to run embedded database
   * @return Connection object
   * @throws SQLException
   * @throws IOException
   */
  public void switchConnection(ConnType type) throws SQLException, IOException {
    connType = type;
    connectDatabase(connType);
    System.out.println("Set Connection To Type: " + connType.toString());
    System.out.println(dbConnection.getMetaData().getConnection().toString());
    initializeDatabaseManager();
  }

  /**
   * inits the dao objects
   *
   * @return DatabaseManager
   * @throws SQLException
   * @throws IOException
   */
  public void initializeDatabaseManager() throws SQLException, IOException {

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
    extPatDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/extPatDelivery.csv");
    themeDAO.initTable("/edu/wpi/cs3733/D22/teamF/csv/themes.csv");
  }

  /**
   * gets the connection object
   *
   * @return Connection
   */
  public Connection getDatabaseConnection() {
    return dbConnection;
  }
  /**
   * Runs SQL statement
   *
   * @param statement statement to run
   * @throws SQLException if there is an error with the statement (santax, etc)
   */
  public void runStatement(String statement) throws SQLException {
    Statement stm = dbConnection.createStatement();
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
  public ResultSet runQuery(String query) throws SQLException {
    Statement stm = dbConnection.createStatement();
    // System.out.println("SQL: " + query);
    try {
      return stm.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    stm.close();
    return null;
  }

  public void dropAllTables() throws SQLException {

    // DROP ALL REQUEST
    dropTableIfExist("externalPatientRequest");
    dropTableIfExist("MAINTENANCEREQUEST");
    dropTableIfExist("FACILITIESREQUEST");
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
    dropTableIfExist("externalPatientRequest");
    // DROP BIG TABLES
    dropTableIfExist("ServiceRequest");
    dropTableIfExist("MedicalEquipment");
    dropTableIfExist("Locations");
  }

  public void dropTableIfExist(String droppingTable) throws SQLException {
    if (dbConnection
        .getMetaData()
        .getTables(null, null, droppingTable.toUpperCase(), null)
        .next()) {
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
  public void backUpDatabaseToCSV() throws SQLException, IOException {
    locationsDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/TowerLocations.csv");
    medicalEquipmentDAO.backUpToCSV(
        "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/equipment.csv");
    medicalEquipmentDeliveryRequestDAO.backUpToCSV(
        "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/MedEquipReq.csv");
    giftDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/gifts.csv");
    labRequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/labs.csv");
    mealDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/meals.csv");
    // medicineDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/medicine.csv");
    MedicineRequest.backUpDatabase(
        "src/main/resources/apiCSV/medicine.csv", "src/main/resources/apiCSV/employees.csv");
    scanRequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/scans.csv");
    RequestDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/serviceRequest.csv");
    audioVisualDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/audioVis.csv");
    ptDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/physicaltherapy.csv");
    securityDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/security.csv");
    facilitiesDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/facilities.csv");
    extPatDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/extPatDelivery.csv");
    themeDAO.backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/themes.csv");
    System.out.println("Files backed up");
  }

  /**
   * gets the LocatationDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return LocationsDAOImpl
   */
  public LocationsDAOImpl getLocationDAO() {
    return locationsDAO;
  }

  public RequestDAOImpl getRequestDAO() {
    return RequestDAO;
  }
  /**
   * gets the MedDelReqDAOImpl object Use to use the addLocation, update, delete, etc
   *
   * @return MedDelReqDAOImpl
   */
  public EquipmentDeliveryDAOImpl getMedEquipDelReqDAO() {
    return medicalEquipmentDeliveryRequestDAO;
  }
  /**
   * Return med equipment DAO object to ad
   *
   * @return medEquipImpl DAO object
   */
  public EquipmentDAOImpl getMedEquipDAO() {
    return medicalEquipmentDAO;
  }
  /** gets the LabRequestDAO */
  public LabDAOImpl getLabRequestDAO() {
    return labRequestDAO;
  }
  /** gets the scanRequestDAO */
  public ScanDAOImpl getScanRequestDAO() {
    return scanRequestDAO;
  }

  public MealDAOImpl getMealDAO() {
    return mealDAO;
  }

  public FloralDAOImpl getFloralDAO() {
    return floralDAO;
  }

  public GiftDAOImpl getGiftDAO() {
    return giftDAO;
  }

  public MedicineDAOImpl getMedicineDAO() {
    return medicineDAO;
  }

  public EmployeeDAOImpl getEmployeeDAO() {
    return employeeDAO;
  }

  public PatientDAOImpl getPatientDAO() {
    return patientDAO;
  }

  public AudioVisualDAOImpl getAudioVisDAO() {
    return audioVisualDAO;
  }

  public MaintenanceSRDAOImpl getMaintenanceDAO() {
    return maintenanceDAO;
  }

  public FacilitiesDAOImpl getFacilitiesDAO() {
    return facilitiesDAO;
  }

  public PhysicalTherapyDAOImpl getPTDAO() {
    return ptDAO;
  }

  public SecurityDAOImpl getSecurityDAO() {
    return securityDAO;
  }

  public ExtPatientDAOImpl getExtPatDAO() {
    return extPatDAO;
  }

  public ThemeDAOImpl getThemeDAO() {
    return themeDAO;
  }
}
