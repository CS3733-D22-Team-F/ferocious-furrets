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

/** ARCHIVED, MEDICINEREQUEST TABLE NOW HANDLED THROUGH TEAM F MEDICINE REQUEST API */
public class MedicineDAOImpl implements IRequestDAO {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("MedicineRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE MedicineRequest (reqID varchar(16), medicine varChar(16), RxDoctor varchar(32), dosage varchar(16), totalAmount varchar(16), pharmacyAddress varchar(128), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      addInit(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("medicineRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE MedicineRequest (reqID varchar(16), medicine varChar(16), RxDoctor varchar(32), dosage varchar(16), totalAmount varchar(16), pharmacyAddress varchar(128), FOREIGN KEY (reqID) REFERENCES ServiceRequest(reqID))");

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
    String medicine = currentLineSplit[1];
    String rxDoc = currentLineSplit[2];
    String dosage = currentLineSplit[3];
    String totalAmount = currentLineSplit[4];
    String pharmAdd = currentLineSplit[5];

    fields.add(0, reqID);
    fields.add(1, medicine);
    fields.add(2, rxDoc);
    fields.add(3, dosage);
    fields.add(4, totalAmount);
    fields.add(5, pharmAdd);

    return fields;
  }

  public void add(ArrayList<String> fields) throws SQLException {
    ArrayList<String> MedicineRequestFields = new ArrayList<>();
    ArrayList<String> RequestFields = new ArrayList<>();

    MedicineRequestFields.add(0, fields.get(0)); // request id
    MedicineRequestFields.add(1, fields.get(5)); // medicine type
    MedicineRequestFields.add(2, fields.get(6)); // prescribing doctor
    MedicineRequestFields.add(3, fields.get(7)); // dosage
    MedicineRequestFields.add(4, fields.get(8)); // total amount
    MedicineRequestFields.add(5, fields.get(9)); // pharmacy address

    RequestFields.add(0, fields.get(0)); // request ID
    RequestFields.add(1, fields.get(1)); // node ID
    RequestFields.add(2, fields.get(2)); // assigned emp id
    RequestFields.add(3, fields.get(3)); // requester emp id
    RequestFields.add(4, fields.get(4)); // status

    DatabaseManager.getInstance()
        .runStatement(RequestDAOImpl.generateInsertStatementForService(RequestFields));
    DatabaseManager.getInstance().runStatement(generateInsertStatement(MedicineRequestFields));
  }

  public void addInit(ArrayList<String> fields) throws SQLException {
    ArrayList<String> medicineRequestFields = new ArrayList<>();

    medicineRequestFields.add(0, fields.get(0)); // request id
    medicineRequestFields.add(1, fields.get(1)); // medicine type
    medicineRequestFields.add(2, fields.get(2)); // prescribing doctor
    medicineRequestFields.add(3, fields.get(3)); // dosage
    medicineRequestFields.add(4, fields.get(4)); // total amount
    medicineRequestFields.add(5, fields.get(5)); // pharmacy address

    DatabaseManager.getInstance().runStatement(generateInsertStatement(medicineRequestFields));
  }

  public void delete(String reqID) throws SQLException {
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'", reqID));
    //    DatabaseManager.runStatement(
    //        String.format("DELETE FROM MedicineRequest WHERE reqID = '%s'", reqID));
    String cmd = "UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
    DatabaseManager.getInstance().runStatement(cmd);
  }

  public void update(ArrayList<String> fields) {}

  public ResultSet get() throws SQLException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM MEDICINEREQUEST");
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO MedicineRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(5));
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,medicine,RxDoctor,dosage,totalAmount,pharmacyAddress");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("medicine"),
              currentRow.getString("RxDoctor"),
              currentRow.getString("dosage"),
              currentRow.getString("totalAmount"),
              currentRow.getString("pharmacyAddress")));
    }

    currentRow.close();

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,medicine,RxDoctor,dosage,totalAmount,pharmacyAddress");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("medicine"),
              currentRow.getString("RxDoctor"),
              currentRow.getString("dosage"),
              currentRow.getString("totalAmount"),
              currentRow.getString("pharmacyAddress")));
    }
    currentRow.close();

    CSVWriter.writeAll(file, toAdd);
  }
}
