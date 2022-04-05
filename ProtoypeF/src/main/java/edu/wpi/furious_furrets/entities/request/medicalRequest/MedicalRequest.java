package edu.wpi.furious_furrets.entities.request.medicalRequest;

import edu.wpi.furious_furrets.entities.request.Request;

public abstract class MedicalRequest extends Request {

  private final String equipID;
  private final String equipType;

  public MedicalRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType,
      String reqID) {
    super(assign, empID, nID, sts, reqType, reqID);
    this.equipID = equipID;
    this.equipType = equipType;
  }
}
