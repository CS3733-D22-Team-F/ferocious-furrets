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
  @Override
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

  public ArrayList<equipmentDeliveryRequest> get() {
    return null;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO medicalEquipmentDeliveryRequest VALUES ('%s', '%s', '%s', '%s', '%s, '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
  }

  public ArrayList<equipmentDeliveryRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }

  @Override
  public void saveRequestToCSV(String filename) throws FileNotFoundException {
    ArrayList<String> toAdd = new ArrayList<>();
    toAdd.add("reqID, equipmentID, nodeID, assignedEmpID, reqEmpID, status");
    ArrayList<equipmentDeliveryRequest> currentMedicalEquipment = get();
    for (equipmentDeliveryRequest l : currentMedicalEquipment) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s",
              l.getReqID(),
              l.getRequestedEquipmentID(),
              l.getNodeID(),
              l.getAssignedEmpID(),
              l.getRequesterEmpID(),
              l.getStatus()));
    }

    CSVWriter.writeAllToDir(filename, toAdd);
  }
}
