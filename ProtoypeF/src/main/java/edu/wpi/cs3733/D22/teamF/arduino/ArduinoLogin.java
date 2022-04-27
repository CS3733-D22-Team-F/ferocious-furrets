package edu.wpi.cs3733.D22.teamF.arduino;

import java.io.IOException;

public class ArduinoLogin {

  String ardString = "";
  ArduinoConnection conn = ArduinoConnection.getArduinoConnection();

  public ArduinoLogin() {}

  public boolean login() throws IOException, InterruptedException {
    if (!conn.getSerialPort().openPort()) {
      conn.startConnectionSearchPorts();
    }
    boolean retVal = false;
    sendUserEnteredUsername();
    waitUntilUsernameAck();
    sendUserID("30");
    waitForFingerprintValidation();
    if (ardString.equals("Match")) {
      retVal = true;
    }
    return retVal;
  }

  /** Arduino interprets 200 as the command that the user entered their username */
  public void sendUserEnteredUsername() throws IOException, InterruptedException {
    conn.sendSerialString("200");
  }

  public void waitUntilUsernameAck() throws IOException, InterruptedException {
    while (conn.getSerialPort().bytesAvailable() == 0) {}
    conn.readSerialString();
  }

  public void sendUserID(String id) throws IOException, InterruptedException {
    conn.sendSerialString(id);
  }

  public boolean waitForFingerprintValidation() throws IOException, InterruptedException {
    boolean valid = false;
    while (conn.getSerialPort().bytesAvailable() == 0) {}
    ardString = conn.readSerialString(); // will either be "Match" or "No Match"
    return valid;
  }

  public void addFingerprint(String employeeID) throws IOException, InterruptedException {
    conn.sendSerialString(employeeID);
  }

  public void resetArdString() {
    ardString = "";
  }
}
