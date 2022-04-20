package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class giftDeliveryRequest extends DeliveryRequest {

  private String gift;

  public giftDeliveryRequest() {
    db = new Repository("Gift");
  }

  public giftDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String gift) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.gift = gift;
  }

  public String getGift() {
    return gift;
  }

  public void place(ArrayList<String> fields) throws SQLException {
    db.addRequest(fields);
  }

  public void resolve(String reqID) throws SQLException {
    db.deleteRequest(reqID);
  }

  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  public String getReqType() {
    return "Gift";
  }

  public void cancel(String reqID) {}
}
