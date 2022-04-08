package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.entities.request.IRequest;

import java.util.ArrayList;

public class patientDAOImpl implements IRequestDAO{

    @Override
    public void add(String assignedID, String requestedID, String nodeID, String status) {

    }

    @Override
    public void delete(IRequest req) {

    }

    @Override
    public void update(IRequest req, String reqID, String assignedID, String requestedID, String nodeID, String status) {

    }

    @Override
    public ArrayList<IRequest> get() {
        return null;
    }
}
