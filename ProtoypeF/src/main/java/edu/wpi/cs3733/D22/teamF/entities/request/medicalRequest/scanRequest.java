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

  @Override
  public void place(ArrayList<String> fields) throws SQLException {}

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(ArrayList<String> fields) {}

  @Override
  public void cancel(String reqID) {}

  public String generateInsertStatement(
      String reqID, String assignedID, String requestedID, String nodeID, String status) {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
        reqID, assignedID, requestedID, nodeID, status);
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
}
