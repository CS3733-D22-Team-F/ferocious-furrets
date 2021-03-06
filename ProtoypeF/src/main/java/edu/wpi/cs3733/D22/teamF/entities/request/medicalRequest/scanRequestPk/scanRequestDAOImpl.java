package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequestPk;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.ScanRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ARCHIVED Implentation of scanRequestDAO interface
 *
 * @author Will Huang
 */
public class scanRequestDAOImpl implements scanRequestDAO {

  ArrayList<ScanRequest> currentScanRequests = new ArrayList<>();

  public void initScanRequestTable() throws SQLException {
    // TODO implement read from CSV if needed
    DatabaseManager.getInstance().dropTableIfExist("scanRequest");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE scanRequest (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), reqType varChar(16), medicalType varChar(16), scanType varChar(16))");
  }

  /**
   * * Gets all the current scanRequest
   *
   * @return returns an arrayList of all scanRequest objects
   * @throws SQLException throws sql exception if there is a problem getting the request objects
   */
  public ArrayList<ScanRequest> getAllRequests() {
    return currentScanRequests;
  }

  /**
   * Add a new scan request object and adds it to the arrayList and sql table
   *
   * @param reqID requestID (primary key)
   * @param nodeID nodeID (foreign key)
   * @param assignedEmpID employee ID task is assigned to ID (foreign key)
   * @param requesterEmpID employee ID of who requested the rquest (foreign key)
   * @param status status of the request (processing, done)
   * @param scanType type of scan (xray, cat, mri)
   */
  public void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String scanType)
      throws SQLException {
    //    scanRequest addedScanRequest =
    //        new scanRequest(reqID, nodeID, assignedEmpID, requesterEmpID, status, scanType);
    //    try {
    //            System.out.println(addedScanRequest.generateInsertStatement());
    //      DatabaseManager.runStatement(addedScanRequest.generateInsertStatement());
    //      currentScanRequests.add(addedScanRequest);
    //    } catch (SQLException e) {
    //      e.printStackTrace();
    //    }
  }

  /**
   * Deletes request in the table and the arrayList
   *
   * @param deletedScanRequest takes in object wanting to delete
   */
  public void removeRequest(ScanRequest deletedScanRequest) throws SQLException {
    for (ScanRequest currentScanRequest : currentScanRequests) {
      if (currentScanRequest.getReqID().equals(deletedScanRequest.getReqID())) {
        DatabaseManager.getInstance()
            .runStatement("DELETE FROM scanRequest WHERE reqID = " + deletedScanRequest.getReqID());
        currentScanRequests.remove(currentScanRequest);
        System.out.println(
            "Successfully deleted scanRequest from database where reqID = "
                + currentScanRequest.getReqID());
        break;
      }
    }
  }

  /**
   * Updates a request to the following fields
   *
   * @param updatingScanRequest currentRequest in the table
   * @param reqID
   * @param nodeID nodeID (foreign key)
   * @param assignedEmpID employee ID task is assigned to ID (foreign key)
   * @param requesterEmpID employee ID of who requested the rquest (foreign key)
   * @param status status of the request (processing, done)
   * @param scanType type of scan (xray, cat, mri)
   * @throws SQLException throws exception with something wrong with sql
   */
  public void updateRequest(
      ScanRequest updatingScanRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String scanType)
      throws SQLException {
    for (ScanRequest currentScanRequest : currentScanRequests) {
      if (currentScanRequest.getReqID().equals(updatingScanRequest.getReqID())) {
        DatabaseManager.getInstance()
            .runStatement(
                String.format(
                    "UPDATE scanRequest SET reqID = %s, nodeID = %s, assignedEmployeeID = %s, requesterEmployeeID = %s, status = %s, scanType = %s",
                    currentScanRequest.getReqID(),
                    currentScanRequest.getNodeID(),
                    currentScanRequest.getAssignedEmpID(),
                    currentScanRequest.getRequesterEmpID(),
                    currentScanRequest.getStatus(),
                    currentScanRequest.getScanType()));
      }
    }
  }
}
