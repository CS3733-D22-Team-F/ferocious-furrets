package edu.wpi.cs3733.D22.teamF.entities.location;

/**
 * Interface for the LocationDAO class to handle the location DB necessary function
 *
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
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
   * Taking user input for the ID of the new location node. A new Java Location object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   * @param newLocation
   */
  public void addLocation(Location newLocation) throws SQLException;
  /**
   * Taking user input for the ID of the location node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   * @param nID
   */
  public void deleteLocation(String nID) throws SQLException;
  /**
   * Taking user input for the ID of the location node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateLocation(String oldNodeID, Location updatedLocation) throws SQLException;
  /**
   * Saves the Locations table to a csv file
   *
   * @param fileDir is the directory of the file the map will be saved up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException;
  /**
   * Saves the Locations table to a csv file
   *
   * @param filename is the file filetype of the file the map will be saved up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(File filename) throws SQLException, IOException;
}
