package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class medicineDAOImpl implements IRequestDAO {
  @Override
  public void initTable() throws SQLException {
    DatabaseManager.dropTableIfExist("medicineRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE medicineRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  @Override
  public void add(ArrayList<String> fields)
      throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(fields));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM medicineRequest WHERE reqID = '" + reqID + "'";
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
        "INSERT INTO medicineRequest VALUES ('%s', '%s', '%s', '%s')",
            fields.get(0), fields.get(1), fields.get(2), fields.get(3));
  }
}
