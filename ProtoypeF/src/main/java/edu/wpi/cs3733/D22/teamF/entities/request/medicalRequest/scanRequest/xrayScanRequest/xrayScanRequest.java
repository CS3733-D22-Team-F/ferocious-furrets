package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.xrayScanRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

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
