package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EquipmentFilter implements IFilter {
  private String equipToFilter = "";

  public EquipmentFilter(String equipToFilter) {
    this.equipToFilter = equipToFilter;
  }

  @Override
  public ArrayList<RequestTree> apply(ResultSet rSet) {
    return null;
  }

  public List<Equipment> apply(List<Equipment> rawFloors) {
    List<Equipment> filterEquip = new ArrayList<>();
    for (Equipment eq : rawFloors) {
      if (eq.getEquipType().equals(equipToFilter)) {
        filterEquip.add(eq);
      }
    }
    return filterEquip;
  }
}
