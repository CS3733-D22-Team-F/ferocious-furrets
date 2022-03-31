package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

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
