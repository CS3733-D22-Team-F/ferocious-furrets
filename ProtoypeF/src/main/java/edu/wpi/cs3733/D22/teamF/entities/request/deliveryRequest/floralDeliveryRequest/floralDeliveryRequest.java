package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.floralDeliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;

public class floralDeliveryRequest extends DeliveryRequest {

  public floralDeliveryRequest(
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
