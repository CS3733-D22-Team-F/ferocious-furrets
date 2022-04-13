package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class audioVisualRequest extends DeliveryRequest {

  private String accessObject;

  public audioVisualRequest() {
    db = new Repository("AudioVisual");
  }

  public audioVisualRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String accessObject) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.accessObject = accessObject;
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
