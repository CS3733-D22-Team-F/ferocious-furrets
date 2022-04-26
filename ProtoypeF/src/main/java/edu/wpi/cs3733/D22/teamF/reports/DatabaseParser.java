package edu.wpi.cs3733.D22.teamF.reports;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Parse thy database */
public class DatabaseParser {

  /**
   * Returns an arraylist of all requests on the database
   *
   * @return ArrayList </RequestTree>
   * @throws SQLException
   */
  public ArrayList<RequestTree> getRequests() throws SQLException {
    ArrayList<RequestTree> requests = new ArrayList<>();
    ArrayList<String> reqIDs = new ArrayList<>();
    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      String reqID = rset.getString("REQID");
      String nodeID = rset.getString("NODEID");
      String assignedEmp = rset.getString("ASSIGNEDEMPLOYEEID");
      String requestedEmployeeID = rset.getString("REQUESTEREMPLOYEEID");
      String status = rset.getString("STATUS");
      RequestTree req = new RequestTree(reqID, nodeID, assignedEmp, requestedEmployeeID, status);
      requests.add(req);
    }
    rset.close();
    return requests;
  }
}
