/**
 * Medical Equipment service request class w/ getters and setters for the params
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets.entities.request.deliveryRequest.equipmentDeliveryRequest;

/** Medical Delivery request object */
public class MedDelReq {

  private final String reqID; // Primary Key
  private final String nodeID; // Foreign Key from Location
  private final String employeeIDofAssignedTo; // EMPLOYEE ID (PRIMARY KEY OF EMPLOYEE)
  private final String status; // status of the request
  private String name;

  /**
   * Construction for Med equip service request
   *
   * @param reqID primary key
   * @param nodeID foreign key
   * @param employeeIDofAssignedTo employee id
   * @param status
   */
  public MedDelReq(String reqID, String nodeID, String employeeIDofAssignedTo, String status) {
    this.reqID = reqID;
    this.nodeID = nodeID;
    this.employeeIDofAssignedTo = employeeIDofAssignedTo;
    this.status = status;
  }

  /**
   * return primary key request id
   *
   * @return String
   */
  public String getReqID() {
    return reqID;
  }

  /**
   * return foreign key node id
   *
   * @return String
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * return employee id
   *
   * @return String
   */
  public String getEmployeeIDofAssignedTo() {
    return employeeIDofAssignedTo;
  }

  /**
   * Return status
   *
   * @return int
   */
  public String getStatus() {
    return status;
  }

  /**
   * SQL insert statement
   *
   * @return String statement
   */
  public String generateInsertStatement() {
    return "INSERT INTO MedServReq VALUES ('"
        + this.reqID
        + "', '"
        + this.nodeID
        + "', '"
        + this.employeeIDofAssignedTo
        + "' , '"
        + this.status
        + "' )";
  }

  /**
   * Equals method which checks the parameters are equal
   *
   * @param o MedEquipServReq Object to be compared with the calling MedEquipServReq object
   * @return True or False
   */
  public boolean equals(MedDelReq o) {
    return (this.reqID.equals(o.nodeID))
        && (this.nodeID.equals(o.nodeID))
        && (this.employeeIDofAssignedTo.equals(o.employeeIDofAssignedTo))
        && (this.status == o.status);
  }
}
