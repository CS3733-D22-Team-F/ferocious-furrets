package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.medicineDeliveryRequest;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class medicineDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("MedicineRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE MedicineRequest (reqID varchar(16), medicine varChar(16), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }

  }

  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("medicineRequest");
    DatabaseManager.runStatement(
            "CREATE TABLE MedicineRequest (reqID varchar(16), medicine varChar(16), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

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
    String medicine = currentLineSplit[1];

    fields.add(0, reqID);
    fields.add(1, medicine);

    return fields;
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> MedicineRequestFields = new ArrayList<>();
    ArrayList<String> giftRequestFields = new ArrayList<>();

    giftRequestFields.add(0, fields.get(0)); // request id
    giftRequestFields.add(1, fields.get(5)); // medicine

    MedicineRequestFields.add(0, fields.get(0)); // request ID
    MedicineRequestFields.add(1, fields.get(1)); // node ID
    MedicineRequestFields.add(2, fields.get(2)); // assigned emp id
    MedicineRequestFields.add(3, fields.get(3)); // requester emp id
    MedicineRequestFields.add(4, fields.get(4)); // status

    DatabaseManager.runStatement(generateInsertStatement(giftRequestFields));
    DatabaseManager.runStatement(
            RequestDAOImpl.generateInsertStatementForService(MedicineRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    DatabaseManager.runStatement(String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'",
            reqID));
    DatabaseManager.runStatement(String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'",
            reqID));
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {
    return DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO MedicineRequest VALUES ('%s', '%s')",
        fields.get(0), fields.get(1));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,medicine");

    while (currentRow.next()) {
      toAdd.add(
              String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("medicine")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,medicine");

    while (currentRow.next()) {
      toAdd.add(
              String.format("%s,%s", currentRow.getString("reqID"), currentRow.getString("medicine")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
