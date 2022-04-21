/**
 * DAO for the Medical Equipmend Service Request DB with the necessary add, delete and update
 * functions
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Implementation of the MedDelReq Interface */
public class EquipmentDeliveryDAOImpl implements IRequestDAO {

  /** Constructor */
  public EquipmentDeliveryDAOImpl() {}

  /**
   * An ArrayList of all the service requests in the embedded CSV file. Provided as a data structure
   * for * the Java objects made from the embedded CSV file. The ArrayList is used to create a SQL
   * table * by generating INSERT INTO statements. @Design The method * creates a SQL statement, and
   * checks to see if the service request table has been created already. If * it has, DROP the
   * table and create a new one, just create a new one if it doesn't exist * already.
   *
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("EquipmentDeliveryRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE EquipmentDeliveryRequest (reqID varchar(16) PRIMARY KEY, equipID varchar(16), "
                + "Foreign Key(reqID) References ServiceRequest(reqID), Foreign Key(equipID) References MedicalEquipment(equipID))");

    List<String> lines = CSVReader.readResourceFilepath(filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("EquipmentDeliveryRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE EquipmentDeliveryRequest (reqID varchar(16) PRIMARY KEY, equipID varchar(16), "
                + "Foreign Key(reqID) References ServiceRequest(reqID), Foreign Key(equipID) References MedicalEquipment(equipID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM EquipmentDeliveryRequest");
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> ServiceRequestFields = new ArrayList<>();
    ArrayList<String> EquipmentDeliveryRequestFields = new ArrayList<>();

    EquipmentDeliveryRequestFields.add(0, fields.get(0)); // request id
    EquipmentDeliveryRequestFields.add(1, fields.get(5)); // equipID

    ServiceRequestFields.add(0, fields.get(0)); // request ID
    ServiceRequestFields.add(1, fields.get(1)); // node ID
    ServiceRequestFields.add(2, fields.get(2)); // assigned emp id
    ServiceRequestFields.add(3, fields.get(3)); // requester emp id
    ServiceRequestFields.add(4, fields.get(4)); // status

    String updateCmd =
        "UPDATE MEDICALEQUIPMENT SET STATUS = 'unavailable' WHERE EQUIPID = '"
            + fields.get(5)
            + "'";
    DatabaseManager.getInstance().runStatement(updateCmd);
    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(ServiceRequestFields));
    DatabaseManager.getInstance()
        .runStatement(generateInsertStatement(EquipmentDeliveryRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> EquipmentDeliveryRequestFields = new ArrayList<>();

    EquipmentDeliveryRequestFields.add(0, fields.get(0)); // request id
    EquipmentDeliveryRequestFields.add(1, fields.get(1)); // equipID

    DatabaseManager.getInstance()
        .runStatement(generateInsertStatement(EquipmentDeliveryRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    String cmd = "DELETE FROM EquipmentDeliveryRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd);
    //    String cmd1 = "DELETE FROM ServiceRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd1);
    // mark request status as 'done'
    // move equipment to dirty storage for cleaning
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
    ResultSet rset =
        DatabaseManager.getInstance()
            .runQuery("Select * from EQUIPMENTDELIVERYREQUEST WHERE REQID = '" + reqID + "'");
    String eID = "";
    if (rset.next()) {
      eID = rset.getString("equipID");
    }
    rset.close();
    String cmd1 = "UPDATE MEDICALEQUIPMENT SET NODEID = 'fSTOR00503' WHERE EQUIPID = '" + eID + "'";
    DatabaseManager.getInstance().runStatement(cmd1);
  }

  public void update(ArrayList<String> fields) throws SQLException {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE EQUIPMENTDELIVERYREQUEST SET EQUIPID = '%s' WHERE REQID = '%s'",
            fields.get(5), fields.get(0));
    DatabaseManager.getInstance().runStatement(servCmd);
    DatabaseManager.getInstance().runStatement(cmd);
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO EQUIPMENTDELIVERYREQUEST VALUES ('%s', '%s')", fields.get(0), fields.get(1));
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String equipID = currentLineSplit[1];

    fields.add(0, reqID);
    fields.add(1, equipID);

    return fields;
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,equipID");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("equipID")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,equipID");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("equipID")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
