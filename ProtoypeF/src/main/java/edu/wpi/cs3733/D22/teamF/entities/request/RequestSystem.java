package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.*;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.EquipmentDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest.MaintenanceRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.LabRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.PhysicalTherapyRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.ScanRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Facade design pattern for service requests, called by Controller classes and creates new Request
 * object for the type of service request requested, abstracts backend from frontend for
 * encapsulated design
 */
public class RequestSystem {
  private IRequest request;

  /**
   * Contructor
   *
   * @param reqType String type of request
   */
  public RequestSystem(String reqType) {
    switch (reqType) {
      case "Meal":
        request = new MealDeliveryRequest();
        break;
      case "PT":
        request = new PhysicalTherapyRequest();
        break;
      case "Gift":
        request = new GiftDeliveryRequest();
        break;
      case "Medicine":
        request = new MedicineDeliveryRequest();
        break;
      case "Lab":
        request = new LabRequest();
        break;
      case "Scan":
        request = new ScanRequest();
        break;
      case "Equipment":
        request = new EquipmentDeliveryRequest();
        break;
      case "Maintenance":
        request = new MaintenanceRequest();
        break;
      case "Audio/Visual":
        request = new AudioVisualRequest();
        break;
      case "Facilities":
        request = new FacilitiesRequest();
        break;
      case "Security":
        request = new SecurityRequest();
        break;
      case "ExternalPatient":
        request = new ExtPatientDeliveryRequest();
        break;
      default:
        break;
    }
  }

  /**
   * places a request
   *
   * @param fields
   */
  public void placeRequest(ArrayList<String> fields) {
    try {
      request.place(fields);
    } catch (java.sql.SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  /**
   * marks a request as resoved
   *
   * @param nodeID String location id
   * @throws SQLException
   */
  public void resolveRequest(String nodeID) throws SQLException {
    request.resolve(nodeID);
  }

  /**
   * modifys a request taking in a arraylist of the fields
   *
   * @param fields ArrayLiss</String>
   */
  public void modifyRequest(ArrayList<String> fields) throws SQLException {
    request.modify(fields);
  }

  /**
   * Concales a request taking a nodeID of a location?
   *
   * @param nodeID String
   */
  public void cancelRequest(String nodeID) {
    request.cancel(nodeID);
  }
}
