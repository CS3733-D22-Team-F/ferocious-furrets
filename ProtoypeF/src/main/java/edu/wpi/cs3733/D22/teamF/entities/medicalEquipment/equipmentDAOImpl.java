package edu.wpi.cs3733.D22.teamF.entities.medicalEquipment;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class equipmentDAOImpl implements equipmentDAO {

  /** Constructor */
  public equipmentDAOImpl() {}

  /**
   * Method that initalizes all the tables for SQL and makes objects and adds them to the arrayList
   *
   * @param Filepath STRING WHERE THE CSV IS IN RESOURCES
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(String Filepath) throws SQLException, IOException {
    ArrayList<equipment> equipmentList = new ArrayList<>();
    DatabaseManager.dropTableIfExist("MedicalEquipment");
    DatabaseManager.runStatement(
        "CREATE TABLE MedicalEquipment (equipID varchar(16) PRIMARY KEY, equipType varchar(16), nodeID varchar(16), status varchar(16))");

    List<String> lines = CSVReader.readResourceFilepath(Filepath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      equipment addedEquipment = makeObjectFromString(currentLine);
      equipmentList.add(addedEquipment);
    }

    for (equipment currentEquipment : equipmentList) {
      DatabaseManager.runStatement(currentEquipment.generateInsertStatement());
    }
  }
  /**
   * Method that initalizes all the tables for SQL and makes objects and adds them to the arrayList
   *
   * @param file file of chose file (FROM JAVA FX CHOOSE)
   * @throws SQLException
   * @throws IOException
   */
  public void initTable(File file) throws SQLException, IOException {
    ArrayList<equipment> equipmentList = new ArrayList<>();
    DatabaseManager.dropTableIfExist("MedicalEquipment");
    DatabaseManager.runStatement(
        "CREATE TABLE MedicalEquipment (equipID varchar(16) PRIMARY KEY, equipType varchar(16), nodeID varchar(16), status varchar(16))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      equipment addedEquipment = makeObjectFromString(currentLine);
      equipmentList.add(addedEquipment);
    }

    for (equipment currentEquipment : equipmentList) {
      DatabaseManager.runStatement(currentEquipment.generateInsertStatement());
    }
  }

  /**
   * Saves the MedEquip table to a csv file
   *
   * @param fileDir is the name of the file the map will be backed up to
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ArrayList<equipment> equipment = getAllEquipment();

    toAdd.add("equipID,equipType,nodeID,status");
    for (equipment l : equipment) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s", l.getEquipID(), l.getEquipType(), l.getNodeID(), l.getStatus()));
    }

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }
  /**
   * Saves the MedEquip table to a csv file
   *
   * @param file FILE FROM JAVAFXCHOOSER
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ArrayList<equipment> equipment = getAllEquipment();

    toAdd.add("equipID,equipType,nodeID,status");
    for (equipment l : equipment) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s", l.getNodeID(), l.getEquipType(), l.getNodeID(), l.getStatus()));
    }

    CSVWriter.writeAll(file, toAdd);
  }

  /**
   * Make an location object from String (with commas) (Helper function)
   *
   * @param currentLine Line in CSV to take in as parameters for an object
   * @return return a Location object
   */
  public equipment makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");
    String equipID = currentLineSplit[0];
    String equipType = currentLineSplit[1];
    String nodeID = currentLineSplit[2];
    String status = currentLineSplit[3];
    return new equipment(equipID, equipType, nodeID, status);
  }

  /**
   * Gets all MedEquip in the MEDEQUIP db and outputs them in an ArrayList
   *
   * @return ArrayList of type MedEquip
   * @throws SQLException
   */
  public ArrayList<equipment> getAllEquipment() throws SQLException {
    return MedEquipFromRSET(DatabaseManager.runQuery("SELECT * FROM MEDICALEQUIPMENT"));
  }

  /**
   * Taking user input for the ID of the new equipment node. A new Java MedEqup object is created
   * and the node is added to the SQL table.
   *
   * @param newEquipment
   * @throws SQLException
   */
  public void addEquipment(equipment newEquipment) throws SQLException {
    DatabaseManager.runStatement(newEquipment.generateInsertStatement());
  }

  /**
   * Taking user input for the ID of the equipment node. The node is removed from the SQL table, and
   * the corresponding Java object is deleted.
   *
   * @param eID
   * @throws SQLException
   */
  public void deleteMedEquip(String eID) throws SQLException {
    DatabaseManager.runStatement(
        String.format("DELETE FROM MedicalEquipment WHERE equipID = '%s'", eID));
  }

  /**
   * Taking user input for the ID of the equipment node and the new values of the floor and location
   * type. The Location is then updated in the Locations DB
   *
   * @throws SQLException
   */
  public void updateEquipment(equipment updatedMedEqip) throws SQLException {
    DatabaseManager.runStatement(
        String.format(
            "UPDATE FROM MedicalEquipment SET equipType = '%s', nodeID = '%s', status = '%s' WHERE equipID = '%s'",
            updatedMedEqip.getEquipType(),
            updatedMedEqip.getNodeID(),
            updatedMedEqip.getStatus(),
            updatedMedEqip.getEquipID()));
  }

  /**
   * Taking in a ResultSet object take the locations in the form of new Location object
   *
   * @param rset ResultSet object to get locations from
   * @throws SQLException
   */
  public ArrayList<equipment> MedEquipFromRSET(ResultSet rset) throws SQLException {
    ArrayList<equipment> allEquipment = new ArrayList<>();
    while (rset.next()) {
      String equipID = rset.getString("equipID");
      String equipType = rset.getString("equipType");
      String nodeID = rset.getString("nodeID");
      String status = rset.getString("status");
      equipment newE = new equipment(equipID, equipType, nodeID, status);
      allEquipment.add(newE);
    }
    rset.close();
    return allEquipment;
  }
}
