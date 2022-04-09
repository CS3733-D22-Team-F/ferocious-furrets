package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;

public abstract class MedicalRequest extends Request implements IRequest {

  public String medicalType;

  public MedicalRequest(
      String reqID, String nodeID, String assignedEmpID, String requesterEmpID, String status) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status);
  }

  public MedicalRequest() {}

  public String getMedicalType() {
    return medicalType;
  }

  public void setMedicalType(String medicalType) {
    this.medicalType = medicalType;
  }
}
