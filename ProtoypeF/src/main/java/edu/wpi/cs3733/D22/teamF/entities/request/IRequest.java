package edu.wpi.cs3733.D22.teamF.entities.request;

/**
 * Interface for requests, allows the use of a Facade design pattern for service requests as seen in RequestSystem
 */
public interface IRequest {

    public void place(String assignedID, String requestedID, String nodeID, String status);
    public void resolve(String reqID);
    public void modify(String reqID, String assignedID, String requestedID, String nodeID, String status);
    public void cancel(String reqID);

}
