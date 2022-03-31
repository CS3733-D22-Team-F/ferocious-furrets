package edu.wpi.furious_furrets.entitites.request.medicalRequest.scanRequest;

import edu.wpi.furious_furrets.entitites.request.medicalRequest.MedicalRequest;

public class xrayScanRequest extends MedicalRequest {

  public xrayScanRequest(
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
