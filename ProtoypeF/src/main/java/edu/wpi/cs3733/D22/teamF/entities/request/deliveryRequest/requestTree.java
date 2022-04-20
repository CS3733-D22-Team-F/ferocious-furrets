package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public class requestTree extends Request {

  public requestTree(
      String reqID, String nodeID, String assignedEmpID, String requesterEmpID, String status) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status);
  }

  @Override
  public void place(ArrayList<String> fields) throws SQLException {}

  @Override
  public void resolve(String reqID) throws SQLException {}

  @Override
  public void modify(ArrayList<String> fields) {}

  @Override
  public void cancel(String reqID) {}

  @Override
  public String getReqID() {
    return reqID;
  }

  @Override
  public void setReqID(String reqID) {
    this.reqID = reqID;
  }

  @Override
  public String getNodeID() {
    return nodeID;
  }

  @Override
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  @Override
  public String getAssignedEmpID() {
    return assignedEmpID;
  }

  @Override
  public void setAssignedEmpID(String assignedEmpID) {
    this.assignedEmpID = assignedEmpID;
  }

  @Override
  public String getRequesterEmpID() {
    return requesterEmpID;
  }

  @Override
  public void setRequesterEmpID(String requesterEmpID) {
    this.requesterEmpID = requesterEmpID;
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }
}
