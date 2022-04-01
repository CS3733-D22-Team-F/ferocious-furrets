package edu.wpi.furious_furrets.controllers.entities;

import edu.wpi.furious_furrets.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

  private static Connection conn = DatabaseInitializer.getConnection().dbConnection;
  private static LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
  private static MedDelReqDAOImpl mdao = new MedDelReqDAOImpl(conn);

  private static DatabaseManager DatabaseManager;

  private DatabaseManager() {}

  private static class Helper {
    private static final DatabaseManager dbMan = new DatabaseManager();
  }

  private static class SingletonHelper {
    private static final DatabaseManager DatabaseManager = new DatabaseManager();
  }

  public static DatabaseManager initalizeDatabaseManager() throws SQLException, IOException {
    ldao.initTable();
    mdao.initTable();
    return Helper.dbMan;
  }

  public static Connection getConn() {
    return conn;
  }

  public static LocationsDAOImpl getLdao() {
    return ldao;
  }

  public static MedDelReqDAOImpl getMdao() {
    return mdao;
  }
}
