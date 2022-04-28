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

public class PhysicalTherapyDAOImpl implements IRequestDAO {
  @Override
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("PTRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE PTREQUEST (reqID varchar(16) PRIMARY KEY, type varchar(16), duration varchar(16), notes varchar(256), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  @Override
  public void initTable(String filePath) throws SQLException, IOException {
    try {
      DatabaseManager.getInstance()
          .runStatement(
              "CREATE TABLE PTREQUEST (reqID varchar(16) PRIMARY KEY, type varchar(16), duration varchar(16), notes varchar(256), Foreign Key (reqID) references SERVICEREQUEST(reqID))");
    } catch (SQLException e) {
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
    List<String> lines = CSVReader.readResourceFilepath(filePath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> PTRequestFields = new ArrayList<>();

    PTRequestFields.add(0, fields.get(0)); // request id
    PTRequestFields.add(1, fields.get(1)); // type
    PTRequestFields.add(2, fields.get(2)); // duration
    PTRequestFields.add(3, fields.get(3)); // doctor's notes

    DatabaseManager.getInstance().runStatement(generateInsertStatement(PTRequestFields));
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String type = currentLineSplit[1];
    String duration = currentLineSplit[2];
    String notes = currentLineSplit[3];

    fields.add(reqID);
    fields.add(type);
    fields.add(duration);
    fields.add(notes);

    return fields;
  }

  @Override
  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM PTREQUEST");
  }

  @Override
  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> ptRequestFields = new ArrayList<>();

    ptRequestFields.add(0, fields.get(0)); // request id
    ptRequestFields.add(1, fields.get(5)); // type
    ptRequestFields.add(2, fields.get(6)); // duration
    ptRequestFields.add(3, fields.get(7)); // doctor's note

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.getInstance().runStatement(generateInsertStatement(ptRequestFields));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
  }

  @Override
  public void update(ArrayList<String> fields) throws SQLException {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE PTREQUEST SET TYPE = '%s', DURATION = '%s', NOTES = '%s' WHERE REQID = '%s'",
            fields.get(5), fields.get(6), fields.get(7), fields.get(0));
    try {
      DatabaseManager.getInstance().runStatement(servCmd);
      DatabaseManager.getInstance().runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO PTREQUEST VALUES ('%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  @Override
  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,type,duration,notes");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("REQID"),
              currentRow.getString("TYPE"),
              currentRow.getString("DURATION"),
              currentRow.getString("NOTES")));
    }
    currentRow.close();

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  @Override
  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,type,duration,notes");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("type"),
              currentRow.getString("duration"),
              currentRow.getString("notes")));
    }
    currentRow.close();

    CSVWriter.writeAll(file, toAdd);
  }
}
