package edu.wpi.cs3733.D22.teamF.entities.employees;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO {

  /**
   * Initalizes the table of Employee with a file
   *
   * @param file file (usally from javafx chooser)
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(File file) throws SQLException, IOException;
  /**
   * Initalizes the table of Employee with a file
   *
   * @param filePath String filepath from resources
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String filePath) throws SQLException, IOException;
  /**
   * results a ResultSet all the rows in the table from the database
   *
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public ResultSet get() throws SQLException, IOException;
  /**
   * adds an row into the database from the arrayList fields
   *
   * @param fields
   * @throws SQLException
   */
  public void add(ArrayList<String> fields) throws SQLException;
  /**
   * deletes row from database table
   *
   * @param reqID String reqID using to delete
   * @throws SQLException
   */
  public void delete(String reqID) throws SQLException;

  public void update(ArrayList<String> fields) throws SQLException;

  public String generateInsertStatement(ArrayList<String> fields);

  public void backUpToCSV(String fileDir) throws SQLException, IOException;

  public void backUpToCSV(File file) throws SQLException, IOException;
}
