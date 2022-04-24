package edu.wpi.cs3733.D22.teamF.observers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AlertObserver implements PropertyChangeListener {
  private static List<List<Alert>> allFloorAlerts = new ArrayList<>();
  private static AlertObserver m_AlertObserver = new AlertObserver();

  private List<JFXButton> buttonsToUpdate = new ArrayList<>();
  private int ll2Count = 0;
  private int ll1Count = 0;
  private int fl1Count = 0;
  private int fl2Count = 0;
  private int fl3Count = 0;
  private int fl4Count = 0;
  private int fl5Count = 0;
  private int totalAlertCount = 0;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    allFloorAlerts = DashboardObserver.getAllFloorAlerts();
    setFloorAlertCount();
    updateButtons();
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
      totalAlertCount = ll1Count + ll2Count + fl5Count + fl4Count + fl3Count + fl2Count + fl1Count;
    }
  }

  public void setAlertNotifications(List<JFXButton> buttons) {
    this.buttonsToUpdate = buttons;
  }

  public void updateButtons() {
    if (!buttonsToUpdate.isEmpty()
        && SceneManager.getInstance().getCurrentScene().equals("views/mapPage.fxml")) {
      buttonsToUpdate.get(0).setText(ll2Count + "  LL2");
      buttonsToUpdate.get(1).setText(ll1Count + "  LL1");
      buttonsToUpdate.get(2).setText(fl1Count + "  FL1");
      buttonsToUpdate.get(3).setText(fl2Count + "  LL2");
      buttonsToUpdate.get(4).setText(fl3Count + "  LL3");
      buttonsToUpdate.get(5).setText(fl4Count + "  LL4");
      buttonsToUpdate.get(6).setText(fl5Count + "  LL5");
      buttonsToUpdate.get(7).setText(totalAlertCount + " Alerts");
    }
  }
}
