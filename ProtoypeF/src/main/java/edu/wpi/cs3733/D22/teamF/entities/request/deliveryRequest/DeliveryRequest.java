package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.Request;

public abstract class DeliveryRequest extends Request {

  private String deliveryType;

  public DeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String deliveryType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType);
    this.deliveryType = deliveryType;
  }

  public String getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
  }
}
