package edu.wpi.furious_furrets.entitites.request.medicalRequest.scanRequest;

import edu.wpi.furious_furrets.entitites.request.medicalRequest.MedicalRequest;

public class mriScanRequest extends MedicalRequest {

  public mriScanRequest(
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
