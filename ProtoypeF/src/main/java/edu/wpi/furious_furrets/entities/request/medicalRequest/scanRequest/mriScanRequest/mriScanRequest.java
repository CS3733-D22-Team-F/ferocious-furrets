package edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest.mriScanRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class mriScanRequest extends MedicalRequest {

  public mriScanRequest(
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
