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

public class GiftDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("GiftRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE GiftRequest (reqID varchar(16) PRIMARY KEY, gift varChar(64), FOREIGN KEY (reqID) REFERENCES SERVICEREQUEST(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("GiftRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE GiftRequest (reqID varchar(16) PRIMARY KEY, gift varChar(64), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

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
    String nodeID = currentLineSplit[1];

    fields.add(reqID);
    fields.add(nodeID);

    return fields;
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> ServiceRequestFields = new ArrayList<>();
    ArrayList<String> giftRequestFields = new ArrayList<>();

    giftRequestFields.add(0, fields.get(0)); // request id
    giftRequestFields.add(1, fields.get(5)); // gift type

    ServiceRequestFields.add(0, fields.get(0)); // request ID
    ServiceRequestFields.add(1, fields.get(1)); // node ID
    ServiceRequestFields.add(2, fields.get(2)); // assigned emp id
    ServiceRequestFields.add(3, fields.get(3)); // requester emp id
    ServiceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(ServiceRequestFields));
    DatabaseManager.getInstance().runStatement(generateInsertStatement(giftRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> giftRequestFields = new ArrayList<>();

    giftRequestFields.add(0, fields.get(0)); // request id
    giftRequestFields.add(1, fields.get(1)); // equipID

    DatabaseManager.getInstance().runStatement(generateInsertStatement(giftRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM GiftRequest WHERE reqID = '%s'", reqID));
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'", reqID));

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
            "UPDATE GIFTREQUEST SET GIFT = '%s' WHERE REQID = '%s'", fields.get(5), fields.get(0));
    try {
      DatabaseManager.getInstance().runStatement(servCmd);
      DatabaseManager.getInstance().runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet get() throws SQLException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM GiftRequest");
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
