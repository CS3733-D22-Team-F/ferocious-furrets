package edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.catScanRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class catscanRequest extends MedicalRequest {

  public catscanRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType, medicalType);
  }
}
