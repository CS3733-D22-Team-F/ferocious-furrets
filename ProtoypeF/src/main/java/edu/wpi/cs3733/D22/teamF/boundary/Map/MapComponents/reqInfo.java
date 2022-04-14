package edu.wpi.cs3733.D22.teamF.boundary.Map.MapComponents;

public class reqInfo {
  private String type;
  private String status;
  private String nodeID;

  public reqInfo(String type, String nodeID, String status) {
    this.type = type;
    this.status = status;
    this.nodeID = nodeID;
  }
}
