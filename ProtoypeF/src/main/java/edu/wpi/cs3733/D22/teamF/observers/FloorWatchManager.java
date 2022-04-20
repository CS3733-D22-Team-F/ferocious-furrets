package edu.wpi.cs3733.D22.teamF.observers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

public class FloorWatchManager {
  private static FloorWatchManager m_FloorWatchmanager;

  static {
    try {
      m_FloorWatchmanager = new FloorWatchManager();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private List<DashboardObserver> allFloorObservers = new ArrayList<>();

  private FloorWatchManager() throws SQLException {
    DashboardObserver ll2Observer = new DashboardObserver(Floor.LL2);
    allFloorObservers.add(ll2Observer);
    DashboardObserver ll1Observer = new DashboardObserver(Floor.LL1);
    allFloorObservers.add(ll1Observer);
    DashboardObserver f1Observer = new DashboardObserver(Floor.FL1);
    allFloorObservers.add(f1Observer);
    DashboardObserver f2Observer = new DashboardObserver(Floor.FL2);
    allFloorObservers.add(f2Observer);
    DashboardObserver f3Observer = new DashboardObserver(Floor.FL3);
    allFloorObservers.add(f3Observer);
    DashboardObserver f4Observer = new DashboardObserver(Floor.FL4);
    allFloorObservers.add(f4Observer);
    // Makes the last one send events to AlertObserver
    DashboardObserver f5Observer = new DashboardObserver(Floor.FL5);
    f5Observer.addPropertyChangeListener(AlertObserver.getInstance());
    allFloorObservers.add(f5Observer);

    for (DashboardObserver observer : allFloorObservers)
      FloorObservable.getInstance().addPropertyChangeListener(observer);

    FloorObservable.getInstance().setState();
  }

  public static FloorWatchManager getInstance() {
    return m_FloorWatchmanager;
  }

  public List<DashboardObserver> getAllFloorObservers() {
    return allFloorObservers;
  }

  public void setLabelsToUpdate(
      List<Label> cleanLabels,
      List<Label> dirtyLabels,
      List<Label> podLabels,
      List<Label> inUseLabels) {

    for (DashboardObserver observer : allFloorObservers)
      observer.setLabels(cleanLabels, dirtyLabels, podLabels, inUseLabels);
  }

  public void setEquipCountLabels(List<Label> countLabels) {
    for (int counter = 0; counter < countLabels.size(); counter++)
      allFloorObservers.get(counter).setTotalCountLabels(countLabels.get(counter));
  }
}
