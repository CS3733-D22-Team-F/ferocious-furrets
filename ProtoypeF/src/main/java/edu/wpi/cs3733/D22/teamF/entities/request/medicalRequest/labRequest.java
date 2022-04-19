package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

/** object for labRequest ie blood/ urine tests */
public class labRequest extends MedicalRequest {

  protected String sampleType;

  public labRequest() {
    db = new Repository("Lab");
  }

  public labRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String reqEmpID,
      String status,
      String sampleType) {
    super(reqID, nodeID, assignedEmpID, reqEmpID, status, "Lab");
    this.sampleType = sampleType;
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

  /**
   * @param reqID reqID
   * @param nodeID location nodeID
   * @param assignedEmpID name of employee
   * @param requesterEmpID id of employee
   * @param status Status
   * @param reqType reqType
   * @param medicalType lab/scan
   * @param sampleType type of sample blood/urine
   */
  public labRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType) {}

  /**
   * getter for sample type
   *
   * @return String
   */
  public String getSampleType() {
    return sampleType;
  }

  /**
   * setter for sampleType
   *
   * @param sampleType String
   */
  public void setSampleType(String sampleType) {
    this.sampleType = sampleType;
  }

  /**
   * SQL insert statement
   *
   * @return String statement
   */
  public String generateInsertStatement() {
    return String.format(
        "INSERT INTO labRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
        reqID, nodeID, assignedEmpID, requesterEmpID, status, medicalType);
  }

  public String generateInsertStatement(
      String reqID,
      String assignedID,
      String requestedID,
      String nodeID,
      String status,
      String type) {
    return String.format(
        "INSERT INTO labRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
        reqID, nodeID, assignedID, requestedID, status, type);
  }
}
