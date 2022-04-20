package edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest;

import edu.wpi.cs3733.D22.teamF.entities.database.Repository;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import java.sql.SQLException;
import java.util.ArrayList;

public class maintenanceSR extends Request implements IRequest {

  public String getMaintenanceType() {
    return maintenanceType;
  }

  public String getEquipID() {
    return equipID;
  }

  private String maintenanceType; // ENUM maybe (IT, etc)
  private String equipID; // equipID of needed of maintenance

  public maintenanceSR() {
    db = new Repository("Maintenance");
  }

  public maintenanceSR(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requestedEmpID,
      String status,
      String equipID,
      String maintenanceType) {
    super(reqID, nodeID, assignedEmpID, requestedEmpID, status);
    this.equipID = equipID;
    this.maintenanceType = maintenanceType;
  }

  public void place(ArrayList<String> fields) throws SQLException {
    db.addRequest(fields);
  }

  public void resolve(String reqID) throws SQLException {
    db.deleteRequest(reqID);
  }

  public void modify(ArrayList<String> fields) throws SQLException {
    db.updateRequest(fields);
  }

  public String getReqType(){
    return "Maintenance";
  }
  public void cancel(String reqID) {}
}
