package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicineDeliveryRequest extends DeliveryRequest {

  private String medicine;
  private String RxDoctor;
  private String Dosage;
  private String totalAmount;
  private String pharmacyAddress;

  public MedicineDeliveryRequest() {
    db = new Repository("Medicine");
  }

  public MedicineDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmployeeID,
      String requesterEmployeeID,
      String status,
      String medicine,
      String RxDoctor,
      String dosage,
      String totalAmount,
      String pharmacyAddress) {
    super(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status);
    this.medicine = medicine;
    this.RxDoctor = RxDoctor;
    this.Dosage = dosage;
    this.totalAmount = totalAmount;
    this.pharmacyAddress = pharmacyAddress;
  }

  public String getMedicine() {
    return medicine;
  }

  public String getRxDoctor() {
    return RxDoctor;
  }

  public String getDosage() {
    return Dosage;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  public String getPharmacyAddress() {
    return pharmacyAddress;
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

  public String getReqType() {
    return "Medicine";
  }

  @Override
  public void cancel(String reqID) {}
}
