package edu.wpi.furious_furrets.entitites.request.deliveryRequest;

import edu.wpi.furious_furrets.entitites.request.medicalRequest.MedicalRequest;

public class medicineDeliveryRequest extends MedicalRequest {
  public medicineDeliveryRequest(
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
