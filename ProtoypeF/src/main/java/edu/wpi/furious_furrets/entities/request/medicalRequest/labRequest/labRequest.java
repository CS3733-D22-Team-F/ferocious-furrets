package edu.wpi.furious_furrets.entities.request.medicalRequest.labRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public abstract class labRequest extends MedicalRequest {

  protected String sampleType;

  /**
   * @param assign employee string
   * @param empID employee id
   * @param nID location node id
   * @param sts status
   * @param reqType request type
   * @param equipID id of equipment
   * @param equipType type of equipment , MRI, CAT, etc
   * @param sampleType type of sample, blood, etc
   */
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
