package edu.wpi.cs3733.D22.teamF.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AlertObserver implements PropertyChangeListener {
  private static List<List<Alert>> allFloorAlerts = new ArrayList<>();
  private static AlertObserver m_AlertObserver = null;

  private int ll2Count = 0;
  private int ll1Count = 0;
  private int fl1Count = 0;
  private int fl2Count = 0;
  private int fl3Count = 0;
  private int fl4Count = 0;
  private int fl5Count = 0;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    allFloorAlerts = DashboardObserver.getAllFloorAlerts();
    setFloorAlertCount();
    System.out.println("AlertObserver found new alerts");
  }

  public static AlertObserver getInstance() {
    if (m_AlertObserver == null) m_AlertObserver = new AlertObserver();

    return m_AlertObserver;
  }

  public List<List<Alert>> getAllFloorAlerts() {
    return allFloorAlerts;
  }

  public void setFloorAlertCount() {
    if (allFloorAlerts.size() == 7) {
      ll2Count = allFloorAlerts.get(0).size();
      ll1Count = allFloorAlerts.get(1).size();
      fl1Count = allFloorAlerts.get(2).size();
      fl2Count = allFloorAlerts.get(3).size();
      fl3Count = allFloorAlerts.get(4).size();
      fl4Count = allFloorAlerts.get(5).size();
      fl5Count = allFloorAlerts.get(6).size();

      System.out.println("Floor Alert Counts");
      System.out.println(ll2Count);
      System.out.println(ll1Count);
      System.out.println(fl1Count);
      System.out.println(fl2Count);
      System.out.println(fl3Count);
      System.out.println(fl4Count);
      System.out.println(fl5Count);
    }
  }
}
