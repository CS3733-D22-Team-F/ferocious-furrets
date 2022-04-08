/**
 * Medical Equipment service request class w/ getters and setters for the params
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.DeliveryRequest;

/** Medical Delivery request object */
public class MedDelReq extends DeliveryRequest {

  String requestedEquipmentID;

  /** Construction for Med equip service */
  public MedDelReq(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String deliveryType,
      String requestedEquipmentID) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType);
    this.requestedEquipmentID = requestedEquipmentID;
  }
  /**
   * SQL insert statement
   *
   * @return String statement
   */
  public String generateInsertStatement() {
    return "INSERT INTO MEDICALEQUIPMENTDELIVERYREQUEST VALUES ('"
        + this.getReqID()
        + "', '"
        + this.getRequestedEquipmentID()
        + "', '"
        + this.getNodeID()
        + "' , '"
        + this.getAssignedEmpID()
        + "' , '"
        + this.getRequesterEmpID()
        + "' , '"
        + this.getStatus()
        + "' )";
  }

  //  /**
  //   * Equals method which checks the parameters are equal
  //   *
  //   * @param o MedEquipServReq Object to be compared with the calling MedEquipServReq object
  //   * @return True or False
  //   */
  //  public boolean equals(MedDelReq o) {
  //    return (this.requestedEquipmentID.equals(o.nodeID))
  //        && (this.nodeID.equals(o.nodeID))
  //        && (this.employeeIDofAssignedTo.equals(o.employeeIDofAssignedTo))
  //        && (this.status == o.status);
  //  }

  public String getRequestedEquipmentID() {
    return requestedEquipmentID;
  }

  public void setRequestedEquipmentID(String requestedEquipmentID) {
    this.requestedEquipmentID = requestedEquipmentID;
  }

  @Override
  public void place(String assignedID, String requestedID, String nodeID, String status) {}

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(
      String reqID, String assignedID, String requestedID, String nodeID, String status) {}

  @Override
  public void cancel(String reqID) {}
}
