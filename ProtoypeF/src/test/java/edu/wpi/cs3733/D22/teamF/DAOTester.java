package edu.wpi.cs3733.D22.teamF;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.ConnType;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DAOTester {

  /**
   * Checks that the DAO can get data in the form of an Array List from the DB
   *
   * @throws SQLException
   * @throws IOException
   */
  @Test
  public void testGetData() throws SQLException, IOException {
    DatabaseManager.getInstance().switchConnection(ConnType.EMBEDDED);
    Connection conn = DatabaseManager.getInstance().getDatabaseConnection();
    assertTrue(conn.isValid(100));
    LocationsDAOImpl dao = DatabaseManager.getInstance().getLocationDAO();
    dao.initTable("/edu/wpi/cs3733/D22/teamF/csv/TowerLocationsTest.csv");
    assertNotNull(dao.getAllLocationsFromDB());
    System.out.println(dao.getAllLocationsFromDB().get(2).getLongName());
  }


}
