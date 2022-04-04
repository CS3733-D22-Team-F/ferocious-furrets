package edu.wpi.furious_furrets.entities;

public abstract class Request {

    private String reqID; // id of request
    private String assign; // Employee that is assigned the task (First name, Last name)
    private int empID; // ID of the employee that requested the task (5 Digit int)
    private String
            nID; // nodeID is the key for the location in which the request is directed to (Check
    // Locations.csv for examples)
    private String sts; // Status of the request (In Progress or Done)
    // TODO enum
    private String
            reqType; // Type of request made, Patient Bed (PTBD), Recliner (RECL), X-Ray Machine (XRAY),
    // Infusion Pump (IPMP)

    public Request(String assign, int empID, String nID, String sts, String reqType, String reqID) {
        this.reqID = reqID;
        this.assign = assign;
        this.empID = empID;
        this.nID = nID;
        this.sts = sts;
        this.reqType = reqType;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getnID() {
        return nID;
    }

    public void setnID(String nID) {
        this.nID = nID;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }
}
