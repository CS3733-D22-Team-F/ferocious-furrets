package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class giftDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("GiftRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE GiftRequest (reqID varchar(16) PRIMARY KEY, gift varChar(16), FOREIGN KEY (reqID) REFERENCES SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("GiftRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE GiftRequest (reqID varchar(16) PRIMARY KEY, gift varChar(16), FOREIGN KEY (reqID) REFERENCES SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readResourceFilepath(filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];

    fields.add(reqID);
    fields.add(nodeID);

    return fields;
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> ServiceRequestFields = new ArrayList<>();
    ArrayList<String> giftRequestFields = new ArrayList<>();

    giftRequestFields.add(0, fields.get(0)); // request id
    giftRequestFields.add(1, fields.get(1)); // equipID

    //    ServiceRequestFields.add(0, fields.get(0)); // request ID
    //    ServiceRequestFields.add(1, fields.get(1)); // node ID
    //    ServiceRequestFields.add(2, fields.get(2)); // assigned emp id
    //    ServiceRequestFields.add(3, fields.get(3)); // requester emp id
    //    ServiceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(generateInsertStatement(giftRequestFields));
    //    DatabaseManager.runStatement(
    //        RequestDAOImpl.generateInsertStatementForService(ServiceRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    DatabaseManager.runStatement(
        String.format("DELETE FROM GiftRequest WHERE reqID = '%s'", reqID));
    DatabaseManager.runStatement(
        String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'", reqID));
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {
    return DatabaseManager.runQuery("SELECT * FROM GiftRequest");
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO giftRequest VALUES ('%s', '%s')", fields.get(0), fields.get(1));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,gift");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("gift")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,gift");

    while (currentRow.next()) {
      toAdd.add(
          String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("gift")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
