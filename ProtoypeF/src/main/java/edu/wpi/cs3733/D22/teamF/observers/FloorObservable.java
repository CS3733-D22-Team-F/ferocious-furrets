package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * an observable representing state changes
 * to the equipment database
 */
public class FloorObservable {

    private PropertyChangeSupport observers;
    private final Connection connection = DatabaseManager.getConn();
    private List<equipment> equip;


    public FloorObservable(){
        observers = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        observers.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        observers.removePropertyChangeListener(listener);
    }

    private List<equipment> pullFloorData() throws SQLException {
        ArrayList<equipment> allEquip = DatabaseManager.getMedEquipDAO().getAllEquipment();
        return allEquip;
    }

    public void setState(List<equipment> newEquip){
        observers.firePropertyChange("equip", this.equip, newEquip);
        this.equip = newEquip;
    }
}
