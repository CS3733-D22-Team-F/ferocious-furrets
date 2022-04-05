package edu.wpi.furious_furrets.controllers.entities;

import edu.wpi.furious_furrets.database.DatabaseInitializer;
import edu.wpi.furious_furrets.database.LocationsDAOImpl;
import edu.wpi.furious_furrets.database.MedDelReqDAOImpl;
import edu.wpi.furious_furrets.database.labReqDAOImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

  private static final Connection conn = DatabaseInitializer.getConnection().dbConnection;
  private static final LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
  private static final MedDelReqDAOImpl mdao = new MedDelReqDAOImpl(conn);
  private static final labReqDAOImpl lrdao = new labReqDAOImpl(conn);

  private static DatabaseManager DatabaseManager;

  private DatabaseManager() {}

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

  public static labReqDAOImpl getlrdao() {
    return lrdao;
  }

  private static class Helper {
    private static final DatabaseManager dbMan = new DatabaseManager();
  }

  private static class SingletonHelper {
    private static final DatabaseManager DatabaseManager = new DatabaseManager();
  }
}
