package edu.wpi.furious_furrets;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.furious_furrets.database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

public class DAOTester {
  DatabaseHandler handler = new DatabaseHandler();
  Connection conn = handler.connectDatabase();

  /**
   * Checks that the DAO can get data in the form of an Array List from the DB
   *
   * @throws SQLException
   * @throws IOException
   */
  @Test
  public void testGetData() throws SQLException, IOException {
    LocationsDAOImpl dao = new LocationsDAOImpl(conn);
    dao.initTable();
    assertNotNull(dao.getAllLocations());
    System.out.println(dao.getAllLocations().get(2).getLongName());
  }

  /**
   * Checks that the DAO can get data in the form of an Array List from the DB
   *
   * @throws SQLException
   * @throws IOException
   */
  @Test
  public void testGetDataMedical() throws SQLException, IOException {
    MedDelReqDAOImpl dao = new MedDelReqDAOImpl(conn);
    dao.initTable();
    assertNotNull(dao.getAllRequests());
    //    System.out.println(dao.getAllRequests().get(2).getLongName());
  }
}
