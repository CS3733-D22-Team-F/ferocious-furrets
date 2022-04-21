package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FloralDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("floralRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE floralRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("floralRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE floralRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.getInstance().runStatement(generateInsertStatement(fields));
  }

  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM floralRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() {
    return null;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO floralRequest VALUES ('%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  public void backUpToCSV(String filename) {}

  public void backUpToCSV(File file) throws SQLException, IOException {}
}
