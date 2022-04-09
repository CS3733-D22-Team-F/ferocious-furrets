package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.entities.request.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * IRequestDAO is an interface implemented by every service request DAO This allows for the use of
 * the Facade design pattern by the Repository class as well as maintaining the DAO pattern for the
 * individual service requests
 */
public interface IRequestDAO {

  public void initTable() throws SQLException, IOException;

  public void add(ArrayList<String> fields) throws SQLException;

  public void delete(String reqID) throws SQLException;

  public void update(ArrayList<String> fields);

  public ArrayList<IRequest> get();

  public String generateInsertStatement(ArrayList<String> fields);

  public ArrayList<IRequest> resultsFromRSET(ResultSet rset);

  public void saveRequestToCSV();
}
