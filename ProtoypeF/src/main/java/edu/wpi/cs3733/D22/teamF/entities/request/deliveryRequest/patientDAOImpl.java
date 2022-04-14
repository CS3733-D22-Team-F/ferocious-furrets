package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequestDAO;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class patientDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("patientRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE patientRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("patientRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE patientRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM patientRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() {
    return null;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO patientRequest VALUES ('%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  public ArrayList<patientDeliveryRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }

  public void backUpToCSV(String filename) throws SQLException, IOException {}

  public void backUpToCSV(File file) throws SQLException, IOException {}
}
