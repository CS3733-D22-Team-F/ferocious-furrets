package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class AudioVisualRequest extends DeliveryRequest {

  private String accessObject;
  private String objectType;

  public AudioVisualRequest() {
    db = new Repository("AudioVisual");
  }

  public AudioVisualRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String objectType,
      String accessObject) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.accessObject = accessObject;
    this.objectType = objectType;
  }

  public AudioVisualRequest(String reqID, String objectType, String accessObject) {
    this.reqID = reqID;
    this.accessObject = accessObject;
    this.objectType = objectType;
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

  public String getReqType() {
    return "Audio/Visual";
  }

  public String getAccessObject() {
    return this.accessObject;
  }

  public String getObjectType() {
    return this.objectType;
  }
}
