package edu.wpi.furious_furrets.entities.medicalEquipment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MedEquipDAO {

  /**
   * Gets all MedEquip in the MEDEQUIP db and outputs them in an ArrayList
   *
   * @return ArrayList of type MedEquip
   * @throws SQLException
   */
  public ArrayList<MedEquip> getAllEquipment() throws SQLException;

  /**
   * Taking user input for the ID of the equipment node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateEquipment() throws SQLException;

  /**
   * Taking user input for the ID of the new equipment node. A new Java MedEqup object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   * @param newMedEquip
   */
  public void addEquipment(MedEquip newMedEquip) throws SQLException;

  /**
   * Taking user input for the ID of the equipment node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   * @param eID
   */
  public void deleteMedEquip(String eID) throws SQLException;

  /**
   * Taking User input for the name of a CSV file. The program first loads all of the contents of
   * the SQL MEDEQUIP table into Java MedEquip objects. Then the CSV file is created from the Java
   * objects.
   *
   * @return
   */
  public void saveMedEquipToCSV();
}
