package edu.wpi.furious_furrets;

import java.util.ArrayList;

public interface LocationDAO {
  public ArrayList<Location> getAllLocations();

  public void addLocation();

  public void deleteLocation();

  public void updateLocation();
}
