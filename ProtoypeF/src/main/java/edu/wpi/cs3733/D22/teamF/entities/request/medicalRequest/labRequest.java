package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;

/** object for labRequest ie blood/ urine tests */
public class labRequest extends MedicalRequest {

  protected String sampleType;

  public labRequest() {
    db = new Repository("Lab");
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
    return "INSERT INTO LABREQUEST VALUES ('"
        + this.getReqID()
        + "', '"
        + this.getNodeID()
        + "', '"
        + this.getAssignedEmpID()
        + "' , '"
        + this.getRequesterEmpID()
        + "' , '"
        + this.getStatus()
        + "' , '"
        + this.getReqType()
        + "' , '"
        + this.getMedicalType()
        + "' , '"
        + this.getSampleType()
        + "' )";
  }
}
