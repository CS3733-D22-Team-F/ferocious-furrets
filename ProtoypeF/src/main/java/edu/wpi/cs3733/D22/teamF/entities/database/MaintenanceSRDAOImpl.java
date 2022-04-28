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

public class MaintenanceSRDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("MaintenanceRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE MaintenanceRequest (reqID varchar(16) PRIMARY KEY, equipID varchar(16), maintenanceType varchar(16), "
                + "Foreign Key (reqID) references SERVICEREQUEST(reqID), FOREIGN KEY (equipID) references MedicalEquipment)");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String filePath) throws SQLException, IOException {
    try {
      DatabaseManager.getInstance()
          .runStatement(
              "CREATE TABLE MaintenanceRequest (reqID varchar(16) PRIMARY KEY, equipID varchar(16), maintenanceType varchar(16), "
                  + "Foreign Key (reqID) references SERVICEREQUEST(reqID), FOREIGN KEY (equipID) references MedicalEquipment)");
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

  private ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String maintenanceType = currentLineSplit[1];

    fields.add(reqID);
    fields.add(maintenanceType);

    return fields;
  }

  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM MaintenanceRequest");
  }

  private void addInit(ArrayList<String> fields) throws SQLException {
    if (fields.size() == 3) {
      ArrayList<String> MaintenanceFields = new ArrayList<>();

      MaintenanceFields.add(0, fields.get(0)); // request id
      MaintenanceFields.add(1, fields.get(1)); // equipID
      MaintenanceFields.add(2, fields.get(2)); // maintenanceType

      DatabaseManager.getInstance().runStatement(generateInsertStatement(MaintenanceFields));
    } else {
      System.err.println("Size of ArrayList is not equal to 3:");
      for (String s : fields) {
        System.out.println(s);
      }
    }
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> serviceRequestFields = new ArrayList<>();
    ArrayList<String> MaintenanceFields = new ArrayList<>();

    MaintenanceFields.add(0, fields.get(0)); // request id
    MaintenanceFields.add(1, fields.get(5)); // equipID
    MaintenanceFields.add(2, fields.get(6)); // maintenanceType

    serviceRequestFields.add(0, fields.get(0)); // request ID
    serviceRequestFields.add(1, fields.get(1)); // node iD
    serviceRequestFields.add(2, fields.get(2)); // assigned emp id
    serviceRequestFields.add(3, fields.get(3)); // requester emp id
    serviceRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
    DatabaseManager.getInstance().runStatement(generateInsertStatement(MaintenanceFields));
  }

  public void delete(String reqID) throws SQLException {
    String cmd = "UPDATE ServiceRequest SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
  }

  public void update(ArrayList<String> fields) throws SQLException {
    String servCmd =
        String.format(
            "UPDATE SERVICEREQUEST SET NODEID = '%s', ASSIGNEDEMPLOYEEID = '%s', REQUESTEREMPLOYEEID = '%s', STATUS = '%s' WHERE REQID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0));
    String cmd =
        String.format(
            "UPDATE MAINTENANCEREQUEST SET EQUIPID = '%s', MAINTENANCETYPE = '%s' WHERE REQID = '%s'",
            fields.get(5), fields.get(6), fields.get(0));
    try {
      DatabaseManager.getInstance().runStatement(servCmd);
      DatabaseManager.getInstance().runStatement(cmd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO MaintenanceRequest VALUES ('%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,equipID,maintenanceType");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("equipID"),
              currentRow.getString("maintenanceType")));
    }
    currentRow.close();

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,equipID,maintenanceType");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("equipID"),
              currentRow.getString("maintenanceType")));
    }
    currentRow.close();

    CSVWriter.writeAll(file, toAdd);
  }
}
