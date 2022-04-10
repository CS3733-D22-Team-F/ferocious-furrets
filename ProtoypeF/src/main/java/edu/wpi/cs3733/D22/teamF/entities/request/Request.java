package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;

public abstract class Request implements IRequest {

  protected String reqID; // id of request
  protected String assignedEmpID; // Employee that is assigned the task (First name, Last name)
  protected String requesterEmpID; // ID of the employee that requested the task (5 Digit int)
  protected String
      nodeID; // nodeID is the key for the location in which the request is directed to (Check
  // Locations.csv for examples)
  protected String status; // Status of the request (In Progress or Done)
  // TODO enum
  private String reqType; // Type of request made
  protected Repository db;

  /**
   * @param reqID reqID
   * @param nodeID location node ID
   * @param assignedEmpID requester name
   * @param requesterEmpID requester id
   * @param status request status processing/done
   */
  public Request(
      String reqID, String nodeID, String assignedEmpID, String requesterEmpID, String status) {
    this.reqID = reqID;
    this.nodeID = nodeID;
    this.assignedEmpID = assignedEmpID;
    this.requesterEmpID = requesterEmpID;
    this.status = status;
  }

  public String generateInsertStatement() {
    return String.format("INSERT INTO ServiceRequest VALUES ('%s', '%s', '%s', '%s', '%s')",
            reqID, nodeID, assignedEmpID, requesterEmpID, status);
  }

  public Request() {}

  public String getAssignedEmpID() {
    return assignedEmpID;
  }

  public void setAssignedEmpID(String assignedEmpID) {
    this.assignedEmpID = assignedEmpID;
  }

  public String getRequesterEmpID() {
    return requesterEmpID;
  }

  public void setRequesterEmpID(String requesterEmpID) {
    this.requesterEmpID = requesterEmpID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String NodeID) {
    this.nodeID = nodeID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getReqType() {
    return reqType;
  }

  public void setReqType(String reqType) {
    this.reqType = reqType;
  }

  public String getReqID() {
    return reqID;
  }

  public void setReqID(String reqID) {
    this.reqID = reqID;
  }
}
