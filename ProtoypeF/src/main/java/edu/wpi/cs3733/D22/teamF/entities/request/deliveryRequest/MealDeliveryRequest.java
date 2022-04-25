package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealDeliveryRequest extends DeliveryRequest {

  private String meal;

  public MealDeliveryRequest() {
    db = new Repository("Meal");
  }

  public MealDeliveryRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String reqEmpID,
      String status,
      String meal) {
    super(reqID, nodeID, assignedEmpID, reqEmpID, status);
    this.meal = meal;
  }

  @Override
  public void place(ArrayList<String> fields) throws SQLException {
    db.addRequest(fields);
  }

  public String getMeal() {
    return this.meal;
  }

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  public String getReqType() {
    return "Meal";
  }

  @Override
  public void cancel(String reqID) {}
}
