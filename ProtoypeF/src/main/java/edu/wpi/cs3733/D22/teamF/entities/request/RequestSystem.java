package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.*;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;
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

  public RequestSystem(String reqType) {
    switch (reqType) {
      case "Meal":
        request = new mealDeliveryRequest();
        break;
      case "Gift":
        request = new giftDeliveryRequest();
        break;
      case "Floral":
        request = new floralDeliveryRequest();
        break;
      case "Medicine":
        request = new medicineDeliveryRequest();
        break;
      case "Patient":
        request = new patientDeliveryRequest();
        break;
      case "Lab":
        request = new labRequest();
        break;
      case "Scan":
        request = new scanRequest();
        break;
      case "Equipment":
        request = new equipmentDeliveryRequest();
      default:
        break;
    }
  }

  public void placeRequest(ArrayList<String> fields) {
    try {
      request.place(fields);
    } catch (java.sql.SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void resolveRequest(String nodeID) throws SQLException {
    request.resolve(nodeID);
  }

  public void modifyRequest(ArrayList<String> fields) {
    request.modify(fields);
  }

  public void cancelRequest(String nodeID) {
    request.cancel(nodeID);
  }
}
