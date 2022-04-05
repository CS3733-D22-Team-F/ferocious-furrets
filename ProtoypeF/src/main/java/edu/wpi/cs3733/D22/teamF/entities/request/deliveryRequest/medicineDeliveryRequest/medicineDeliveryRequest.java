package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.medicineDeliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;

public class medicineDeliveryRequest extends DeliveryRequest {
  public medicineDeliveryRequest(
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
