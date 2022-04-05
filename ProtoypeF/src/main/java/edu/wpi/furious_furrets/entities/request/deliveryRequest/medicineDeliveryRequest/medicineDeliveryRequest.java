package edu.wpi.furious_furrets.entities.request.deliveryRequest.medicineDeliveryRequest;

import edu.wpi.furious_furrets.entities.request.medicalRequest.MedicalRequest;

public class medicineDeliveryRequest extends MedicalRequest {
  public medicineDeliveryRequest(
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
