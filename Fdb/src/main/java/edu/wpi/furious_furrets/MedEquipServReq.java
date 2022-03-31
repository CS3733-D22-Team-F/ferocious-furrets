/**
 * Medical Equipment service request class w/ getters and setters for the params
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets;

public class MedEquipServReq {

  private String reqID; // Primary Key
  private String nodeID; // Foreign Key from Location
  private String employeeIDofAssignedTo; // EMPLOYEE ID (PRIMARY KEY OF EMPLOYEE)
  private int status;
  private String longName;

  /**
   * Construction for Med equip service request
   *
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @param longName
   */
  public MedEquipServReq(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName) {
    this.reqID = reqID;
    this.nodeID = nodeID;
    this.employeeIDofAssignedTo = employeeIDofAssignedTo;
    this.status = status;
    this.longName = longName;
  }

  public String getReqID() {
    return reqID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public String getEmployeeIDofAssignedTo() {
    return employeeIDofAssignedTo;
  }

  public int getStatus() {
    return status;
  }

  public String getLongName() {
    return longName;
  }

  public String generateInsertStatement() {
    return "INSERT INTO MedServReq VALUES ('"
        + this.reqID
        + "', '"
        + this.nodeID
        + "', '"
        + this.employeeIDofAssignedTo
        + "' , "
        + this.status
        + ", '"
        + this.longName
        + "')";
  }

  /**
   * Equals method which checks the parameters are equal
   *
   * @param o MedEquipServReq Object to be compared with the calling MedEquipServReq object
   * @return True or False
   */
  public boolean equals(MedEquipServReq o) {
    return (this.reqID.equals(o.nodeID))
        && (this.nodeID.equals(o.nodeID))
        && (this.employeeIDofAssignedTo.equals(o.employeeIDofAssignedTo))
        && (this.status == o.status)
        && (this.longName.equals(o.longName));
  }

  //  public static MedEquipServReq findByPrimaryKey(String reqID, ArrayList<MedEquipServReq> req) {
  //    for (MedEquipServReq currentReq : req) {
  //    }
  //  }

}
