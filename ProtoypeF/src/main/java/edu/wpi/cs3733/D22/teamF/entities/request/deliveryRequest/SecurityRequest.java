package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class SecurityRequest extends DeliveryRequest {

  private String urgency;
  private String needs;

  public SecurityRequest() {
    db = new Repository("Security");
  }

  public SecurityRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String urgency,
      String needs) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.urgency = urgency;
    this.needs = needs;
  }

  public SecurityRequest(String reqID, String urgency, String needs) {
    this.reqID = reqID;
    this.urgency = urgency;
    this.needs = needs;
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
  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  @Override
  public void cancel(String reqID) {}

  public String getUrgency() {
    return this.urgency;
  }

  public String getNeeds() {
    return this.needs;
  }

  public String getReqType() {
    return "Security";
  }
}
