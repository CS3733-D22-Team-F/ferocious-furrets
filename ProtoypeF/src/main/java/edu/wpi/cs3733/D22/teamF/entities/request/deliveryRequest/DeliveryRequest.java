package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;


public abstract class DeliveryRequest extends Request implements IRequest {

  private String deliveryType;

  public DeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType);

  }

  public DeliveryRequest(){}

  public String getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
  }
}
