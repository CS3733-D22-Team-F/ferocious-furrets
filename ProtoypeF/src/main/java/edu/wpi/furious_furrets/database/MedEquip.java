package edu.wpi.furious_furrets.database;

/** Equipment object */
public class MedEquip {

  private String equipID;
  private String equipType;
  private String nodeID;

  private enum equipType {};

  /**
   * Constructor
   *
   * @param equipID
   * @param nodeID
   * @param equipID
   */
  public MedEquip(String equipID, String equipType, String nodeID) {
    this.equipID = equipID;
    this.equipType = equipType;
    this.nodeID = nodeID;
  }

  /**
   * get Equipment id
   *
   * @return String
   */
  public String getEquipID() {
    return equipID;
  }

  /**
   * Gets Equipment Type
   *
   * @return String
   */
  public String getEquipType() {
    return equipType;
  }

  /**
   * Gets nodeID
   *
   * @return String
   */
  public String getNodeID() {
    return nodeID;
  }

  /**
   * Generates SQL insert statement to insert an equipment object into the table
   *
   * @return String
   */
  public String generateInsertStatement() {
    return "INSERT INTO MEDEQUIP VALUES ('"
        + this.equipID
        + "', '"
        + this.equipType
        + "', '"
        + this.nodeID
        + "' )";
  }
}
