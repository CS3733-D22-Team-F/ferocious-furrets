package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class patientDAOImpl implements IRequestDAO {

  @Override
  public void initTable() throws SQLException {
    DatabaseManager.dropTableIfExist("patientRequest");
    DatabaseManager.runStatement(
        "CREATE TABLE patientRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))");
  }

  @Override
  public void add(String assignedID, String requestedID, String nodeID, String status)
      throws SQLException {
    DatabaseManager.runStatement(generateInsertStatement(assignedID, requestedID, nodeID, status));
  }

  @Override
  public void delete(String reqID) throws SQLException {
    String cmd = "DELETE FROM patientRequest WHERE reqID = '" + reqID + "'";
    DatabaseManager.runStatement(cmd);
  }

  @Override
  public void update(
      IRequest req,
      String reqID,
      String assignedID,
      String requestedID,
      String nodeID,
      String status) {}

  @Override
  public ArrayList<IRequest> get() {
    return null;
  }

  @Override
  public String generateInsertStatement(
      String assignedID, String requestedID, String nodeID, String status) {
    return String.format(
        "INSERT INTO patientRequest VALUES ('%s', '%s', '%s', '%s')",
        assignedID, requestedID, nodeID, status);
  }
}
