package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import edu.wpi.cs3733.D22.teamF.observers.Floor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FloorFilter implements IFilter {
  Floor floorToFilter;

  public FloorFilter(Floor filterBy) {
    this.floorToFilter = filterBy;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    return null;
  }

  public ArrayList<Equipment> apply(List<Equipment> rawFloors) {
    ArrayList<Equipment> filtered = new ArrayList<>();
    for (Equipment eq : rawFloors) {
      String equipFloor = eq.getNodeID().substring(8);
      //      System.out.println(
      //          currFloor.toFloorString() + " is currFloor." + equipFloor + " is equipFloor");

      if (equipFloor.equals(floorToFilter.toFloorString())) {
        //        System.out.println(
        //            currFloor.toFloorString() + "Adding to medEquipList, size:  " +
        // listOfMedEquip.size());
        filtered.add(eq);
      }
    }
    return filtered;
  }
}
