package edu.wpi.cs3733.D22.teamF.entities.medicalEquipment;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IEquipmentDAO {

  /**
   * Gets all MedEquip in the MEDEQUIP db and outputs them in an ArrayList
   *
   * @return ArrayList of type MedEquip
   * @throws SQLException
   */
  public ArrayList<Equipment> getAllEquipment() throws SQLException;

  /**
   * Taking user input for the ID of the new equipment node. A new Java MedEqup object is created
   * and the node is added to the SQL table.
   *
   * @throws SQLException
   * @param newEquipment
   */
  public void addEquipment(Equipment newEquipment) throws SQLException;

  /**
   * Taking user input for the ID of the equipment node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @throws SQLException
   * @param eID
   */
  public void deleteMedEquip(String eID) throws SQLException;

  /**
   * Taking user input for the ID of the equipment node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateEquipment(Equipment newEquipment) throws SQLException;

  /**
   * Saves the MedEquip table to a csv file
   *
   * @param file FILE FROM JAVAFXCHOOSER
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(File file) throws SQLException, IOException;
  /**
   * Saves the MedEquip table to a csv file
   *
   * @param fileDir is the name of the file the map will be backed up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException;
}
