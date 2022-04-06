package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest;

import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.MedicalRequest;

public class scanRequest extends MedicalRequest {

  String scanType;

  public scanRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String scanType) {
    super(reqID, nodeID, assignedEmpID, requesterEmpID, status, "Medical", "Scan");
    this.scanType = scanType;
  }

  public String generateInsertStatement() {
    return String.format(
        "INSERT INTO scanRequest VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
        getReqID(),
        getNodeID(),
        getAssignedEmpID(),
        getRequesterEmpID(),
        getStatus(),
        "Medical",
        "Scan",
        scanType);
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
