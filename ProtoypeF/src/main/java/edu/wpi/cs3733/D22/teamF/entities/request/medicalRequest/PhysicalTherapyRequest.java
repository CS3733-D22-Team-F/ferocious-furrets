package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhysicalTherapyRequest extends MedicalRequest {

  protected String treatmentType;
  protected String duration;
  protected String notes;

  public PhysicalTherapyRequest() {
    db = new Repository("PT");
  }

  public PhysicalTherapyRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String reqEmpID,
      String status,
      String type,
      String duration,
      String notes) {
    super(reqID, nodeID, assignedEmpID, reqEmpID, status, type);
    this.duration = duration;
    this.notes = notes;
  }

  public PhysicalTherapyRequest(String type, String duration, String notes) {
    this.notes = notes;
    this.duration = duration;
    this.treatmentType = type;
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

  public String getTreatmentType() {
    return this.treatmentType;
  }

  public void setTreatmentType(String treatmentType) {
    this.treatmentType = treatmentType;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String note) {
    this.notes = note;
  }

  public String getReqType() {
    return "PT";
  }
}
