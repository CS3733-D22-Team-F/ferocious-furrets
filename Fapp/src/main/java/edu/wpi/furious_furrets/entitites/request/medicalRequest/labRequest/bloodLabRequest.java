package edu.wpi.furious_furrets.entitites.request.medicalRequest.labRequest;

public class bloodLabRequest extends labRequest {

  public bloodLabRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType,
      String sampleType) {
    super(assign, empID, nID, sts, reqType, equipID, equipType, sampleType);
  }
}