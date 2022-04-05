package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.Request;

public abstract class MedicalRequest extends Request {

  public String medicalType;

  public MedicalRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType);
    this.medicalType = medicalType;
  }

  public String getMedicalType() {
    return medicalType;
  }

  public void setMedicalType(String medicalType) {
    this.medicalType = medicalType;
  }
}
