package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import java.sql.SQLException;
import java.util.ArrayList;

public class mealDeliveryRequest extends DeliveryRequest {

  private String meal;

  public mealDeliveryRequest() {
    db = new Repository("Meal");
  }

  public mealDeliveryRequest(
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

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(ArrayList<String> fields) {
    db.updateRequest(fields);
  }

  @Override
  public void cancel(String reqID) {}
}
