package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EquipmentFilter implements IFilter {
  private String equipToFilter = "";
  private String filterForLoc = "";

  public EquipmentFilter(String filterBy, String filterFor) {
    this.equipToFilter = filterBy;
    this.filterForLoc = filterFor;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    return null;
  }

  public ArrayList<Equipment> apply(List<Equipment> rawFloors) {
    return null;
  }
}
