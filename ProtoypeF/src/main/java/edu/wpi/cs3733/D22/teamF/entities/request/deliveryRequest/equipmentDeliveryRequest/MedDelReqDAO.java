/**
 * Interface for the MedEquipServReqDAO class with the necessary addRequest, deleteRequest, and
 * updateRequest function
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the MedDelReqDAO class to handle the Medical delivery DB necessary function
 *
 * @see MedDelReq
 * @version 1.0
 */
public interface MedDelReqDAO {
  public ArrayList<MedDelReq> getAllRequests() throws SQLException;

  public void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String equipmentID)
      throws SQLException;

  public void deleteRequest(MedDelReq deletedObject) throws SQLException;

  public void updateRequest(
      MedDelReq updatingRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String requestedEquipmentID)
      throws SQLException;
}
