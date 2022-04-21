package edu.wpi.cs3733.D22.teamF;

/**
 * Equipment request object
 *
 * @see RequestObject
 */
public class EquipmentObject extends RequestObject {

  private String equipID;

  private enum equipReqType {};

  /**
   * Constructor
   *
   * @param employee id
   * @param nodeID
   * @param equipID
   */
  public EquipmentObject(String employee, String nodeID, String equipID) {
    super(employee, nodeID);
    this.equipID = equipID;
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
   * sets equip ID
   *
   * @param equipID String
   */
  public void setEquipID(String equipID) {
    this.equipID = equipID;
  }
}
