package edu.wpi.furious_furrets.entities.request.deliveryRequest;

public class giftDeliveryRequest extends DeliveryRequest {

  public giftDeliveryRequest(
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
