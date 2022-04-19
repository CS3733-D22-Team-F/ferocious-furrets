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

public class securityDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("securityRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE securityRequest (reqID varchar(16), needs varChar(32), urgency varchar(32), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("securityRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE securityRequest (reqID varchar(16), needs varChar(32), urgency varchar(32), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

    List<String> lines = CSVReader.readResourceFilepath(filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String needs = currentLineSplit[1];
    String urgency = currentLineSplit[2];

    fields.add(0, reqID);
    fields.add(1, needs);
    fields.add(2, urgency);

    return fields;
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> securityRequestFields = new ArrayList<>();
    ArrayList<String> RequestFields = new ArrayList<>();

    securityRequestFields.add(0, fields.get(0)); // request id
    securityRequestFields.add(1, fields.get(5)); // needs
    securityRequestFields.add(2, fields.get(6)); // urgency

    RequestFields.add(0, fields.get(0)); // request ID
    RequestFields.add(1, fields.get(1)); // node ID
    RequestFields.add(2, fields.get(2)); // assigned emp id
    RequestFields.add(3, fields.get(3)); // requester emp id
    RequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(RequestDAOImpl.generateInsertStatementForService(RequestFields));
    DatabaseManager.runStatement(generateInsertStatement(securityRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> securityRequestFields = new ArrayList<>();

    securityRequestFields.add(0, fields.get(0)); // request id
    securityRequestFields.add(1, fields.get(1)); // needs
    securityRequestFields.add(2, fields.get(2)); // urgency

    DatabaseManager.runStatement(generateInsertStatement(securityRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'", reqID));
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM MedicineRequest WHERE reqID = '%s'", reqID));
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {
    return DatabaseManager.runQuery("SELECT * FROM securityRequest");
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO securityRequest VALUES ('%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,needs,urgency");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("needs"),
              currentRow.getString("urgency")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,needs,urgency");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("needs"),
              currentRow.getString("urgency")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
