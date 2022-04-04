package edu.wpi.furious_furrets.database;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the MedDelReqDAO class to handle the Medical delivery DB necessary function
 *
 * @version 1.0
 * @see MedDelReq
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
   * @param reqID
   * @throws SQLException
   */
  void deleteRequest(LabRequest req) throws SQLException;

  /**
   * @param reqID
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
