package edu.wpi.furious_furrets;

import java.util.ArrayList;

public interface MedEquipServReqDAO {
  public ArrayList<MedEquipServReq> getAllRequests();

  public void addRequest();

  public void deleteRequest();

  public void updateRequest();
}
