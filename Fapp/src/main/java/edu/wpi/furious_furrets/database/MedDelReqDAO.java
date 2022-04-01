/**
 * Interface for the MedEquipServReqDAO class with the necessary addRequest, deleteRequest, and
 * updateRequest function
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets.database;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Interface for the MedDelReqDAO class to handle the Medical delivery DB necessary function
 *@see MedDelReq
 * @version 1.0
 */
public interface MedDelReqDAO {
  public ArrayList<MedDelReq> getAllRequests() throws SQLException;

  /**
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @throws SQLException
   * @see MedDelReq
   */
  public void addRequest(String reqID, String nodeID, String employeeIDofAssignedTo, int status)
      throws SQLException;

  /**
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @throws SQLException
   * @see MedDelReq
   */
  public void deleteRequest(String reqID, String nodeID, String employeeIDofAssignedTo, int status)
      throws SQLException;

  /**
   * @param old_reqID
   * @param old_nodeID
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @throws SQLException
   * @see MedDelReq
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
