package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EquipmentFilter implements IFilter {
  private String equipToFilter = "";
  private String filterForLoc = "";
  private List<Equipment> rawList = new ArrayList<>();

  public EquipmentFilter(){}

  public void setFiltration(String filterByEquip, String filterForLoc) {
    this.equipToFilter = filterByEquip;
    this.filterForLoc = filterForLoc;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    return null;
  }

  public List<Equipment> apply(List<Equipment> rawFloors) {
    List<Equipment> filterEquip = new ArrayList<>();

    for (Equipment eq : rawFloors) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Clean")) {
        filterEquip.add(eq);
      }
    }

    return filterEquip;
  }
}
