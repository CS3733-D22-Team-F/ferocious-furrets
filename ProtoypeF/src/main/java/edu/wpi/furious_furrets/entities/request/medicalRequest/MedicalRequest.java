package edu.wpi.furious_furrets.entities.request.medicalRequest;

import edu.wpi.furious_furrets.entities.Request;

public abstract class MedicalRequest extends Request {

  private String equipID;
  private String equipType;

  public MedicalRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String equipID,
      String equipType) {
    super(assign, empID, nID, sts, reqType);
    this.equipID = equipID;
    this.equipType = equipType;
  }
}