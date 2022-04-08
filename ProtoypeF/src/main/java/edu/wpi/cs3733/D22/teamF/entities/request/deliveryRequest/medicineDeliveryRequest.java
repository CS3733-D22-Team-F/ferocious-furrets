package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;

public class medicineDeliveryRequest extends DeliveryRequest {

  public medicineDeliveryRequest() {
    db = new Repository("Medicine");
  }

  @Override
  public void place(String assignedID, String requestedID, String nodeID, String status) {}

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(
      String reqID, String assignedID, String requestedID, String nodeID, String status) {}

  @Override
  public void cancel(String reqID) {}
}
