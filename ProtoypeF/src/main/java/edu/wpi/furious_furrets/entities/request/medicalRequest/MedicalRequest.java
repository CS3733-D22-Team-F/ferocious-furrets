package edu.wpi.furious_furrets.entities.request.medicalRequest;

import edu.wpi.furious_furrets.entities.request.Request;

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
}
