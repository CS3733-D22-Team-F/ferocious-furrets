package edu.wpi.furious_furrets.controllers.entities;

import java.util.ArrayList;

/**
 * Abstract for the DatabaseManager
 * @version 1.0
 */
public abstract class DatabaseManager {

  public DatabaseManager() {}

  /** Update specfic database attached to instance of the database */
  private void updateDataBase() {}

  /**
   * Gets every entry in the database
   *
   * @return ArrayList of all rows of the database
   */
  public ArrayList<String> getAllEntries() {
    // decide on the return type in the list
    return null;
  }

  /** Adds the passed entry to the database */
  public void addEntry() {}

  /** deletes the passed entry from the database */
  public void deleteEntry() {}
}
