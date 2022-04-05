package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.giftDeliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;

public class giftDeliveryRequest extends DeliveryRequest {

  public giftDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String deliveryType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType, deliveryType);
  }
}
