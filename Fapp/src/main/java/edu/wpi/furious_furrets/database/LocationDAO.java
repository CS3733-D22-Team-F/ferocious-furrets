package edu.wpi.furious_furrets.database;

/**
 * Interface for the LocationDAO class to handle the location DB necessary function
 *
 * @version 1.0
 */
import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDAO {

  /**
   * Gets all Locations in the location db and outputs them in an ArrayList
   *
   * @return ArrayList of type Location
   * @throws SQLException
   */
  public ArrayList<Location> getAllLocations() throws SQLException;

  /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateLocation() throws SQLException;

  /**
   * Taking user input for the ID of the new location node. A new Java Location object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   */
  public void addLocation() throws SQLException;

  /**
   * Taking user input for the ID of the location node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   */
  public void deleteLocation() throws SQLException;

  /**
   * Taking User input for the name of a CSV file. The program first loads all of the contents of
   * the SQL Location table into Java Location objects. Then the CSV file is created from the Java
   * objects.
   */
  public void saveLocation();
}
