package edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class catscanRequest extends MedicalRequest {

  public catscanRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType) {
    super(assign, empID, nID, sts, reqType, equipID, equipType);
  }
}
