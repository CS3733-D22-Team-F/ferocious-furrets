package edu.wpi.cs3733.D22.teamF.arduino;

import java.io.IOException;

public class ArduinoLogin {

  public ArduinoLogin() {}

  /** Arduino interprets 200 as the command that the user entered their username */
  public void sendUserEnteredUsername() throws IOException, InterruptedException {
    ArduinoConnection.getArduinoConnection().sendSerialString("200");
  }

  public void sendUserID(String id) throws IOException, InterruptedException {
    ArduinoConnection.getArduinoConnection().sendSerialString(id);
  }

  public boolean waitForFingerprintValidation() {
    boolean valid = false;

    return valid;
  }

  public void addFingerprint(String employeeID) throws IOException, InterruptedException {
    ArduinoConnection.getArduinoConnection().sendSerialString(employeeID);
  }
}
