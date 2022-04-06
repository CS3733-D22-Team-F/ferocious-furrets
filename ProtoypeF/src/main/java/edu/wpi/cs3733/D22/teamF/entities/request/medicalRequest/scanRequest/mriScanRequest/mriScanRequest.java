package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.mriScanRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

/** @deprecated */
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
