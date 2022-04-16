package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DashboardController implements Initializable {
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
   *
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

    for (DashboardObserver observer : floorObservers) {
      allFloorData.addPropertyChangeListener(observer);
    }
  }

  private int getCBedCount(DashboardObserver obs) {
    List<equipment> bedCount = obs.getCleanList();
    return bedCount.size();
  }

  /** Increases the value of the current floor by 1 */
  public void nextFloor() {
    currentFloor.next();
  }

  /** Increases the value of the current floor by 1 */
  public void prevFloor() {
    currentFloor.prev();
  }

  /**
   * Takes in the list of equipment on the floor and returns only Beds
   * @param equip
   * @return
   */
  private List<equipment> filterBeds(List<equipment> equip) {
    List<equipment> bedList = new ArrayList<>();
    for (equipment eq : equip) {
      if (eq.getEquipType().equals("Bed")) {
        bedList.add(eq);
      }
    }
    return bedList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only Infusion Pumps
   * @param equip
   * @return
   */
  private List<equipment> filterInfusionPump(List<equipment> equip) {
    List<equipment> pumpList = new ArrayList<>();
    for (equipment eq : equip) {
      if (eq.getEquipType().equals("Infusion Pump")) {
        pumpList.add(eq);
      }
    }
    return pumpList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only Recliners
   * @param equip
   * @return
   */
  private List<equipment> filterRecliner(List<equipment> equip) {
    List<equipment> reclinerList = new ArrayList<>();
    for (equipment eq : equip) {
      if (eq.getEquipType().equals("Recliner")) {
        reclinerList.add(eq);
      }
    }
    return reclinerList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only XRays
   * @param equip
   * @return
   */
  private List<equipment> filterXRay(List<equipment> equip) {
    List<equipment> xrayList = new ArrayList<>();
    for (equipment eq : equip) {
      if (eq.getEquipType().equals("Xray")) {
        xrayList.add(eq);
      }
    }
    return xrayList;
  }

  /**
   * takes all labels and applies
   * appropriate amount based current observed floor
   */
  public void setLabels() {
    DashboardObserver current = floorObservers.get(currentFloor.toInt());

    List<equipment> cleanEquip = current.getCleanList();
    List<equipment> dirtyEquip = current.getDirtyList();
    List<equipment> podEquip = current.getPodList();
    List<equipment> inUseEquip = current.getInUse();


    cBed.setText(String.valueOf(filterBeds(cleanEquip).size()));
    cXRay.setText(String.valueOf(filterXRay(cleanEquip).size()));
    cRecliner.setText(String.valueOf(filterRecliner(cleanEquip).size()));
    cInfusionPump.setText(String.valueOf(filterInfusionPump(cleanEquip).size()));

    dBed.setText(String.valueOf(filterBeds(dirtyEquip).size()));
    dXRay.setText(String.valueOf(filterXRay(dirtyEquip).size()));
    dRecliner.setText(String.valueOf(filterRecliner(dirtyEquip).size()));
    dInfusionPump.setText(String.valueOf(filterInfusionPump(dirtyEquip).size()));

    pBed.setText(String.valueOf(filterBeds(podEquip).size()));
    pXRay.setText(String.valueOf(filterXRay(podEquip).size()));
    pRecliner.setText(String.valueOf(filterRecliner(podEquip).size()));
    pInfusionPump.setText(String.valueOf(filterInfusionPump(podEquip).size()));

    iBed.setText(String.valueOf(filterBeds(inUseEquip).size()));
    iXRay.setText(String.valueOf(filterBeds(inUseEquip).size()));
    iRecliner.setText(String.valueOf(filterBeds(inUseEquip).size()));
    iInfusionPump.setText(String.valueOf(filterBeds(inUseEquip).size()));

  }
}
