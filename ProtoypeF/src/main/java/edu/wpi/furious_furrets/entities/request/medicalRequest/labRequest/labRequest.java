package edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public abstract class labRequest extends MedicalRequest {

  protected String sampleType;

  public labRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType,
      String sampleType) {
    super(assign, empID, nID, sts, reqType, equipID, equipType);
    this.sampleType = sampleType;
  }
}
