/**
 * Interface for the MedEquipServReqDAO class with the necessary addRequest, deleteRequest, and updateRequest
 * function
 * @version 1.0
 */
package edu.wpi.furious_furrets;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipServReqDAO {
  public ArrayList<MedEquipServReq> getAllRequests() throws SQLException;

  /**
   *
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @param longName
   * @throws SQLException
   */
  public void addRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException;

  /**
   *
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @param longName
   * @throws SQLException
   */
  public void deleteRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException;

  /**
   *
   * @param old_reqID
   * @param old_nodeID
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @param longName
   * @throws SQLException
   */
  public void updateRequest(
      String old_reqID,
      String old_nodeID,
      String reqID,
      String nodeID,
      String employeeIDofAssignedTo,
      int status,
      String longName)
      throws SQLException;
}
