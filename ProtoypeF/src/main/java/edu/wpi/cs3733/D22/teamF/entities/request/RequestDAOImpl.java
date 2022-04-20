package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.IRequestDAO;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * implementation of the IRequestDAO
 *
 * @see IRequestDAO
 */
public class RequestDAOImpl implements IRequestDAO {

  /**
   * An ArrayList of all the service requests in the embedded CSV file. Provided as a data structure
   * for * the Java objects made from the embedded CSV file. The ArrayList is used to create a SQL
   * table * by generating INSERT INTO statements. @Design The method * creates a SQL statement, and
   * checks to see if the service request table has been created already. If * it has, DROP the
   * table and create a new one, just create a new one if it doesn't exist * already.
   *
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String filepath) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("SERVICEREQUEST");
    DatabaseManager.runStatement(
        "CREATE TABLE ServiceRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), "
            + "Foreign Key(assignedEmployeeID) references EMPLOYEE(EMPLOYEEID), Foreign Key(requesterEmployeeID) references EMPLOYEE(EMPLOYEEID))");

    List<String> lines = CSVReader.readResourceFilepath(filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("ServiceRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE ServiceRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), "
            + "Foreign Key(assignedEmployeeID) references EMPLOYEE(EMPLOYEEID), Foreign Key(requesterEmployeeID) references EMPLOYEE(EMPLOYEEID))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromString(currentLine));
    }
  }

  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.runQuery("SELECT * FROM ServiceRequest");
  }

  public ResultSet getProcessing() throws SQLException, IOException {
    return DatabaseManager.runQuery(
        "SELECT * FROM ServiceRequest WHERE UPPER(status) = 'PROCESSING'");
  }

  public void add(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(generateInsertStatementForService(fields));
  }

  public void delete(String reqID) throws SQLException {
    DatabaseManager.runStatement(
        String.format("DELETE FROM ServiceRequest WHERE reqID = '%s'", reqID));
  }

  public void update(ArrayList<String> fields) throws SQLException {
    DatabaseManager.runStatement(
        String.format(
            "UPDATE ServiceRequest SET nodeID = '%s', assignedEmployeeID = '%s', requesterEmployeeID = '%s', status = '%s' WHERE reqID = '%s'",
            fields.get(1), fields.get(2), fields.get(3), fields.get(4), fields.get(0)));
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO ServiceRequest VALUES ('%s', '%s', '%s', '%s','%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4));
  }

  public static String generateInsertStatementForService(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO ServiceRequest VALUES ('%s', '%s', '%s', '%s','%s')",
        fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4));
  }

  public static ArrayList<String> makeArrayListFromString(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();
    String[] currentLineSplit = currentLine.split(",");
    String reqID = currentLineSplit[0];
    String nodeID = currentLineSplit[1];
    String assignedEmployeeID = currentLineSplit[2];
    String reqEmpID = currentLineSplit[3];
    String status = currentLineSplit[4];

    fields.add(0, reqID);
    fields.add(1, nodeID);
    fields.add(2, assignedEmployeeID);
    fields.add(3, reqEmpID);
    fields.add(4, status);

    return fields;
  }

  /**
   * Saves the Medical Equipment Request table to a csv file
   *
   * @param fileDir is the name of the file the map will be backed up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,nodeID,assEmpID,reqEmpID,status");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("nodeID"),
              currentRow.getString("assignedEmployeeID"),
              currentRow.getString("requesterEmployeeID"),
              currentRow.getString("status")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }
  /**
   * Saves the Medical Equipment Request table to a csv file
   *
   * @param file FILE FROM JAVAFXCHOOSER
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("reqID,nodeID,assEmpID,reqEmpID,status");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s,%s",
              currentRow.getString("reqID"),
              currentRow.getString("nodeID"),
              currentRow.getString("assignedEmployeeID"),
              currentRow.getString("requesterEmployeeID"),
              currentRow.getString("status")));
    }

    CSVWriter.writeAll(file, toAdd);
  }
}
