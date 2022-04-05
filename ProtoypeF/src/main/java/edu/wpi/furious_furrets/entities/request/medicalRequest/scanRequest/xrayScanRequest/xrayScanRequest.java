package edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.xrayScanRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class xrayScanRequest extends MedicalRequest {

  public xrayScanRequest(
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
