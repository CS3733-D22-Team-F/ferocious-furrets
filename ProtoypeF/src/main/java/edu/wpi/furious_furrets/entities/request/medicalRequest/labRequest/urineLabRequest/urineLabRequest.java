package edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest.urineLabRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest.labRequest;

public class urineLabRequest extends labRequest {

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
