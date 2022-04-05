package edu.wpi.cs3733.D22.teamF;

/**
 * Equipment request object
 *
 * @see requestObject
 */
public class equipmentObject extends requestObject {

  private String equipID;

  private enum equipReqType {};

  /**
   * Constructor
   *
   * @param employee id
   * @param nodeID
   * @param equipID
   */
  public equipmentObject(String employee, String nodeID, String equipID) {
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
