package edu.wpi.furious_furrets.entitites.request.deliveryRequest;

public class patientDeliveryRequest extends DeliveryRequest {

  public patientDeliveryRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String deliveryID,
      String deliveryType) {
    super(assign, empID, nID, sts, reqType, deliveryID, deliveryType);
  }
}
