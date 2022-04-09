package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class scanDAOImpl implements IRequestDAO {
  @Override
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("scanRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE scanRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), type varchar(16))");
    ArrayList<scanRequest> csvScan = new ArrayList<scanRequest>();
    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      scanRequest addedScan = makeObjectFromString(currentLine);
      csvScan.add(addedScan);
    }

    for (scanRequest currentScan : csvScan) {
      DatabaseManager.runStatement(currentScan.generateInsertStatement());
    }
  }

  @Override
  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("scanRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE scanRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), type varchar(16))");
    ArrayList<scanRequest> csvScan = new ArrayList<scanRequest>();
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      scanRequest addedScan = makeObjectFromString(currentLine);
      csvScan.add(addedScan);
    }

    for (scanRequest currentScan : csvScan) {
      DatabaseManager.runStatement(currentScan.generateInsertStatement());
    }
  }

  private scanRequest makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];
    String assignedEmployeeID = currentLineSplit[2];
    String reqEmpID = currentLineSplit[3];
    String status = currentLineSplit[4];
    String type = currentLineSplit[5];

    return new scanRequest(reqID, nodeID, assignedEmployeeID, reqEmpID, status, type);
  }

  @Override
  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM scanRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  @Override
  public void update(ArrayList<String> fields) {}

  public ArrayList<scanRequest> get() {
    return null;
  }

  @Override
  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {}

  public void backUpToCSV(File file) throws SQLException, IOException {}

  public ArrayList<scanRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }
}
