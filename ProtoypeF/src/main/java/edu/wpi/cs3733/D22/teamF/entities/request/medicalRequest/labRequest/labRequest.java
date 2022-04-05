package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

public abstract class labRequest extends MedicalRequest {

  protected String sampleType;

  public labRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType, medicalType);
    this.sampleType = sampleType;
  }
}
