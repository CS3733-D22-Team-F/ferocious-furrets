package edu.wpi.cs3733.D22.teamF.observers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import edu.wpi.cs3733.D22.teamF.observers.Floor;


public class DashboardController implements Initializable{
    List<DashboardObserver> floorObservers = new ArrayList<>();
    Floor currentFloor;

    @FXML Label cBed;
    @FXML Label cXRay;
    @FXML Label cRecliner;
    @FXML Label cInfusionPump;

    @FXML Label dBed;
    @FXML Label dXRay;
    @FXML Label dRecliner;
    @FXML Label dInfusionPump;

    @FXML Label pBed;
    @FXML Label pXRay;
    @FXML Label pRecliner;
    @FXML Label pInfusionPump;

    @FXML Label iBed;
    @FXML Label iXRay;
    @FXML Label iRecliner;
    @FXML Label iInfusionPump;




    /**
     * Initializes the DashboardController to be used with the corresponding FXML
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FloorObservable allFloorData = new FloorObservable();

        DashboardObserver ll2Observer = new DashboardObserver(Floor.LL2);
        floorObservers.add(ll2Observer);
        DashboardObserver ll1Observer = new DashboardObserver(Floor.LL1);
        floorObservers.add(ll1Observer);
        DashboardObserver f1Observer = new DashboardObserver(Floor.FL1);
        floorObservers.add(f1Observer);
        DashboardObserver f2Observer = new DashboardObserver(Floor.FL2);
        floorObservers.add(f2Observer);
        DashboardObserver f3Observer = new DashboardObserver(Floor.FL3);
        floorObservers.add(f3Observer);
        DashboardObserver f4Observer = new DashboardObserver(Floor.FL4);
        floorObservers.add(f4Observer);
        DashboardObserver f5Observer = new DashboardObserver(Floor.FL5);
        floorObservers.add(f5Observer);


        for(DashboardObserver observer : floorObservers){
            allFloorData.addPropertyChangeListener(observer);
        }


    }

    /**
     * Increases the value of the current floor by 1
     */
    public void nextFloor(){
//        if(currentFloor != floorObservers.size() -1)
//        this.currentFloor += 1;
        currentFloor.next();
    }

    /**
     * Increases the value of the current floor by 1
     */
    public void prevFloor(){
//        if(currentFloor != floorObservers.size() -1)
//            this.currentFloor -= 1;
        currentFloor.prev();
    }


    public void setLabels()
    {
        DashboardObserver current = floorObservers.get(currentFloor);



//        cBed.setText();
//        cXRay.setText();
//        cRecliner.setText();
//        cInfusionPump.setText();
//
//        dBed.setText();
//        dXRay.setText();
//        dRecliner.setText();
//        dInfusionPump.setText();
//
//        pBed.setText();
//        pXRay.setText();
//        pRecliner.setText();
//        pInfusionPump.setText();
//
//        iBed.setText();
//        iXRay.setText();
//        iRecliner.setText();
//        iInfusionPump.setText();

    }
}
