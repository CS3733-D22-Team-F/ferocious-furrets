package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;

/** @deprecated */
public class urineLabRequest extends labRequest {
  /**
   * @param reqID
   * @param nodeID
   * @param assignedEmpID
   * @param requesterEmpID
   * @param status
   * @param reqType
   * @param medicalType
   * @param sampleType
   */
  public urineLabRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType, medicalType, sampleType);
  }
}
