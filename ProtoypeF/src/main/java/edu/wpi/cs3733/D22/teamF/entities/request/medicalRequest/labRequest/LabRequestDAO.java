/**
 * Interface for the MedEquipServReqDAO class with the necessary addRequest, deleteRequest, and
 * updateRequest function
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest.MedDelReq;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for the MedDelReqDAO class to handle the Medical delivery DB necessary function
 *
 * @version 1.0
 * @see MedDelReq
 */
public interface LabRequestDAO {
  ArrayList<labRequest> getAllRequests() throws SQLException;

  void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType)
      throws SQLException;

  void deleteRequest(labRequest deletedObject) throws SQLException;

  void updateRequest(
      labRequest updatingRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType)
      throws SQLException;
}
