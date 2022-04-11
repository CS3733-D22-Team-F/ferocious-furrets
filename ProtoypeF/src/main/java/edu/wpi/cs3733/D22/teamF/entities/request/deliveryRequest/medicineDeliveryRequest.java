package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class medicineDeliveryRequest extends DeliveryRequest {

  private String medicine;

  public medicineDeliveryRequest() {
    db = new Repository("Medicine");
  }

  public medicineDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String medicine) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.medicine = medicine;
  }

  @Override
  public void place(ArrayList<String> fields) throws SQLException {
    db.addRequest(fields);
  }

  @Override
  public void resolve(String reqID) throws SQLException {
    db.deleteRequest(reqID);
  }

  @Override
  public void modify(ArrayList<String> fields) {}

  @Override
  public void cancel(String reqID) {}
}
