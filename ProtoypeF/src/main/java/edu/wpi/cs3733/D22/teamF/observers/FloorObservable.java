package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** an observable representing state changes to the equipment database */
public class FloorObservable {

  private PropertyChangeSupport observers;
  private final Connection connection = DatabaseManager.getConn();
  private List<equipment> equip;

  private static FloorObservable m_floorObservable = new FloorObservable();

  private FloorObservable() {
    observers = new PropertyChangeSupport(this);
  }

  public static FloorObservable getInstance() {
    return m_floorObservable;
  }

  /**
   * adds observers to watch FloorsObservable
   *
   * @param listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    observers.addPropertyChangeListener(listener);
  }

  /**
   * removes observers that are watching FloorsObservable
   *
   * @param listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    observers.removePropertyChangeListener(listener);
  }

  /**
   * gets a list of all equip from database
   *
   * @return a unfilter list of all equip
   * @throws SQLException
   */
  private List<equipment> pullFloorData() throws SQLException {
    ArrayList<equipment> allEquip = DatabaseManager.getMedEquipDAO().getAllEquipment();
    return allEquip;
  }

  /** When list of equip is changed sends an event to all listeners */
  public void setState() throws SQLException {
    List<equipment> newEquip = pullFloorData();
    observers.firePropertyChange("equip", this.equip, newEquip);
    this.equip = newEquip;
    System.out.println("Fired event from FloorObservable equip size: " + equip.size());
  }
}
