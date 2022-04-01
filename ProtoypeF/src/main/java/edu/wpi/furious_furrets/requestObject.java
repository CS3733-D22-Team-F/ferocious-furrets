package edu.wpi.furious_furrets;

/** object for requests */
public class requestObject {
  private String employee;

  private enum status {
    blank,
    processing,
    done
  };

  private String nodeID;

  /**
   * constructer
   *
   * @param employee String
   * @param nodeID String
   */
  public requestObject(String employee, String nodeID) {
    this.employee = employee;
    this.nodeID = nodeID;
  }

  /**
   * gets the employee id
   *
   * @return String
   */
  public String getEmployee() {
    return employee;
  }

  /**
   * sets the employee id
   *
   * @param employee String
   */
  public void setEmployee(String employee) {
    this.employee = employee;
  }

  /**
   * gets the node id
   *
   * @return String
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * sets the node id
   *
   * @param nodeID String
   */
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }
}
