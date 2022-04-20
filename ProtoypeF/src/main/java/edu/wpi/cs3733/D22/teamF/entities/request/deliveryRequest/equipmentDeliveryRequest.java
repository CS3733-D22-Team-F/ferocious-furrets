/**
 * Medical Equipment service request class w/ getters and setters for the params
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

/** Medical Delivery request object */
public class equipmentDeliveryRequest extends DeliveryRequest {

  String requestedEquipmentID;
  Repository db;

  /** Construction for Med equip service */
  public equipmentDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String requestedEquipmentID) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status);
    this.requestedEquipmentID = requestedEquipmentID;
  }

  public equipmentDeliveryRequest() {
    db = new Repository("Equipment");
  }

  public equipmentDeliveryRequest(String equipID) {
    this.requestedEquipmentID = equipID;
    this.db = new Repository("Equipment");
  }
  /**
   * SQL insert statement
   *
   * @return String statement
   */
  public String generateInsertStatement() {
    return String.format(
        "INSERT INTO EQUIPMENTDELIVERYREQUEST VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
        reqID, requestedEquipmentID, nodeID, assignedEmpID, requesterEmpID, status);
  }

  public String getreqID() {
    return reqID;
  }

  public String getRequestedEquipmentID() {
    return requestedEquipmentID;
  }

  public void setRequestedEquipmentID(String requestedEquipmentID) {
    this.requestedEquipmentID = requestedEquipmentID;
  }

  public void place(ArrayList<String> fields) throws SQLException {
    //    db.addRequest(fields); //ADD FIELDS FOR REQ ID ALSO MAKE SURE FIELDS ARE CORRECT ORDER
    // @equipmentDeliveryDaoImpl
    db.addRequest(fields);
  }

  public void resolve(String reqID) throws SQLException {
    db.deleteRequest(reqID);
  }

  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  public void cancel(String reqID) {}

  public String getReqType(){
    return "Equipment";
  }
}
