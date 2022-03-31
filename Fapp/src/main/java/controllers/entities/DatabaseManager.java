package controllers.entities;

import java.util.ArrayList;

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
