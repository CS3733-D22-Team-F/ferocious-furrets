package edu.wpi.furious_furrets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Main {

  public static void main(String[] args) throws SQLException, NoSuchElementException, IOException {
  //objects for connection and the handler
    DatabaseHandler dbConn = new DatabaseHandler();
    Connection conn = dbConn.connectDatabase();
  //init tables for both the location and medical equip
    LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
    ldao.initTable();
    MedEquipServReqDAOImpl mdao = new MedEquipServReqDAOImpl(conn);
    mdao.initTable();

    // App.launch(App.class, args);

  }
}
