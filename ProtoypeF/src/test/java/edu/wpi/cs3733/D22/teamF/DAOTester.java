package edu.wpi.cs3733.D22.teamF;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipmentDAOImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DAOTester {
  Connection conn = DatabaseManager.getConn();

  /**
   * Checks that the DAO can get data in the form of an Array List from the DB
   *
   * @throws SQLException
   * @throws IOException
   */
  @Test
  public void testGetData() throws SQLException, IOException {
    LocationsDAOImpl dao = new LocationsDAOImpl();
    dao.initTable("/edu/wpi/cs3733/D22/teamF/csv/TowerLocationsTest.csv");
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
    equipmentDAOImpl dao = new equipmentDAOImpl();
    dao.initTable("/edu/wpi/cs3733/D22/teamF/csv/MedEquipTest.csv");
    assertNotNull(dao.getAllEquipment());
    //    System.out.println(dao.getAllRequests().get(2).getLongName());
  }
}
