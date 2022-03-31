package entitites.request;

import entitites.Request;

public abstract class DeliveryRequest extends Request {

  private String deliveryID;
  private String deliveryType;

  public DeliveryRequest(String assign, int empID, String nID, String sts, String reqType, String deliveryID, String deliveryType){
    super(assign, empID, nID, sts, reqType);
    this.deliveryID = deliveryID;
    this.deliveryType = deliveryType;
  }

}