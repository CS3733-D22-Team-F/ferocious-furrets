package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
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
        "CREATE TABLE scanRequest (reqID varchar(16) FOREIGN KEY, type varchar(16))");

    ArrayList<scanRequest> RequestListFromCSV = new ArrayList<scanRequest>();
    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  @Override
  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("scanRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE scanRequest (reqID varchar(16) PRIMARY KEY, type varchar(16)) foreign key (reqID) references SERVICEREQUEST(reqID)");
    ArrayList<scanRequest> RequestListFromCSV = new ArrayList<scanRequest>();
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> scanRequestFields = new ArrayList<>();

    scanRequestFields.add(0, fields.get(0)); // request id
    scanRequestFields.add(1, fields.get(5)); // type

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(
        RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.runStatement(generateInsertStatement(scanRequestFields));
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];
    String assignedEmployeeID = currentLineSplit[2];
    String reqEmpID = currentLineSplit[3];
    String status = currentLineSplit[4];
    String type = currentLineSplit[5];

    fields.add(reqID);
    fields.add(nodeID);
    fields.add(assignedEmployeeID);
    fields.add(reqEmpID);
    fields.add(status);
    fields.add(type);

    return fields;
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM scanRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
    String cmd1 = "DELETE FROM ServiceRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd1);
  }

  @Override
  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {

    return DatabaseManager.runQuery("SELECT * FROM SCANREQUEST");
  }

  @Override
  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s')", fields.get(0), fields.get(5));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,type");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("type")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,type");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("type")));
    }

    CSVWriter.writeAll(file, toAdd);
  }

  public ArrayList<scanRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }
}
