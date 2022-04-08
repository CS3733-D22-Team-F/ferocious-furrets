package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;


import java.util.ArrayList;

/**
 * Facade design pattern for backend database design
 * Repository is called by a Request object and allows for database abstraction from the front-end
 */
public class Repository {

    private IRequestDAO db;

    public Repository(String reqType){
        switch(reqType){
            case "Meal":
                db = new mealDAOImpl();
                break;
            case "Gift":
                db = new giftDAOImpl();
                break;
            case "Floral":
                db = new floralDAOImpl();
                break;
            case "Medicine":
                db = new medicineDAOImpl();
                break;
            case "Patient":
                db = new patientDAOImpl();
                break;
            case "Lab":
                db = new labDAOImpl();
                break;
            case "Scan":
                db = new scanDAOImpl();
                break;
            default: break;
        }
    }

    public void addRequest(String assignedID, String requestedID, String nodeID, String status){
        db.add(assignedID, requestedID, nodeID, status);
    }

    public void deleteRequest(IRequest req){
        db.delete(req);
    }

    public void updateRequest(IRequest req, String reqID, String assignedID, String requestedID, String nodeID, String status){
        db.update(req, reqID, assignedID, requestedID, nodeID, status);
    }

    public ArrayList<IRequest> getAll(){
        return db.get();
    }
}
