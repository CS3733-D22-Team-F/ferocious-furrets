package edu.wpi.furious_furrets;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.furious_furrets.database.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

public class HandlerTester {
  DatabaseHandler handler = new DatabaseHandler();

  /** Check whether the handler can connect to the database */
  @Test
  public void testConnection() {
    assertNotNull(handler.connectDatabase());
  }

  /**
   * Checks whether after connected to the db the connection remains valid
   *
   * @throws SQLException
   */
  @Test
  public void checkValidConnections() throws SQLException {
    Connection conn = handler.connectDatabase();
    assertTrue(conn.isValid(100));
  }

  /**
   * Checks whether the db can open then close a connections
   *
   * @throws SQLException
   */
  @Test
  public void checkClose() throws SQLException {
    Connection conn = handler.connectDatabase();

    assertTrue(conn.isValid(100));

    conn.close();
    assertFalse(conn.isValid(100));
  }
}
