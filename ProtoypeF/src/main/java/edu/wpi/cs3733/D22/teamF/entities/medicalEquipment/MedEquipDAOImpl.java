package edu.wpi.cs3733.D22.teamF.entities.medicalEquipment;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

public class MedEquipDAOImpl implements MedEquipDAO {

  private ArrayList<MedEquip> csvMedEquip = new ArrayList<MedEquip>();
  private ArrayList<MedEquip> updatedMedEquip = new ArrayList<MedEquip>();
  private ArrayList<String> csvIDS = new ArrayList<String>();

  // FXML ojects not added
  //

  /** Constructor */
  public MedEquipDAOImpl() {}

  public void initTable() throws SQLException, IOException {
    csvIDS.clear();
    csvMedEquip.clear();
    updatedMedEquip.clear();
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                MedEquipDAOImpl.class.getResourceAsStream(
                    "/edu/wpi/cs3733/D22/teamF/csv/MedEquip.csv"),
                StandardCharsets.UTF_8));
    String lineText;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String meID = data[0];
      String meType = data[1];
      String meNodeID = data[2];
      String stat = data[3];
      MedEquip m = new MedEquip(meID, meType, meNodeID, stat);
      csvMedEquip.add(m);
      csvIDS.add(m.getNodeID());
    }
    Statement stm = DatabaseManager.getConn().createStatement();
    DatabaseMetaData databaseMetadata = DatabaseManager.getConn().getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "MEDICALEQUIPMENT", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE MedicalEquipment");
    }
    stm.execute(
        "CREATE TABLE MedicalEquipment (equipID varchar(16) PRIMARY KEY, equipType varchar(16), nodeID varchar(16), status varchar(16))");

    for (MedEquip currentMedEquip : csvMedEquip) {
      stm.execute(currentMedEquip.generateInsertStatement());
    }

    stm.close();
  }

  public String getAvailEquipment(String type) throws SQLException {
    String eID = "";
    Statement stm = DatabaseManager.getConn().createStatement();
    ResultSet allAvailableEquipment =
        stm.executeQuery(
            "Select equipID from MEDICALEQUIPMENT WHERE equipType = '"
                + type
                + "' AND status = 'available'");
    if (allAvailableEquipment.next()) {
      eID = allAvailableEquipment.getString("equipID");
    }
    stm.execute("UPDATE MEDICALEQUIPMENT SET status = 'unavailable' WHERE EQUIPID = '" + eID + "'");
    return eID;
  }

  /**
   * Backs up the SQL Table of MedEquip to a user-specified CSV file
   *
   * @param filename
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSV(String filename) throws SQLException, IOException {

    String csvName = "/edu/wpi/cs3733/D22/teamF/MedEquipBackedUp.csv";

    Statement stm = null;
    try {
      stm = DatabaseManager.getConn().createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // for (String id : csvIDS) {
      ResultSet rset;
      rset = stm.executeQuery("SELECT * FROM MedicalEquipment");

      ArrayList<MedEquip> allMedEquip = medEquipFromRSET(rset);

      rset.close();
      File newCSV = new File(filename);
      FileWriter fw = new FileWriter(filename);
      fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
      for (MedEquip m : allMedEquip) {
        fw.write(m.getEquipID() + "," + m.getEquipType() + "," + m.getNodeID() + "\n");
      }
      fw.close();
      // }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reloads all MedEquip objects from the given file name
   *
   * @param filename
   * @throws IOException
   * @throws SQLException
   */
  public void resetEquipTableFromCSV(String filename) throws IOException, SQLException {
    csvMedEquip.clear();
    csvIDS.clear();
    csvMedEquip.clear();
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                MedEquipDAOImpl.class.getResourceAsStream(filename), StandardCharsets.UTF_8));
    String lineText;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String meID = data[0];
      String meType = data[1];
      String meNodeID = data[2];
      String stat = data[3];
      MedEquip m = new MedEquip(meID, meType, meNodeID, stat);
      csvMedEquip.add(m);
      csvIDS.add(m.getNodeID());
    }
    Statement stm = DatabaseManager.getConn().createStatement();
    DatabaseMetaData databaseMetadata = DatabaseManager.getConn().getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "MedicalEquipment", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE MedicalEquipment");
    }
    stm.execute(
        "CREATE TABLE MedicalEquipment (equipID varchar(16) PRIMARY KEY, equipType varchar(16), nodeID varchar(16))");

    for (MedEquip currentMedEquip : csvMedEquip) {
      stm.execute(currentMedEquip.generateInsertStatement());
    }

    stm.close();
  }

  /**
   * Taking in a ResultSet object take the medEquip in the form of new MedEquip objects
   *
   * @param rset ResultSet object to get medEquip from
   * @throws SQLException
   */
  public ArrayList<MedEquip> medEquipFromRSET(ResultSet rset) throws SQLException {
    ArrayList<MedEquip> allMedEquip = new ArrayList<MedEquip>();
    while (rset.next()) {
      String equipID = rset.getString("equipID");
      String equipType = rset.getString("equipType");
      String nodeID = rset.getString("nodeID");
      String stat = rset.getString("status");
      MedEquip newME = new MedEquip(equipID, equipType, nodeID, stat);
      allMedEquip.add(newME);
    }
    return allMedEquip;
  }

  /**
   * Returns a list of all MedEquip
   *
   * @return ArrayList<MedEquip>
   * @throws SQLException
   */
  public ArrayList<MedEquip> getAllEquipment() throws SQLException {
    Statement stm = DatabaseManager.getConn().createStatement();
    String q = "SELECT * FROM MedicalEquipment";
    ResultSet rset = stm.executeQuery(q);
    ArrayList<MedEquip> allMedEquip = medEquipFromRSET(rset);
    rset.close();
    stm.close();
    return allMedEquip;
  }

  /**
   * Takes a user input to update the fields of a MedEquip
   *
   * @throws SQLException
   */
  public void updateEquipment() throws SQLException {
    String oldEquipID = null;
    String oldNodeID = null;
    String oldEquipType = null;
    String newEquipID = null;
    String newEquipType = null;
    String newNodeID = null;

    String updatedID = "";
    Statement stm = DatabaseManager.getConn().createStatement();
    String cmd = "UPDATE MedicalEquipment SET nodeID = '" + newNodeID + "'";
    stm.execute(cmd);
    stm.close();
  }

  /**
   * Taking user input for the fields of a new MedEquip and adds it to the database of MedEquip
   * objects
   *
   * @param newMedEquip
   * @throws SQLException
   */
  public void addEquipment(MedEquip newMedEquip) throws SQLException {

    Statement stm = DatabaseManager.getConn().createStatement();
    String cmd = newMedEquip.generateInsertStatement();
    stm.execute(cmd);
    stm.close();
  }

  /**
   * Deletes a MedEquip from the SQL table
   *
   * @param eID
   * @throws SQLException
   */
  public void deleteMedEquip(String eID) throws SQLException {

    Statement stm = DatabaseManager.getConn().createStatement();
    String q = "Delete from MedicalEquipment where nodeID = '" + eID + "'";
    stm.execute(q);
    stm.close();
  }

  /** Saves the MedEquip objects to the embedded MedEquip.csv */
  public void saveMedEquipToCSV() {
    String csvName = "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/MedEquip.csv";

    Statement stm = null;
    try {
      stm = DatabaseManager.getConn().createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {

      ResultSet rset;
      rset = stm.executeQuery("SELECT * FROM MedicalEquipment");

      ArrayList<MedEquip> allMedEquip = this.getAllEquipment();

      rset.close();
      File newCSV = new File(csvName);
      FileWriter fw = new FileWriter(csvName);
      fw.write("equipID,equipType,nodeID,status\n");
      for (MedEquip m : allMedEquip) {
        fw.write(
            m.getEquipID()
                + ","
                + m.getEquipType()
                + ","
                + m.getNodeID()
                + ","
                + m.getStatus()
                + "\n");
      }
      fw.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
