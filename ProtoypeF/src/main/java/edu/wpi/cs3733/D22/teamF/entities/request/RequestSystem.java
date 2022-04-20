package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.*;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.maintenceRequest.maintenanceSR;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.physicalTherapyRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest;
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
        request = new mealDeliveryRequest();
        break;
      case "PT":
        request = new physicalTherapyRequest();
        break;
      case "Gift":
        request = new giftDeliveryRequest();
        break;
      case "Medicine":
        request = new medicineDeliveryRequest();
        break;
      case "Lab":
        request = new labRequest();
        break;
      case "Scan":
        request = new scanRequest();
        break;
      case "Equipment":
        request = new equipmentDeliveryRequest();
        break;
      case "Maintenance":
        request = new maintenanceSR();
        break;
      case "Audio/Visual":
        request = new audioVisualRequest();
        break;
      case "Security":
        request = new securityRequest();
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
