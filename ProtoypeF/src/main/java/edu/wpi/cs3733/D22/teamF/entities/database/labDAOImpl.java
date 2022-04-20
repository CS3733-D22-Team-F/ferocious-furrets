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

public class labDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("labRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY, type varchar(16))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("labRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY, type varchar(16))");
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> labRequestFields = new ArrayList<>();

    labRequestFields.add(0, fields.get(0)); // request id
    labRequestFields.add(1, fields.get(5)); // type

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(
        RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.runStatement(generateInsertStatement(labRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> LabRequestFields = new ArrayList<>();

    LabRequestFields.add(0, fields.get(0)); // request id
    LabRequestFields.add(1, fields.get(1)); // equipID

    DatabaseManager.runStatement(generateInsertStatement(LabRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    String cmd = "DELETE FROM labRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd);
    //    String cmd1 = "DELETE FROM ServiceRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd1);
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE LABREQUEST SET TYPE = '%s' WHERE REQID = '%s'", fields.get(5), fields.get(0));
    try {
      DatabaseManager.runStatement(servCmd);
      DatabaseManager.runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet get() throws SQLException {
    return DatabaseManager.runQuery("SELECT * FROM LABREQUEST");
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO labRequest VALUES ('%s', '%s')", fields.get(0), fields.get(1));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    // TODO IMPLEMENT
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
    // TODO IMPLEMENT
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,type");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("type")));
    }

    CSVWriter.writeAll(file, toAdd);
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
}
