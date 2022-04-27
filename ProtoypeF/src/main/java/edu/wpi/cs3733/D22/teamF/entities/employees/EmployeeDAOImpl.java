package edu.wpi.cs3733.D22.teamF.entities.employees;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
  /**
   * Initalizes the table of Employee with a file
   *
   * @param file file (usally from javafx chooser)
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("Employee");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE Employee (employeeID varchar(16) PRIMARY KEY, firstName varchar(16), lastName varchar(16), salary varChar(16))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromCurrentCSVLine(currentLine));
    }
  }

  /**
   * Initalizes the table of Employee with a file
   *
   * @param filePath String filepath from resources
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String filePath) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("Employee");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE Employee (employeeID varchar(16) PRIMARY KEY, firstName varchar(16), lastName varchar(16), salary varChar(16))");

    List<String> lines = CSVReader.readResourceFilepath(filePath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeArrayListFromCurrentCSVLine(currentLine));
    }
  }

  private ArrayList<String> makeArrayListFromCurrentCSVLine(String currentLine) {
    ArrayList<String> fields = new ArrayList<>();

    String[] currentLineSplit = currentLine.split(",");
    String employeeID = currentLineSplit[0];
    String firstName = currentLineSplit[1];
    String lastName = currentLineSplit[2];
    String salary = currentLineSplit[3];

    fields.add(employeeID);
    fields.add(firstName);
    fields.add(lastName);
    fields.add(salary);

    return fields;
  }

  /**
   * results a ResultSet all the rows in the table from the database
   *
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM Employee");
  }

  /**
   * adds an row into the database from the arrayList fields
   *
   * @param fields
   * @throws SQLException
   */
  public void add(ArrayList<String> fields) throws SQLException {
    String employeeID = fields.get(0);
    String firstName = fields.get(1);
    String lastName = fields.get(2);
    String salary = fields.get(3);
    DatabaseManager.getInstance()
        .runStatement(
            String.format(
                "INSERT INTO Employee VALUES ('%s', '%s', '%s', '%s')",
                employeeID, firstName, lastName, salary));
  }

  /**
   * deletes row from database table
   *
   * @param empID String reqID using to delete
   * @throws SQLException
   */
  public void delete(String empID) throws SQLException {
    ResultSet rset =
        DatabaseManager.getInstance()
            .runQuery(
                String.format(
                    "SELECT * FROM SERVICEREQUEST WHERE ASSIGNEDEMPLOYEEID = '%s' OR REQUESTEREMPLOYEEID = '%s'",
                    empID, empID));
    while (rset.next()) {
      if (rset.getString("ASSIGNEDEMPLOYEEID").equals(empID)) {
        DatabaseManager.getInstance()
            .runStatement(
                String.format(
                    "UPDATE SERVICEREQUEST SET ASSIGNEDEMPLOYEEID = NULL WHERE REQID = '%s'",
                    rset.getString("REQID")));
      }
      if (rset.getString("REQUESTEREMPLOYEEID").equals(empID)) {
        DatabaseManager.getInstance()
            .runStatement(
                String.format(
                    "UPDATE SERVICEREQUEST SET REQUESTEREMPLOYEEID = NULL WHERE REQID = '%s'",
                    rset.getString("REQID")));
      }
    }
    rset.close();
    DatabaseManager.getInstance()
        .runStatement(String.format("DELETE FROM Employee WHERE employeeID = '%s'", empID));
  }

  public void update(ArrayList<String> fields) throws SQLException {
    DatabaseManager.getInstance()
        .runStatement(
            String.format(
                "UPDATE EMPLOYEE SET FIRSTNAME = '%s', LASTNAME = '%s', SALARY = '%s' WHERE EMPLOYEEID = '%s'",
                fields.get(1), fields.get(2), fields.get(3), fields.get(0)));
  }

  public String generateInsertStatement(ArrayList<String> fields) {
    //        return String.format("INSERT Into Employee Where values")
    return null; // dont need (for now)
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("employeeID,firstName,lastName,salary");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("employeeID"),
              currentRow.getString("firstName"),
              currentRow.getString("lastName"),
              currentRow.getString("salary")));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("employeeID,firstName,lastName,salary");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("employeeID"),
              currentRow.getString("firstName"),
              currentRow.getString("lastName"),
              currentRow.getString("salary")));
    }
    CSVWriter.writeAll(file, toAdd);
  }

  public String empIDToFirstName(String eID) throws SQLException {
    String cmd = String.format("SELECT firstName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String fName = "";
    while (rset.next()) {
      fName = rset.getString("firstName");
    }
    return fName;
  }

  public String empIDToLastName(String eID) throws SQLException {
    String cmd = String.format("SELECT lastName FROM Employee WHERE employeeID = '%s'", eID);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    String lName = "";
    while (rset.next()) {
      lName = rset.getString("lastName");
    }
    return lName;
  }
}
