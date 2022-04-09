package edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

public class floralDeliveryRequest extends DeliveryRequest {

  public floralDeliveryRequest() {
    db = new Repository("Floral");
  }

  @Override
  public void place(ArrayList<String> fields) throws SQLException {

  }

  @Override
  public void resolve(String reqID) {}

  @Override
  public void modify(ArrayList<String> fields) {}

  @Override
  public void cancel(String reqID) {}
}
