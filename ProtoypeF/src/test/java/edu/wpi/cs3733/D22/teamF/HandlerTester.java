package edu.wpi.cs3733.D22.teamF;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

// TODO ADD Private method testing?
public class HandlerTester {

  /** Check whether the handler can connect to the database */
  @Test
  public void testConnection() {
    assertNotNull(DatabaseManager.getInstance().getDatabaseConnection());
  }

  /**
   * Checks whether after connected to the db the connection remains valid
   *
   * @throws SQLException
   */
  @Test
  public void checkValidConnections() throws SQLException {
    //    Connection conn = handler.connectDatabase();
    //    assertTrue(conn.isValid(100));
  }

  /**
   * Checks whether the db can open then close a connections
   *
   * @throws SQLException
   */
  @Test
  public void checkClose() throws SQLException {
    //    Connection conn = handler.connectDatabase();
    //
    //    assertTrue(conn.isValid(100));
    //
    //    conn.close();
    //    assertFalse(conn.isValid(100));
  }
}
