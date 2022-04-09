package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class labDAOImpl implements IRequestDAO {

  @Override
  public void initTable() throws SQLException, IOException {
    DatabaseManager.dropTableIfExist("labRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  @Override
  public void add(ArrayList<String> fields)
      throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM labRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  @Override
  public void update(ArrayList<String> fields) {}

  @Override
  public ArrayList<IRequest> get() {
    return null;
  }

  @Override
  public String generateInsertStatement(ArrayList<String> fields) {
    return String.format(
        "INSERT INTO labRequest VALUES ('%s', '%s', '%s', '%s')",
            fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }

  @Override
  public ArrayList<IRequest> resultsFromRSET(ResultSet rset) {
    return null;
  }

  @Override
  public void saveRequestToCSV() {

  }
}
