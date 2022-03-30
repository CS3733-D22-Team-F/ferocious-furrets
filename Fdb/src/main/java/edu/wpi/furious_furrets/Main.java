package edu.wpi.furious_furrets;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Main {

  public static void main(String[] args) throws SQLException, NoSuchElementException {

    //    LocationsDAOImpl ldao = new LocationsDAOImpl();
    //    ldao.testConnection();

    MedEquipServReqDAOImpl mdao = new MedEquipServReqDAOImpl();
    mdao.connectDatabase();

    // App.launch(App.class, args);

  }
}
