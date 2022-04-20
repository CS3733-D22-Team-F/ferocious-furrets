package edu.wpi.cs3733.D22.teamF.observers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DashboardController implements Initializable {
  List<Label> cleanLabels = new ArrayList<>();
  List<Label> dirtyLabels = new ArrayList<>();
  List<Label> podLabels = new ArrayList<>();
  List<Label> inUseLabels = new ArrayList<>();

  static Floor currentFloor = Floor.FL3;

  @FXML ComboBox layoutAlerts;
  @FXML TextField floorSelect;

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

    FloorObservable.getInstance();

    /*
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

    // made into alert just for ease of test
    DashboardObserver f5Observer = new DashboardObserver(Floor.FL5);
    f5Observer.addPropertyChangeListener(AlertObserver.getInstance());
    floorObservers.add(f5Observer);
     */

    cleanLabels.add(cBed);
    cleanLabels.add(cInfusionPump);
    cleanLabels.add(cRecliner);
    cleanLabels.add(cXRay);

    dirtyLabels.add(dBed);
    dirtyLabels.add(dInfusionPump);
    dirtyLabels.add(dRecliner);
    dirtyLabels.add(dXRay);

    podLabels.add(pBed);
    podLabels.add(pInfusionPump);
    podLabels.add(pRecliner);
    podLabels.add(pXRay);

    inUseLabels.add(iBed);
    inUseLabels.add(iInfusionPump);
    inUseLabels.add(iRecliner);
    inUseLabels.add(iXRay);

    /*
    for (DashboardObserver observer : floorObservers) {
      observer.setLabels(cleanLabels, dirtyLabels, podLabels, inUseLabels);
      FloorObservable.getInstance().addPropertyChangeListener(observer);
    }

    try {
      FloorObservable.getInstance().setState();
    } catch (SQLException e) {
      e.printStackTrace();
    }

     */
    // currentFloor = Floor.FL3;

    FloorWatchManager.getInstance()
        .setLabelsToUpdate(cleanLabels, dirtyLabels, podLabels, inUseLabels);
    setLabels();
    setAlerts();
  }

  public void setFloor(Floor floorToSet) {
    currentFloor = floorToSet;
  }

  /** Increases the value of the current floor by 1 */
  public void nextFloor() {
    setFloor(currentFloor.next());
    System.out.println(currentFloor + " for labels");
    // allFloorsObserver.setFloor(currentFloor);
    System.out.println(currentFloor.toInt());
    setLabels();
    System.out.println("Next observer");
  }

  /** Increases the value of the current floor by 1 */
  public void prevFloor() {
    setFloor(currentFloor.prev());
    System.out.println(currentFloor + " for labels");
    // allFloorsObserver.setFloor(currentFloor);
    setLabels();
    System.out.println("Prev observer");
  }

  public void setAlerts() {
    layoutAlerts.getItems().clear();
    List<List<Alert>> allFloorAlerts = AlertObserver.getInstance().getAllFloorAlerts();

    for (List<Alert> floorAlert : allFloorAlerts)
      for (Alert inFloorAlert : floorAlert) layoutAlerts.getItems().add(inFloorAlert.getMessage());
  }

  public void readFloorInput() {
    currentFloor = currentFloor.toFloorEnum(floorSelect.getText());
    setLabels();
  }

  /** takes all labels and applies appropriate amount based current observed floor */
  public void setLabels() {

    List<DashboardObserver> floorObservers = FloorWatchManager.getInstance().getAllFloorObservers();
    //    System.out.println(currentFloor);
    //    System.out.println(
    //        "Current Observer Floor"
    //            + floorObservers.get(currentFloor.toInt()).getFloor().toFloorString());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " clean List:"
    //            + floorObservers.get(currentFloor.toInt()).getCleanList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " dirty List:"
    //            + floorObservers.get(currentFloor.toInt()).getDirtyList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " pod List:"
    //            + floorObservers.get(currentFloor.toInt()).getPodList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " in-use List:"
    //            + floorObservers.get(currentFloor.toInt()).getInUse().size());

    List<Alert> currentAlerts = floorObservers.get(currentFloor.toInt()).getFloorAlerts();
    for (int alert = 0; alert < currentAlerts.size(); alert++)
      System.out.println(currentAlerts.get(alert).getMessage());

    floorObservers.get(currentFloor.toInt()).updateLabels();
    floorSelect.setText(currentFloor.toFloorString());
    AlertObserver.getInstance().setFloorAlertCount();
  }
}
