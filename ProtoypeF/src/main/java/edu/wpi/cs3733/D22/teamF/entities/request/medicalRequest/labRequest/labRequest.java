package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

/** object for labRequest ie blood/ urine tests */
public class labRequest extends MedicalRequest {

  protected String sampleType;

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
      String sampleType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, reqType, medicalType);
    this.sampleType = sampleType;
  }

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
