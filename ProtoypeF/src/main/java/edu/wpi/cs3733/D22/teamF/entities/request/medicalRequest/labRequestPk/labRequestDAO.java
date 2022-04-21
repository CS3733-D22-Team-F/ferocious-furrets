/**
 * Interface for the MedEquipServReqDAO class with the necessary addRequest, deleteRequest, and
 * updateRequest function
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequestPk;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.LabRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ARCHIVED Interface for the LabRequestDAO class to handle the labRequest DB necessary function
 *
 * @version 1.0
 */
public interface labRequestDAO {
  ArrayList<LabRequest> getAllRequests() throws SQLException;

  /**
   * @param reqID request ID
   * @param nodeID id of location node
   * @param assignedEmpID employee name
   * @param requesterEmpID employee id
   * @param status status of request processing/done
   * @param reqType type of Request, for this always medical
   * @param medicalType type of medical, for this Lab
   * @param sampleType type of sample blood/urine
   * @throws SQLException
   */
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

  /**
   * deletes a request object
   *
   * @param deletedObject labRequest
   * @throws SQLException
   */
  void deleteRequest(LabRequest deletedObject) throws SQLException;

  /**
   * @param updatingRequest labRequest to update
   * @param reqID new reqID
   * @param nodeID new nodeID
   * @param assignedEmpID new assignedEmpID
   * @param requesterEmpID new erquesterID
   * @param status new status
   * @param reqType new reqtype
   * @param medicalType new
   * @param sampleType new
   * @throws SQLException
   */
  void updateRequest(
      LabRequest updatingRequest,
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
