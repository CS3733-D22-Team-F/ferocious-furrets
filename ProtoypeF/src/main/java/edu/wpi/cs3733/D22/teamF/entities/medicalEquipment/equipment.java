package edu.wpi.cs3733.D22.teamF.entities.medicalEquipment;

/** Equipment object */
public class equipment {

  private String equipID;
  private String equipType;
  private String nodeID;
  private String status;

  private enum equipType {};

  /**
   * Constructor
   *
   * @param equipID
   * @param nodeID
   * @param equipID
   */
  public equipment(String equipID, String equipType, String nodeID, String status) {
    this.equipID = equipID;
    this.equipType = equipType;
    this.nodeID = nodeID;
    this.status = status;
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

  public String getStatus() {
    return this.status;
  };

  /**
   * Generates SQL insert statement to insert an equipment object into the table
   *
   * @return String
   */
  public String generateInsertStatement() {
    return String.format(
        "INSERT INTO MEDICALEQUIPMENT VALUES ('%s','%s','%s','%s')",
        equipID, equipType, nodeID, status);
  }
}
