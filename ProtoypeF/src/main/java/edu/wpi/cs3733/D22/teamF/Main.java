package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.arduino.ArduinoConnection;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
  /**
   * its main, it does main things, not not main things
   *
   * @param args
   * @throws SQLException
   * @throws IOException
   */
  public static void main(String[] args) throws SQLException, IOException, InterruptedException {
    // objects for connection and the handler
    //    DatabaseInitializer dbConn =
    //        new DatabaseInitializer(); // create connection to Apache Derby database
    //    Connection conn = dbConn.getDbConnection(); // get the connection
    //        DatabaseManager dbMan =
    //            DatabaseManager.initalizeDatabaseManager(); // initialize locations and MEDDDELREQ
    /// table

    String ardString = "No Match";
    ArduinoConnection.getArduinoConnection().startConnection("COM9");
    while (ardString.equals("No Match")) {
      ArduinoConnection.getArduinoConnection().sendSerialString("200");
      while (ArduinoConnection.getArduinoConnection().getSerialPort().bytesAvailable() == 0) {}
      ArduinoConnection.getArduinoConnection().readSerialString();
      ArduinoConnection.getArduinoConnection().sendSerialString("30");
      while (ArduinoConnection.getArduinoConnection().getSerialPort().bytesAvailable() == 0) {}
      ardString = ArduinoConnection.getArduinoConnection().readSerialString();
    }

    //    Fapp.launch(Fapp.class, args);
    // backup database to csv files

  }
}
