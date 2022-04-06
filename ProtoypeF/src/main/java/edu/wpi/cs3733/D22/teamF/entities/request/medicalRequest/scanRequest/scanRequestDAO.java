package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public interface scanRequestDAO {

  /**
   * * Gets all the current scanRequest
   *
   * @return returns an arrayList of all scanRequest objects
   * @throws SQLException throws sql exception if there is a problem getting the request objects
   */
  ArrayList<scanRequest> getAllRequests();

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
  void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String scanType)
      throws SQLException;

  /**
   * Deletes request in the table and the arrayList
   *
   * @param deletedScanRequest takes in object wanting to delete
   */
  void removeRequest(scanRequest deletedScanRequest) throws SQLException;

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
  void updateRequest(
      scanRequest updatingScanRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String scanType)
      throws SQLException;
}
