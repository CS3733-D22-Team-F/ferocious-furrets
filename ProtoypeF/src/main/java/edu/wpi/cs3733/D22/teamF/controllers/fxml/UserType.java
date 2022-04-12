package edu.wpi.cs3733.D22.teamF.controllers.fxml;

public class UserType {
  private static String userType;

  public UserType() {
    userType = "";
  }

  public static void setUserType(String clearance) {
    userType = clearance;
  }

  public static String getUserType() {
    return userType;
  }
}
