package edu.wpi.teamname;

public class equipmentObject extends requestObject {

  private String equipID;

  private enum equipReqType {};

  public equipmentObject(String employee, String nodeID, String equipID) {
    super(employee, nodeID);
    this.equipID = equipID;
  }

  public String getEquipID() {
    return equipID;
  }

  public void setEquipID(String equipID) {
    this.equipID = equipID;
  }
}
