package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * An observer attach to each floor of the hospital & updates based on any change from the database
 */
public class DashboardObserver implements PropertyChangeListener {
  private List<equipment> listOfMedEquip = new ArrayList<>(); // list
  private Floor currFloor; // the floor the observer watches
  private List<String> floorAlerts = new ArrayList<>();

  private List<equipment> cleanList = new ArrayList<>();
  private List<equipment> dirtyList = new ArrayList<>();
  private List<equipment> podList = new ArrayList<>();
  private List<equipment> inUseList = new ArrayList<>();

  public List<equipment> getCleanList() {
    return cleanList;
  }

  public List<equipment> getDirtyList() {
    return dirtyList;
  }

  public List<equipment> getPodList() {
    return podList;
  }

  public List<equipment> getInUse() {
    return this.inUseList;
  }

  public DashboardObserver(Floor setFloor) {
    this.currFloor = setFloor;
  }

  /**
   * Filters all hospital equipment for the specfic floor of observer
   *
   * @param eqip
   */
  public void setFloorFilter(List<equipment> eqip) {

    for (equipment eq : eqip) {
      String equipFloor = eq.getNodeID().substring(8);
      if (equipFloor.equals(currFloor.toInt())) {
        listOfMedEquip.add(eq);
      }
    }

    cleanList = filterInClean();
    dirtyList = filterInDirty();
    podList = filterInPod();
    inUseList = filterInUse();
  }

  /**
   * receives any state changes in floor observable and applies approite filter
   *
   * @param evt the event of an observable changing
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("equip"))
      this.setFloorFilter((List<equipment>) evt.getNewValue());
  }

  /**
   * filters floor list for list of clean eqipment
   *
   * @return a list of clean equip for floor
   */
  private List<equipment> filterInClean() {

    List<equipment> cleanEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Clean")) {
        cleanEquip.add(eq);
      }
    }
    return cleanEquip;
  }

  /**
   * filters floor list for dirty equipment
   *
   * @return
   */
  private List<equipment> filterInDirty() {

    List<equipment> dirtyEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Dirty")) {
        dirtyEquip.add(eq);
      }
    }
    return dirtyEquip;
  }

  /**
   * filters floor list for equipment in pods
   *
   * @return a list of equipment in pods
   */
  private List<equipment> filterInPod() {

    List<equipment> podEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("PATI")) {
        podEquip.add(eq);
      }
    }
    return podEquip;
  }

  /**
   * filters floor list for equipment in use
   *
   * @return a list of in use equipment
   */
  private List<equipment> filterInUse() {

    List<equipment> allOtherEquip = new ArrayList<>();
    List<equipment> inUseEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("PATI")) {
        inUseEquip.add(eq);
      }
    }
    return inUseEquip;
  }

  /**
   * gets list of all alerts for a floor
   *
   * @return list of alerts
   */
  //TODO coubters for alerts
  public List<String> getAlerts() {

    return null;
  }
}
