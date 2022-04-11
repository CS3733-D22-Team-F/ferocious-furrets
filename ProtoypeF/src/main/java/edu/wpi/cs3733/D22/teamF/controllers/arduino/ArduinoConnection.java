package edu.wpi.cs3733.D22.teamF.controllers.arduino;

// import arduino.*;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

public class ArduinoConnection {

  SerialPort arduinoPort;

  public ArduinoConnection() {}

  public void startConnection() throws InterruptedException, IOException {
    //    arduinoPort = SerialPort.getCommPorts()[0];
    arduinoPort = SerialPort.getCommPort("COM7");
    arduinoPort.setComPortParameters(9600, 8, 1, 0);
    arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
    Thread.sleep(2000);

    if (arduinoPort.openPort()) {
      System.out.println(arduinoPort.getDescriptivePortName());
      System.out.println("Port opened");
    } else {
      System.out.println("Port not opened");
    }
  }

  public void testConnection() throws InterruptedException, IOException {
    for (Integer i = 0; i < 5; ++i) {
      arduinoPort.getOutputStream().write(i.byteValue());
//      arduinoPort.getOutputStream().write(Byte.parseByte("Hello"));
      arduinoPort.getOutputStream().flush();
      System.out.println("Sent number: " + i);
//      System.out.println("Sent Hello");
      Thread.sleep(1000);
    }
  }
}
