package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class extPatientDeliveryRequest extends DeliveryRequest {

  private String address;
  private String method;
  private String patientName;

  public extPatientDeliveryRequest() {
    db = new Repository("ExternalPatient");
  }

  /**
   * @param reqID
   * @param nodeID OUT
   * @param assignedEmployeeID
   * @param requesterEmployeeID
   * @param status
   * @param address address of external location
   * @param method method of transportation
   */
  public extPatientDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String address,
      String method,
      String patientName) {
    super(reqID, "OUT", assignedEmployeeID, requesterEmployeeID, status);

    this.address = address;
    this.method = method;
  }

  public extPatientDeliveryRequest(
      String reqID, String address, String method, String patientName) {
    // super(reqID, "OUT", assignedEmployeeID, requesterEmployeeID, status);
    this.reqID = reqID;
    this.address = address;
    this.method = method;
    this.patientName = patientName;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
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
  public void modify(ArrayList<String> fields) {}

  @Override
  public void cancel(String reqID) {}
}
