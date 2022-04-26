package edu.wpi.cs3733.D22.teamF.reports;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

/**
 * Generates a reports on the service request
 */
public class GenerateReport {

  DatabaseParser dbParser = new DatabaseParser();

  String reqID;
  String reqType;
  String nodeID;
  String assignedEmployee;
  String requestedEmployee;
  String status;

  /**
   *
   * @param reqID
   * @param reqType
   * @param nodeID
   * @param assignedEmployee
   * @param requestedEmployee
   * @param status
   */
  public GenerateReport(
      String reqID,
      String reqType,
      String nodeID,
      String assignedEmployee,
      String requestedEmployee,
      String status) {
    this.reqID = reqID;
    this.reqType = reqType;
    this.nodeID = nodeID;
    this.assignedEmployee = assignedEmployee;
    this.requestedEmployee = requestedEmployee;
    this.status = status;
  }

  /**
   * generates a report of generic service request taking in the file path of the report
   * @param reportFilepath
   * @throws Throwable
   */
  public void generateGenericServiceRequestReport(String reportFilepath) throws Throwable {
    String srcDoc =
        "src/main/resources/edu/wpi/cs3733/D22/teamF/ReportTemplates/Request_Template.docx";

    boolean save = true;

    WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(srcDoc));
    org.docx4j.model.datastorage.migration.VariablePrepare.prepare(wordMLPackage);
    MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

    // get date
    LocalDate date = java.time.LocalDate.now();
    String requestDate = date.toString();

    // get current time
    LocalDateTime time = java.time.LocalDateTime.now();
    String timeOfReport = time.format(DateTimeFormatter.ISO_TIME);

    String docReport = "src/main/resources/edu/wpi/cs3733/D22/teamF/Reports/RequestsReport.docx";

    HashMap<String, String> mappings = new HashMap<>();

    mappings.put("request", reqID);
    mappings.put("type", requestTypeFinder());
    mappings.put("date", requestDate);
    mappings.put("location", nodeIDFinder(nodeID));
    mappings.put("assigned", employeeIDFinder(assignedEmployee));
    mappings.put("requested", employeeIDFinder(requestedEmployee));
    mappings.put("status", status);
    mappings.put("user", UserType.getUserType());
    mappings.put("time", timeOfReport);

    documentPart.variableReplace(mappings);

    wordMLPackage.save(new java.io.File(reportFilepath));
  }

  /**
   * Generates a service request for the equipment service requests for a a specific equipment ID
   * @param reportFilepath
   * @param equipmentID
   * @throws Throwable
   */
  public void generateEquipmentServiceRequestReport(String reportFilepath, String equipmentID)
      throws Throwable {
    String srcDoc =
        "src/main/resources/edu/wpi/cs3733/D22/teamF/ReportTemplates/EquipmentRequest_Template.docx";

    boolean save = true;

    WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(srcDoc));
    org.docx4j.model.datastorage.migration.VariablePrepare.prepare(wordMLPackage);
    MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

    // get date
    LocalDate date = java.time.LocalDate.now();
    String requestDate = date.toString();

    // get current time
    LocalDateTime time = java.time.LocalDateTime.now();
    String timeOfReport = time.format(DateTimeFormatter.ISO_TIME);

    String docReport = "src/main/resources/edu/wpi/cs3733/D22/teamF/Reports/RequestsReport.docx";

    HashMap<String, String> mappings = new HashMap<>();

    mappings.put("request", reqID);
    mappings.put("type", requestTypeFinder());
    mappings.put("date", requestDate);
    mappings.put("location", nodeIDFinder(nodeID));
    mappings.put("assigned", employeeIDFinder(assignedEmployee));
    mappings.put("requested", employeeIDFinder(requestedEmployee));
    mappings.put("equipment", equipmentTypeFinder(equipmentID));
    mappings.put("status", status);
    mappings.put("user", UserType.getUserType());
    mappings.put("time", timeOfReport);

    documentPart.variableReplace(mappings);

    wordMLPackage.save(new java.io.File(reportFilepath));
  }

  /**
   * Helper methods to convert nodeID, and employee IDs to names Method to set request type given a
   * request ID
   */
  public String nodeIDFinder(String nodeID) throws SQLException {
    String name = "";
    String cmd = String.format("SELECT LONGNAME FROM LOCATIONS WHERE NODEID = '%s'", nodeID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      name = rset.getString("LONGNAME");
    }
    rset.close();
    return name;
  }

  /**
   * Returns the employee name of the employeeID
   * @param employeeID
   * @return
   * @throws SQLException
   * @throws SQLException
   */
  public String employeeIDFinder(String employeeID) throws SQLException, SQLException {
    String employeeName = "";
    String cmd =
        String.format(
            "SELECT LASTNAME, FIRSTNAME FROM EMPLOYEE WHERE EMPLOYEEID = '%s'", employeeID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      employeeName = rset.getString("LASTNAME") + ", " + rset.getString("FIRSTNAME");
    }
    rset.close();
    return employeeName;
  }

  /**
   * Returns the equipmentName from the equipmentID
   * @param equipID
   * @return
   * @throws SQLException
   */
  public String equipmentTypeFinder(String equipID) throws SQLException {
    String equipmentType = "";
    String cmd =
        String.format("SELECT EQUIPTYPE FROM MEDICALEQUIPMENT WHERE EQUIPID = '%s'", equipID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      equipmentType = rset.getString("EQUIPTYPE");
    }
    rset.close();
    return equipmentType;
  }

  /**
   *
   * @return gets the request type of something im not really sure what
   * TODO find out what
   * @throws SQLException
   */
  public String requestTypeFinder() throws SQLException {
    String requestType = "";

    String cmd = String.format("SELECT * FROM LABREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset5 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset5.next()) {
      requestType = "Lab Request";
      return requestType;
    }
    rset5.close();

    cmd = String.format("SELECT * FROM EQUIPMENTDELIVERYREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset12 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset12.next()) {
      requestType = "Equipment Delivery Request";
      return requestType;
    }
    rset12.close();

    cmd = String.format("SELECT * FROM SCANREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset10 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset10.next()) {
      requestType = "Scan Request";
      return requestType;
    }
    rset10.close();

    cmd = String.format("SELECT * FROM EQUIPMENTDELIVERYREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset1 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset1.next()) {
      requestType = "Equipment Delivery Request";
      return requestType;
    }
    rset1.close();

    cmd = String.format("SELECT * FROM AUDIOVISUALREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      requestType = "Audio Visual Request";
      return requestType;
    }
    rset.close();

    cmd = String.format("SELECT * FROM EXTERNALPATIENTREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset2 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset2.next()) {
      requestType = "External Patient Transport Request";
      return requestType;
    }
    rset2.close();

    cmd = String.format("SELECT * FROM FACILITIESREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset3 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset3.next()) {
      requestType = "Facilities Request";
      return requestType;
    }
    rset3.close();

    cmd = String.format("SELECT * FROM GIFTREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset4 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset4.next()) {
      requestType = "Gift Request";
      return requestType;
    }
    rset4.close();

    cmd = String.format("SELECT * FROM MAINTENANCEREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset6 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset6.next()) {
      requestType = "Maintenance Request";
      return requestType;
    }
    rset6.close();

    cmd = String.format("SELECT * FROM MEALREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset7 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset7.next()) {
      requestType = "Meal Request";
      return requestType;
    }
    rset7.close();

    cmd = String.format("SELECT * FROM MEDICINEREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset8 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset8.next()) {
      requestType = "Medicine Delivery Request";
      return requestType;
    }
    rset8.close();

    cmd = String.format("SELECT * FROM PTREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset9 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset9.next()) {
      requestType = "Physical Therapy Request";
      return requestType;
    }
    rset9.close();

    cmd = String.format("SELECT * FROM SECURITYREQUEST WHERE REQID = '%s'", reqID);
    ResultSet rset11 = DatabaseManager.getInstance().runQuery(cmd);
    if (rset11.next()) {
      requestType = "Security Request";
      return requestType;
    }
    rset11.close();
    return "Invalid Type";
  }
}
