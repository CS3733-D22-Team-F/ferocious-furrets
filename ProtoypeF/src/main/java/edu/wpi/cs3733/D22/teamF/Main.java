package edu.wpi.cs3733.D22.teamF;

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

    Fapp.launch(Fapp.class, args);
    // backup database to csv files

  }
}
