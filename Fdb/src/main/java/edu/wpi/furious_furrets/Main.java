package edu.wpi.furious_furrets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Main {

  public static void main(String[] args) throws SQLException, NoSuchElementException, IOException {

    DatabaseHandler dbConn = new DatabaseHandler();
    Connection conn = dbConn.connectDatabase();

    LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
    ldao.initTable();
    MedEquipServReqDAOImpl mdao = new MedEquipServReqDAOImpl(conn);
    mdao.initTable();

    // App.launch(App.class, args);

  }
}
