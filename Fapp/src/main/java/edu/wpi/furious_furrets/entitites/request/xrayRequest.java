package edu.wpi.furious_furrets.entitites.request;

public class xrayRequest extends MedicalRequest {

  public xrayRequest(
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
