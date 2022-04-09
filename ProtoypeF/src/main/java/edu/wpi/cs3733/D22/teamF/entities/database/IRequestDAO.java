package edu.wpi.cs3733.D22.teamF.entities.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * IRequestDAO is an interface implemented by every service request DAO This allows for the use of
 * the Facade design pattern by the Repository class as well as maintaining the DAO pattern for the
 * individual service requests
 */
public interface IRequestDAO {

  public void initTable(File file) throws SQLException, IOException;

  public void initTable(String filePath) throws SQLException, IOException;

  public void add(ArrayList<String> fields) throws SQLException;

  public void delete(String reqID) throws SQLException;

  public void update(ArrayList<String> fields);

  public String generateInsertStatement(ArrayList<String> fields);

  public void saveRequestToCSV(String filename) throws FileNotFoundException;
}
