package edu.wpi.furious_furrets.database;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * TODO change comments Interface for the labReqDAO class to handle the Medical delivery DB
 * necessary function
 *
 * @version 1.0
 * @see LabRequest
 */
public interface labReqDAO {

  ArrayList<LabRequest> getAllRequests() throws SQLException;

  /**
   * @param reqID id of request
   * @param nodeID id of location
   * @param employeeIDofAssignedTo id of employee
   * @param status status of request
   * @param sampleType type of sample
   * @throws SQLException
   */
  void addRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, String status, String sampleType)
      throws SQLException;

  /**
   * @param req LabRequest object
   * @throws SQLException
   */
  void deleteRequest(LabRequest req) throws SQLException;

  /**
   * @param upReq LabRequest being updated
   * @param newReqID String new reqID
   * @param newNodeID String new nodeID
   * @param newEmployeeIDofAssignTo String employeeID
   * @param newStatus String newStatus
   * @param newType String
   * @throws SQLException
   */
  void updateRequest(
      LabRequest upReq,
      String newReqID,
      String newNodeID,
      String newEmployeeIDofAssignTo,
      String newStatus,
      String newType)
      throws SQLException;
}
