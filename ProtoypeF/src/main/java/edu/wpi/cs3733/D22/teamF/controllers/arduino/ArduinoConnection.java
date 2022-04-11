package edu.wpi.cs3733.D22.teamF.controllers.arduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ArduinoConnection {

  SerialPort arduinoPort;
  //
  //  private ArduinoConnection() {}
  //
  //  public static ArduinoConnection initConnection(){
  //    return Helper.arduinoConn;
  //  }

  public ArduinoConnection(String port) throws IOException, InterruptedException {
    this.startConnection(port);
  }

  public void startConnection(String port) throws InterruptedException, IOException {
    //    arduinoPort = SerialPort.getCommPorts()[0];
    arduinoPort = SerialPort.getCommPort(port);
    arduinoPort.setComPortParameters(115200, 8, 1, 0);
    arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
    Thread.sleep(2000);

    if (arduinoPort.openPort()) {
      System.out.println(arduinoPort.getDescriptivePortName());
      System.out.println("Port opened");
    } else {
      System.out.println("Port not opened");
    }
  }

  public void testWriteConnection() throws InterruptedException, IOException {
    String stringToSend = "hello there!! Wow";
    String totalBytesToSend = (char) (stringToSend.length()) + stringToSend;
    byte[] bytes = totalBytesToSend.getBytes(StandardCharsets.UTF_8);
    arduinoPort.getOutputStream().write(bytes);
    System.out.println("Sent " + stringToSend);
    Thread.sleep(1000);
  }

  public void readSerialString() {
    byte[] sizeByte =
        new byte
            [1]; // gets the size of the message in bytes from the first bit that sends the length
    // of the string
    arduinoPort.readBytes(sizeByte, 1);
    int lengthOfMessage = (char) sizeByte[0];

    byte[] inputBytes = new byte[lengthOfMessage];
    arduinoPort.readBytes(inputBytes, lengthOfMessage);
    String s = new String(inputBytes, StandardCharsets.UTF_8);
    System.out.println(s);
  }

  public void sendSerialString(String stringToSend) throws IOException, InterruptedException {

    //    String totalBytesToSend = (char) (stringToSend.length()) + stringToSend;
    String totalBytesToSend = stringToSend;
    byte[] bytes = totalBytesToSend.getBytes(StandardCharsets.UTF_8);
    arduinoPort.getOutputStream().write(bytes);
    System.out.println("Sent " + stringToSend);
    Thread.sleep(50);
  }

  //
  //  /**
  //   * Helper for Arduino Connection Object
  //   */
  //  private static class Helper{
  //    private static final ArduinoConnection arduinoConn = new ArduinoConnection();
  //  }
  //
  //  /**
  //   * Singleton helper for the Arduino Connection object
  //   */
  //  private static class SingletonHelper{
  //    private static final ArduinoConnection ARDUINO_CONNECTION = new ArduinoConnection();
  //  }

}
