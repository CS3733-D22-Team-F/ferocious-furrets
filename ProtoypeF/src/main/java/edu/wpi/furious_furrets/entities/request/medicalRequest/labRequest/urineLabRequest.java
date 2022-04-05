package edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest;

public class urineLabRequest extends labRequest {

  public urineLabRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType,
      String sampleType,
      String reqID) {
    super(assign, empID, nID, sts, reqType, equipID, equipType, sampleType, reqID);
  }
}
