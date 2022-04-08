package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Facade design pattern for backend database design Repository is called by a Request object and
 * allows for database abstraction from the front-end
 */
public class Repository {

  private IRequestDAO db;

  public Repository(String reqType) {
    switch (reqType) {
      case "Meal":
        db = DatabaseManager.getMealDAO();
        break;
      case "Gift":
        db = DatabaseManager.getGiftDAO();
        break;
      case "Floral":
        db = DatabaseManager.getFloralDAO();
        break;
      case "Medicine":
        db = DatabaseManager.getMedicineDAO();
        break;
      case "Patient":
        db = DatabaseManager.getPatientDAO();
        break;
      case "Lab":
        db = DatabaseManager.getLabRequestDAO();
        break;
      case "Scan":
        db = DatabaseManager.getScanRequestDAO();
        break;
      default:
        break;
    }
  }

  public void addRequest(String assignedID, String requestedID, String nodeID, String status)
      throws SQLException {
    db.add(assignedID, requestedID, nodeID, status);
  }

  public void deleteRequest(String reqID) throws SQLException {
    db.delete(reqID);
  }

  public void updateRequest(
      IRequest req,
      String reqID,
      String assignedID,
      String requestedID,
      String nodeID,
      String status) {
    db.update(req, reqID, assignedID, requestedID, nodeID, status);
  }

  public ArrayList<IRequest> getAll() {
    return db.get();
  }
}
