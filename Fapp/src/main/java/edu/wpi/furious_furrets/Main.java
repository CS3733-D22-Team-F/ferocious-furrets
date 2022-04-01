package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
  /**
   * its main, it does main things, not not main things
   *
   * @param args
   * @throws SQLException
   * @throws IOException
   */
  public static void main(String[] args) throws SQLException, IOException {
    // objects for connection and the handler
    DatabaseHandler dbConn = new DatabaseHandler();
    Connection conn = dbConn.connectDatabase();
    // init tables for both the location and medical equip
    LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
    ldao.initTable();
    MedDelReqDAOImpl mdao = new MedDelReqDAOImpl(conn);
    mdao.initTable();
    Fapp.launch(Fapp.class, args);
  }
}
