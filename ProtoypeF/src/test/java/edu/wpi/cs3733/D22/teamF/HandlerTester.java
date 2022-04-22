package edu.wpi.cs3733.D22.teamF;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.ConnType;
import java.io.IOException;
import java.sql.Connection;
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
  public void checkValidConnections() throws SQLException, IOException {
    DatabaseManager.getInstance().switchConnection(ConnType.EMBEDDED);
    Connection conn = DatabaseManager.getInstance().getDatabaseConnection();
    assertTrue(conn.isValid(100));
  }

  /**
   * Checks whether the db can open then close a connections
   *
   * @throws SQLException
   */
  @Test
  public void checkClose() throws SQLException, IOException {
    DatabaseManager.getInstance().switchConnection(ConnType.EMBEDDED);
    Connection conn = DatabaseManager.getInstance().getDatabaseConnection();

    assertTrue(conn.isValid(100));

    conn.close();
    assertFalse(conn.isValid(100));
  }
}
