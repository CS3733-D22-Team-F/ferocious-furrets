package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtPatientDAOImpl implements IRequestDAO {
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("externalPatientRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE externalPatientRequest (reqID varchar(16) PRIMARY KEY, address varChar(32), method varChar(32), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("externalPatientRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE externalPatientRequest (reqID varchar(16) PRIMARY KEY, address varChar(32), method varChar(32), Foreign Key (reqID) references SERVICEREQUEST(reqID))");
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> extPatientRequest = new ArrayList<>();

    extPatientRequest.add(0, fields.get(0)); // request id
    extPatientRequest.add(1, fields.get(1)); // address
    extPatientRequest.add(2, fields.get(2)); // method
    DatabaseManager.getInstance().runStatement(generateInsertStatement(extPatientRequest));
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> extPatientRequest = new ArrayList<>();

    extPatientRequest.add(0, fields.get(0)); // request id
    extPatientRequest.add(1, fields.get(5)); // address
    extPatientRequest.add(2, fields.get(6)); // method of transport

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.getInstance().runStatement(generateInsertStatement(extPatientRequest));
  }

  public void delete(String reqID) throws SQLException {
    //    String cmd = "DELETE FROM audioVisualRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd);
    //    String cmd1 = "DELETE FROM ServiceRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd1);
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE EXTERNALPATIENTREQUEST SET ADDRESS = '%s', METHOD = '%s' WHERE REQID = '%s'",
            fields.get(5), fields.get(6), fields.get(0));
    try {
      DatabaseManager.getInstance().runStatement(servCmd);
      DatabaseManager.getInstance().runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet get() throws SQLException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM EXTERNALPATIENTREQUEST");
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String type = currentLineSplit[1];
    String object = currentLineSplit[2];

    fields.add(reqID);
    fields.add(type);
    fields.add(object);

    return fields;
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO externalPatientRequest VALUES ('%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2));
  }

  public void backUpToCSV(String filename) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,address,method");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("address"),
              currentRow.getString("method")));
    }

    CSVWriter.writeAllToDir(filename, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,address");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("address"),
              currentRow.getString("method")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
