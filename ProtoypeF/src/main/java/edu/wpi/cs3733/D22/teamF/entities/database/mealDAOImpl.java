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

public class mealDAOImpl implements IRequestDAO {
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("mealRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE mealRequest (reqID varchar(16) PRIMARY KEY, meal varChar(32))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("mealRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE mealRequest (reqID varchar(16) PRIMARY KEY, meal varChar(32))");
    List<String> lines = CSVReader.readResourceFilepath(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> mealRequestFields = new ArrayList<>();

    mealRequestFields.add(0, fields.get(0)); // request id
    mealRequestFields.add(1, fields.get(5)); // meal type

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(
        RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.runStatement(generateInsertStatement(mealRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    // ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> mealRequestFields = new ArrayList<>();

    mealRequestFields.add(0, fields.get(0)); // request id
    mealRequestFields.add(1, fields.get(1)); // meal type

    DatabaseManager.runStatement(generateInsertStatement(mealRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    String cmd = "DELETE FROM mealRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd);
    //    String cmd1 = "DELETE FROM ServiceRequest WHERE reqID = '" + reqID + "'";
    //    DatabaseManager.runStatement(cmd1);
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {
    return DatabaseManager.runQuery("SELECT * FROM MEALREQUEST");
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

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO mealRequest VALUES ('%s', '%s')", fields.get(0), fields.get(1));
  }

  public void backUpToCSV(String filename) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,meal");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("meal")));
    }

    CSVWriter.writeAllToDir(filename, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,meal");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("meal")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
