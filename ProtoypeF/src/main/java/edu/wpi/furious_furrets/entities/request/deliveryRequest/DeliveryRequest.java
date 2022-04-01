package edu.wpi.furious_furrets.entities.request.deliveryRequest;

import edu.wpi.furious_furrets.entities.Request;

public abstract class DeliveryRequest extends Request {

  private String deliveryID;
  private String deliveryType;

  public DeliveryRequest(
      String assign,
      int empID,
      String nID,
      String sts,
      String reqType,
      String deliveryID,
      String deliveryType) {
    super(assign, empID, nID, sts, reqType);
    this.deliveryID = deliveryID;
    this.deliveryType = deliveryType;
  }

  public String getDeliveryID() {
    return deliveryID;
  }

  public void setDeliveryID(String deliveryID) {
    this.deliveryID = deliveryID;
  }

  public String getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
  }
}
