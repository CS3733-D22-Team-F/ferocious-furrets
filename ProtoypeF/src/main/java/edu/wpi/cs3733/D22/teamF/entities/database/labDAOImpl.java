package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class labDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("labRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), type varchar(16))");
    ArrayList<labRequest> csvLab = new ArrayList<labRequest>();
    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      labRequest addedLab = makeObjectFromString(currentLine);
      csvLab.add(addedLab);
    }

    for (labRequest currentScan : csvLab) {
      DatabaseManager.runStatement(currentScan.generateInsertStatement());
    }
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("labRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), type varchar(16))");
    ArrayList<labRequest> csvLab = new ArrayList<labRequest>();
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      labRequest addedLab = makeObjectFromString(currentLine);
      csvLab.add(addedLab);
    }

    for (labRequest currentScan : csvLab) {
      DatabaseManager.runStatement(currentScan.generateInsertStatement());
    }
  }

  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM labRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() {
    return null;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO labRequest VALUES ('%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    // TODO IMPLEMENT
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    // TODO IMPLEMENT

  }

  public ArrayList<labRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }

  private labRequest makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];
    String assignedEmployeeID = currentLineSplit[2];
    String reqEmpID = currentLineSplit[3];
    String status = currentLineSplit[4];
    String type = currentLineSplit[5];

    return new labRequest(reqID, nodeID, assignedEmployeeID, reqEmpID, status, type);
  }
}
