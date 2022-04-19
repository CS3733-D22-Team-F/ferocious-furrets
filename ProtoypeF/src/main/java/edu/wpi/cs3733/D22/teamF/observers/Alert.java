package edu.wpi.cs3733.D22.teamF.observers;

public class Alert {
  private Floor alert_floor;
  private String message;

  public Alert(Floor alertFloor, String message) {
    this.alert_floor = alertFloor;
    this.message = message;
  }

  public String getAlert_floor() {
    return alert_floor.toFloorString();
  }

  public Floor getEAlert_floor() {
    return alert_floor;
  }

  public String getMessage() {
    return message;
  }
}
