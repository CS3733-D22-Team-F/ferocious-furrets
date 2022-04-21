package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

public class ReqInfo {
  private String type;
  private String status;
  private String nodeID;

  public ReqInfo(String type, String nodeID, String status) {
    this.type = type;
    this.status = status;
    this.nodeID = nodeID;
  }
}
