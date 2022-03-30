package edu.wpi.furious_furrets;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipServReqDAO {
  public ArrayList<MedEquipServReq> getAllRequests() throws SQLException;

  public void addRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException;

  public void deleteRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException;

  public void updateRequest(
      String old_reqID,
      String old_nodeID,
      String reqID,
      String nodeID,
      String employeeIDofAssignedTo,
      int status,
      String longName)
      throws SQLException;
}
