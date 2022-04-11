package edu.wpi.cs3733.D22.teamF.controllers.arduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ArduinoConnection {

  SerialPort arduinoPort;

  public ArduinoConnection() {}

  public void startConnection() throws InterruptedException, IOException {
    //    arduinoPort = SerialPort.getCommPorts()[0];
    arduinoPort = SerialPort.getCommPort("COM7");
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
    //    for (Integer i = 0; i < 5; ++i) {
    //      arduinoPort.getOutputStream().write(i.byteValue());
    String stringToSend = "Bello The";
    String totalBytesToSend = (char)stringToSend.length() + stringToSend;
    byte[] bytes = totalBytesToSend.getBytes(StandardCharsets.UTF_8);
    arduinoPort.getOutputStream().write(bytes);
    //      arduinoPort.getOutputStream().flush();
    //      System.out.println("Sent number: " + i);
    System.out.println("Sent " + stringToSend);
    Thread.sleep(1000);
    //    }
  }

  public void testReadConnection() {
//    char sizeByte = 0;
//    arduinoPort.readBytes(sizeByte, 1);
    byte[] inputBytes = new byte[32];
    arduinoPort.readBytes(inputBytes, inputBytes.length);
    String s = new String(inputBytes, StandardCharsets.UTF_8);
    System.out.println(s);
  }
}
