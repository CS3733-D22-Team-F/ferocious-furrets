package edu.wpi.teamname;

import java.util.ArrayList;

public class requestObject {

  private String request;
  private String patient;
  private int roomNumber;
  private String doctor;
  private long requestID;
  private String status;

  public requestObject(String request, String patient, int roomNumber, String doctor, long requestID, ArrayList<String> serviceDetail, String status) {
    this.request = request;
    this.patient = patient;
    this.roomNumber = roomNumber;
    this.doctor = doctor;
    this.requestID = requestID;
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getPatient() {
    return patient;
  }

  public void setPatient(String patient) {
    this.patient = patient;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getDoctor() {
    return doctor;
  }

  public void setDoctor(String doctor) {
    this.doctor = doctor;
  }

  public long getRequestID() {
    return requestID;
  }

  public void setRequestID(long requestID) {
    this.requestID = requestID;
  }
}
