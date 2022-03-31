package edu.wpi.furious_furrets.entitites.request;

public class mealRequest extends DeliveryRequest {

  public mealRequest(
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
