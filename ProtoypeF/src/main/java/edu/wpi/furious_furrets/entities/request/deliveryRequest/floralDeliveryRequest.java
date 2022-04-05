package edu.wpi.furious_furrets.entities.request.deliveryRequest;

public class floralDeliveryRequest extends DeliveryRequest {

  public floralDeliveryRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String deliveryID,
      String deliveryType,
      String reqID) {
    super(assign, empID, nID, sts, reqType, deliveryID, deliveryType, reqID);
  }
}
