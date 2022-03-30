package edu.wpi.furious_furrets;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDAO {
  public ArrayList<Location> getAllLocations() throws SQLException;

  public void updateLocation() throws SQLException;

  public void addLocation() throws SQLException;

  public void deleteLocation() throws SQLException;
}
