package edu.wpi.cs3733.D22.teamF.observers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * An observer attach to each floor of the hospital &
 * updates based on any change from the database
 */
public class DashboardObserver implements PropertyChangeListener {
    private List<equipment> listOfMedEquip = new ArrayList<>(); // list
    private Floor currFloor; //the floor the observer watches
    private List<String> floorAlerts = new ArrayList<>();


    public DashboardObserver(Floor setFloor){
        this.currFloor = setFloor;
    }

    /**
     * Filters all hospital equipment for the specfic floor of observer
     * @param eqip
     * @return list of equip on floor
     */
    public List<equipment> setFloorFilter(List<equipment> eqip){
        listOfMedEquip.clear();
        for(equipment eq : eqip){
            String equipFloor = eq.getNodeID().substring(8);
            if (equipFloor.equals(enumToFloor(currFloor))) {
                listOfMedEquip.add(eq);

            }
        }
        return null;
    }

    /**
     * receives any state changes in floor observable and applies approite filter
     * @param evt the event of an observable changing
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("equip")){}
        //this.setFloorFilter((List<equipment>) evt.getNewValue());
    }

    /**
     * filters floor list for list of clean eqipment
     * @return a list of clean equip for floor
     */
    public List<equipment> getInClean(){

        List<equipment> cleanEquip = new ArrayList<>();
        //if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Clean"))
        return null;
    }

    /**
     * filters floor list for dirty equipment
     * @return
     */
    public List<equipment> getInDirty(){

        return null;

    }

    /**
     * filters floor list for equipment in pods
     * @return a list of equipment in pods
     */
    public List<equipment> getInPod(){

        return null;

    }

    /**
     * filters floor list for equipment in use
     * @return a list of in use equipment
     */
    public List<equipment> getInUse(){

        return null;

    }

    /**
     *  gets list of all alerts for a floor
     * @return list of alerts
     */
    public List<String> getAlerts(){

        return null;

    }

    /**
     * Converts a Floor enum value to a string apprite to its floor
     * @param f Floor enum to convert
     * @return a string equivalent
     */
    public String enumToFloor(Floor f){
        String retVal = "";
        switch(f){
            case LL2:
                retVal = "L2";
                break;
            case LL1:
                retVal = "L1";
                break;
            case FL1:
                retVal = "01";
                break;
            case FL2:
                retVal = "02";
                break;
            case FL3:
                retVal = "03";
                break;
            case FL4:
                retVal = "04";
                break;
            case FL5:
                retVal = "05";
                break;
            default:
                retVal = "Undefined";
        }
        return retVal;
    }
}
