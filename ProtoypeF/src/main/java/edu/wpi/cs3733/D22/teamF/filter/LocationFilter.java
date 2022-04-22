package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationFilter implements IFilter {
  private String filterBy;

  public LocationFilter(String filterBy) {
    this.filterBy = filterBy;
  }

  public ArrayList<Equipment> filterLocType(List<Equipment> rawList) {
    ArrayList<Equipment> filteredList = new ArrayList<>();

    if (filterBy.equals("INUSE")) {}

    for (Equipment eq : rawList) {
      Location loc = Cache.getLocation(eq.getNodeID());

      if (loc.getNodeType().equals("PATI")) filteredList.add(eq);
      else if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith(filterBy))
        filteredList.add(eq);
      //    for (Equipment eq : rawList) {
      //      Location loc = Cache.getLocation(eq.getNodeID());
      //      if (filterBy.equals("PATI")) {
      //        filteredList.add(eq);
      //      } else {
      //        if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith(filterBy)) {
      //          filteredList.add(eq);
      //        } else {
      //          filteredList.add(eq);
      //        }
      //      }
      //    }

    }
    return filteredList;
  }

  public List<Equipment> filterInUse(
      List<Equipment> rawList,
      List<Equipment> list1,
      List<Equipment> list2,
      List<Equipment> list3) {

    List<Equipment> allOtherEquip = new ArrayList<>();
    allOtherEquip.addAll(list1);
    allOtherEquip.addAll(list2);
    allOtherEquip.addAll(list3);
    List<Equipment> inUseEquip = new ArrayList<>();
    for (Equipment eq : rawList) {
      if (!allOtherEquip.contains(eq)) {
        inUseEquip.add(eq);
      }
    }
    return inUseEquip;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    return null;
  }
}
