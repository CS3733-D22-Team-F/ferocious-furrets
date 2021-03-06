package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;

public abstract class MedicalRequest extends Request implements IRequest {

  public String medicalType;

  public MedicalRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String type) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status);
    medicalType = type;
  }

  public MedicalRequest() {}

  public String getMedicalType() {
    return this.medicalType;
  }

  public void setMedicalType(String medicalType) {
    this.medicalType = medicalType;
  }
}
