package edu.wpi.cs3733.D22.teamF.controllers.fxml;

public class UserType {
  public static String userType;

  public UserType() {
    userType = "";
  }

  public void setUserType(String clearance) {
    userType = clearance;
  }

  public String getUserType() {
    return this.userType;
  }
}
