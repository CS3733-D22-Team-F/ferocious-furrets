package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.SQLException;

class RequestObject {

  private String reqID; // id of request
  private String assignedEmpID; // Employee that is assigned the task (First name, Last name)
  private String requesterEmpID; // ID of the employee that requested the task (5 Digit int)
  private String nodeID;
  private String status;
  // TODO enum

  /**
   * @param reqID reqID
   * @param nodeID location node ID
   * @param assignedEmpID requester name
   * @param requesterEmpID requester id
   * @param status request status processing/done
   */
  public RequestObject(
      String reqID, String nodeID, String assignedEmpID, String requesterEmpID, String status) {
    this.reqID = reqID;
    this.nodeID = nodeID;
    this.assignedEmpID = assignedEmpID;
    this.requesterEmpID = requesterEmpID;
    this.status = status;
  }

  public String getReqID() {
    return reqID;
  }

  public void setReqID(String reqID) {
    this.reqID = reqID;
  }

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

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAssignedEmpFullName() {
    String first;
    String last;
    try {
      last = DatabaseManager.getInstance().getEmployeeDAO().empIDToLastName(this.assignedEmpID);
      first = DatabaseManager.getInstance().getEmployeeDAO().empIDToFirstName(this.assignedEmpID);
      return String.format("%s, %s", last, first);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getRequesterEmpFullName() {
    String first;
    String last;
    try {
      last = DatabaseManager.getInstance().getEmployeeDAO().empIDToLastName(this.requesterEmpID);
      first = DatabaseManager.getInstance().getEmployeeDAO().empIDToFirstName(this.requesterEmpID);
      return String.format("%s, %s", last, first);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
