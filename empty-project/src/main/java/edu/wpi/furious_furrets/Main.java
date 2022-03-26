package edu.wpi.furious_furrets;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Main {

  public static void main(String[] args) throws SQLException, NoSuchElementException {

    LocationsDAO ldao = new LocationsDAO();
    ldao.testConnection();
    // App.launch(App.class, args);
  }
}
