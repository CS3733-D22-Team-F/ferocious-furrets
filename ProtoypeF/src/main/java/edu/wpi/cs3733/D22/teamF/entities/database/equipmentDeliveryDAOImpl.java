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
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Implementation of the MedDelReq Interface */
public class equipmentDeliveryDAOImpl implements IRequestDAO {

  /** Constructor */
  public equipmentDeliveryDAOImpl() {}

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
  public void initTable(String filePath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("medicalEquipmentDeliveryRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE medicalEquipmentDeliveryRequest (reqID varchar(16) PRIMARY KEY, equipmentID varchar(16), nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");

    ArrayList<equipmentDeliveryRequest> csvMedEquip = new ArrayList<equipmentDeliveryRequest>();
    List<String> lines = CSVReader.readResourceFilepath(filePath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      equipmentDeliveryRequest addedMedEquip = makeObjectFromString(currentLine);
      csvMedEquip.add(addedMedEquip);
    }

    for (equipmentDeliveryRequest currentMedEquip : csvMedEquip) {
      DatabaseManager.runStatement(currentMedEquip.generateInsertStatement());
    }
  }
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("medicalEquipmentDeliveryRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE medicalEquipmentDeliveryRequest (reqID varchar(16) PRIMARY KEY, equipmentID varchar(16), nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");

    ArrayList<equipmentDeliveryRequest> csvMedEquip = new ArrayList<equipmentDeliveryRequest>();
    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      equipmentDeliveryRequest addedMedEquip = makeObjectFromString(currentLine);
      csvMedEquip.add(addedMedEquip);
    }

    for (equipmentDeliveryRequest currentMedEquip : csvMedEquip) {
      DatabaseManager.runStatement(currentMedEquip.generateInsertStatement());
    }
  }

  public equipmentDeliveryRequest makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];
    String assignedEmployeeID = currentLineSplit[2];
    String reqEmpID = currentLineSplit[3];
    String status = currentLineSplit[4];
    String equipID = currentLineSplit[5];

    return new equipmentDeliveryRequest(
        reqID, nodeID, assignedEmployeeID, reqEmpID, status, equipID);
  }

  @Override
  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    DatabaseManager.runStatement(
        String.format("DELETE FROM medicalEquipmentDeliveryRequest WHERE reqID = '%s'", reqID));
  }

  @Override
  public void update(ArrayList<String> fields) {}

  /**
   * returns list of all locations
   *
   * @throws SQLException
   */
  public ArrayList<equipmentDeliveryRequest> get() throws SQLException {
    ArrayList<equipmentDeliveryRequest> allEquipmentServiceRequest = new ArrayList<>();
    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM medicalEquipmentDeliveryRequest");
    while (rset.next()) {
      String reqID = rset.getString("reqID");
      String equipID = rset.getString("equipmentID");
      String nodeID = rset.getString("nodeID");
      String assEmpID = rset.getString("assignedEmployeeID");
      String reqEmpID = rset.getString("requesterEmployeeID");
      String status = rset.getString("status");

      equipmentDeliveryRequest newESR =
          new equipmentDeliveryRequest(reqID, equipID, nodeID, assEmpID, reqEmpID, status);
      allEquipmentServiceRequest.add(newESR);
    }
    rset.close();
    return allEquipmentServiceRequest;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    System.out.println(fields.size());
    return String.format(
        "INSERT INTO medicalEquipmentDeliveryRequest VALUES ('%s', '%s', '%s', '%s', '%s, '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
  }

  /**
   * Saves the Medical Equipment Request table to a csv file
   *
   * @param fileDir is the name of the file the map will be backed up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ArrayList<equipmentDeliveryRequest> equipmentDeliveryRequest = get();

    toAdd.add("reqID,equipID,nodeID,assEmpID,reqEmpID,status");
    for (equipmentDeliveryRequest e : equipmentDeliveryRequest) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s",
              e.getReqID(),
              e.getRequestedEquipmentID(),
              e.getNodeID(),
              e.getAssignedEmpID(),
              e.getRequestedEquipmentID(),
              e.getStatus()));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }
  /**
   * Saves the Medical Equipment Request table to a csv file
   *
   * @param file FILE FROM JAVAFXCHOOSER
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ArrayList<equipmentDeliveryRequest> equipmentDeliveryRequest = get();

    toAdd.add("reqID,equipID,nodeID,assEmpID,reqEmpID,status");
    for (equipmentDeliveryRequest e : equipmentDeliveryRequest) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s",
              e.getReqID(),
              e.getRequestedEquipmentID(),
              e.getNodeID(),
              e.getAssignedEmpID(),
              e.getRequestedEquipmentID(),
              e.getStatus()));
    }
    CSVWriter.writeAll(file, toAdd);
  }
}
