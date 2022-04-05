package edu.wpi.furious_furrets.entities.request.deliveryRequest.mealDeliveryRequest;

import edu.wpi.furious_furrets.entities.request.deliveryRequest.DeliveryRequest;

public class mealDeliveryRequest extends DeliveryRequest {

  public mealDeliveryRequest(
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
