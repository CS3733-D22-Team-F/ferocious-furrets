package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * scamRequest object which extends MedicalRequest
 *
 * @author Will Huang
 */
public class scanRequest extends MedicalRequest {

  String scanType;

  public scanRequest() {
    db = new Repository("Scan");
  }

  public scanRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String reqEmpID,
      String status,
      String type) {
    super(reqID, nodeID, assignedEmpID, reqEmpID, status, type);
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
  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  @Override
  public void cancel(String reqID) {}

  public String generateInsertStatement(
      String reqID,
      String assignedID,
      String requestedID,
      String nodeID,
      String status,
      String type) {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
        reqID, nodeID, assignedID, requestedID, status, type);
  }

  public String generateInsertStatement() {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s')",
        reqID, nodeID, assignedEmpID, requesterEmpID, status, medicalType);
  }

  /**
   * Returns the String scan type
   *
   * @return returns a String Scantype
   */
  public String getScanType() {
    return scanType;
  }
  /**
   * Sets the current scan type of the object the param
   *
   * @param newScanType String to change scantype to
   */
  public void setScanType(String newScanType) {
    this.scanType = newScanType;
  }

  public String getReqType(){
    return "Scan";
  }
}
