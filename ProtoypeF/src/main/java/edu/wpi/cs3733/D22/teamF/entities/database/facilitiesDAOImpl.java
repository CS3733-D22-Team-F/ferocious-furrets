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

public class facilitiesDAOImpl implements IRequestDAO {

  @Override
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("facilitiesRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE facilitiesRequest (reqID varchar(16) PRIMARY KEY, accessObject varChar(64), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  @Override
  public void initTable(String filePath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("facilitiesRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE facilitiesRequest (reqID varchar(16) PRIMARY KEY, accessObject varChar(64), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readResourceFilepath(filePath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> facilitiesRequest = new ArrayList<>();

    facilitiesRequest.add(0, fields.get(0)); // request id
    facilitiesRequest.add(1, fields.get(1)); // type of facilities request
    DatabaseManager.runStatement(generateInsertStatement(facilitiesRequest));
  }

  @Override
  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.runQuery("SELECT * FROM FACILITIESREQUEST");
  }

  @Override
  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> facilitiesRequest = new ArrayList<>();

    facilitiesRequest.add(0, fields.get(0)); // request id
    facilitiesRequest.add(1, fields.get(5)); // facilities request type

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(
        RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.runStatement(generateInsertStatement(facilitiesRequest));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "UPDATE FACILITIESREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  @Override
  public void update(ArrayList<String> fields) throws SQLException {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE FACILITIESREQUEST SET ACCESSOBJECT = '%s' WHERE REQID = '%s'",
            fields.get(5), fields.get(0));
    try {
      DatabaseManager.runStatement(servCmd);
      DatabaseManager.runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String type = currentLineSplit[1];

    fields.add(reqID);
    fields.add(type);

    return fields;
  }

  @Override
  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO facilitiesRequest VALUES ('%s', '%s')", fields.get(0), fields.get(1));
  }

  @Override
  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,accessObject");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s", currentRow.getString("reqID"), currentRow.getString("accessObject")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  @Override
  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,accessObject");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s", currentRow.getString("reqID"), currentRow.getString("accessObject")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
