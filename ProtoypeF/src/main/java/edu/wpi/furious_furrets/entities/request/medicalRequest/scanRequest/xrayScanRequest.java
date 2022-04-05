package edu.wpi.furious_furrets.entities.request.medicalRequest.scanRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class xrayScanRequest extends MedicalRequest {

  public xrayScanRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType,
      String reqID) {
    super(assign, empID, nID, sts, reqType, equipID, equipType, reqID);
  }
}
