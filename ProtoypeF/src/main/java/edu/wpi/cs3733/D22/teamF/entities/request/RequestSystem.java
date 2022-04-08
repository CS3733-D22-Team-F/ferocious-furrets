package edu.wpi.cs3733.D22.teamF.entities.request;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.*;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest.labRequest;
import edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.scanRequest.scanRequest;
import edu.wpi.cs3733.D22.teamF.entities.database.Repository;

/**
 * Facade design pattern for service requests, called by Controller classes and creates new Request object for the type of
 * service request requested, abstracts backend from frontend for encapsulated design
 */
public class RequestSystem {
    private IRequest request;

    public RequestSystem(String reqType){
        switch(reqType){
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
            default: break;
        }
    }

    public void placeRequest(String assignedID, String requestedID, String nodeID, String status){
        request.place(assignedID, requestedID, nodeID, status);
    }

    public void resolveRequest(String nodeID){
        request.resolve(nodeID);
    }

    public void modifyRequest(String reqID, String assignedID, String requestedID, String nodeID, String status){
        request.modify(reqID, assignedID, requestedID, nodeID, status);
    }

    public void cancelRequest(String nodeID){
        request.cancel(nodeID);
    }
}
