package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.catScanRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

/** @deprecated */
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
