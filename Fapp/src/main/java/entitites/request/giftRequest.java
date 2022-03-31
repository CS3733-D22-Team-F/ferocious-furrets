package entitites.request;

public class giftRequest extends DeliveryRequest {

  public giftRequest(
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
