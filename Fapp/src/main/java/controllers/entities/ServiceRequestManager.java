package controllers.entities;

import entitites.Request;

import java.util.LinkedList;

public class ServiceRequestManager {
    private ServiceRequestManager m_ServiceRequestManager;

    private ServiceRequestManager()
    {

    }

    /**
     * Getter for the entire ServiceRequestManager object
     * @return ServiceRequestManager object
     */
    public ServiceRequestManager getInstance()
    {
        if (m_ServiceRequestManager == null)
        {
            m_ServiceRequestManager = new ServiceRequestManager();
        }
        return m_ServiceRequestManager;
    }

    /**
     * returns a list of all of the request objects
     * @return LinkedList<Request>
     */
    public LinkedList<Request> getRequest(){
        //decide if we want a linked list or not
        return null;
    }

    /**
     * gets the next request that needs to be handled
     * @return Request
     */
    public Request poll(){

        return null;
    }

    /**
     * adds a request to the request queue, returns true on completion
     * @return boolean
     */
    public boolean add(Request r){

        return false;
    }

    /**
     * removes the specified request from the request queue, return true on completion
     * @return boolean
     */
    public boolean remove(Request r){
        //search for Request r
        return false;
    }

}
