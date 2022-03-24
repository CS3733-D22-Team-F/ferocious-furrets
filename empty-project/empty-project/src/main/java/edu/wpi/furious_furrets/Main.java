package edu.wpi.furious_furrets;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {

    LocationsDAO ldao = new LocationsDAO();
    ldao.testConnection();
    // App.launch(App.class, args);
  }
}
