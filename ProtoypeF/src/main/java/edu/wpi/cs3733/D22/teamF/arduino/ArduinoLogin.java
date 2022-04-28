package edu.wpi.cs3733.D22.teamF.arduino;

import java.io.IOException;

public class ArduinoLogin {

  String ardString = "";
  ArduinoConnection conn = ArduinoConnection.getArduinoConnection();

  public ArduinoLogin() {}

  public boolean login() throws IOException, InterruptedException {
    //    if (!conn.getSerialPort().openPort()) {
    //      conn.startConnectionSearchPorts();
    //    }
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

  public boolean reenrollFingerprint() throws IOException, InterruptedException {
    //    if (!conn.getSerialPort().openPort()) {
    //      conn.startConnectionSearchPorts();
    //    }
    boolean retVal = false;
    sendUserEnteredUsername();
    waitUntilUsernameAck();
    sendUserEnteredReEnroll();
    waitUntilUsernameAck(); // wait until "Adding Fingerprint"
    sendUserID("30");
    System.out.println("Press Finger onto Sensor");
    waitUntilUsernameAck(); // wait until "EnrollingEmployee"
    waitUntilNewFingerprintStored();
    if (ardString.equals("Stored!")) {
      retVal = true;
    }
    //    else if (ardString.equals("No Enroll Match")) {
    //      retVal = false;
    //    }
    return retVal;
  }

  /** Arduino interprets 200 as the command that the user entered their username */
  public void sendUserEnteredUsername() throws IOException, InterruptedException {
    conn.sendSerialString("200");
  }

  /** Arduino interprets 202 as the command that the user entered their username */
  public void sendUserEnteredReEnroll() throws IOException, InterruptedException {
    conn.sendSerialString("202");
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

  public void waitUntilNewFingerprintStored() throws IOException, InterruptedException {
    while (conn.getSerialPort().bytesAvailable() == 0) {}
    ardString = conn.readSerialString();
  }

  public void addFingerprint(String employeeID) throws IOException, InterruptedException {
    conn.sendSerialString(employeeID);
  }

  public void resetArdString() {
    ardString = "";
  }
}
