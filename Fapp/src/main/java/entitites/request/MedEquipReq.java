package entitites.request;

import entitites.Request;

public abstract class MedEquipReq extends Request {
  private String equipID;
  private equipReqType equipmentType;

  // TODO Sample as ....
  public enum equipReqType {
    CATSCAN,
    XRAY,
    MRI,
    URINE,
    BLOOD
  }

  public MedEquipReq(String id, equipReqType type) {
    this.equipID = id;
    this.equipmentType = type;
  }
}
