package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
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
        db = DatabaseManager.getInstance().getMealDAO();
        break;
      case "PT":
        db = DatabaseManager.getInstance().getPTDAO();
        break;
      case "Gift":
        db = DatabaseManager.getInstance().getGiftDAO();
        break;
      case "Floral":
        db = DatabaseManager.getInstance().getFloralDAO();
        break;
      case "Medicine":
        db = DatabaseManager.getInstance().getMedicineDAO();
        break;
      case "Patient":
        db = DatabaseManager.getInstance().getPatientDAO();
        break;
      case "Lab":
        db = DatabaseManager.getInstance().getLabRequestDAO();
        break;
      case "Scan":
        db = DatabaseManager.getInstance().getScanRequestDAO();
        break;
      case "Equipment":
        db = DatabaseManager.getInstance().getMedEquipDelReqDAO();
        break;
      case "Maintenance":
        db = DatabaseManager.getInstance().getMaintenanceDAO();
        break;
      case "AudioVisual":
        db = DatabaseManager.getInstance().getAudioVisDAO();
        break;
      case "Facilities":
        db = DatabaseManager.getInstance().getFacilitiesDAO();
        break;
      case "Security":
        db = DatabaseManager.getInstance().getSecurityDAO();
        break;
      case "ExternalPatient":
        db = DatabaseManager.getInstance().getExtPatDAO();
        break;
      default:
        break;
    }
  }

  public void addRequest(ArrayList<String> fields) throws SQLException {
    db.add(fields);
  }

  public void deleteRequest(String reqID) throws SQLException {
    db.delete(reqID);
  }

  public void updateRequest(ArrayList<String> fields) throws SQLException {
    db.update(fields);
  }
}
