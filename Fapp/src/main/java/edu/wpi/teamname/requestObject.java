package edu.wpi.teamname;

public class requestObject {
  private String employee;

  private enum status {
    blank,
    processing,
    done
  };

  private String nodeID;

  public requestObject(String employee, String nodeID) {
    this.employee = employee;
    this.nodeID = nodeID;
  }

  public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }
}
