package edu.wpi.cs3733.D22.teamF.observers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

  private VBox appAlertVbox;
  private JFXNodesList dashBoardNodeList;

  private boolean isDashAlertsReady;
  private boolean isAppAlertsReady;
  private Label appAlertLabel;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    allFloorAlerts = DashboardObserver.getAllFloorAlerts();
    setFloorAlertCount();
    updateButtons();
    updateAppAlerts();
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
      // System.out.println("Floor Alert Counts");
    }
  }

  // TODO
  public void setAlertNotifications(List<JFXButton> buttons) {
    this.buttonsToUpdate = buttons;
  }

  public void setDashBoardNodeList(JFXNodesList newList) {
    this.dashBoardNodeList = newList;
    isDashAlertsReady = true;
  }

  public void setAppAlertVbox(VBox newVbox) {
    this.appAlertVbox = newVbox;
    isAppAlertsReady = true;
  }

  public void setAppAlertsLabel(Label newLabel) {
    this.appAlertLabel = newLabel;
  };

  public void updateAppAlerts() {

    if (isDashAlertsReady && isAppAlertsReady) {
      appAlertVbox.getChildren().clear();
      dashBoardNodeList.getChildren().removeAll();

      for (int count = 0; count < totalAlertCount; count++) {
        for (int i = 0; i < allFloorAlerts.get(count).size(); i++) {
          String message = allFloorAlerts.get(count).get(i).getMessage();
          JFXButton newAlert = new JFXButton(message);
          Label newLabel = new Label(message);
          newLabel.textFillProperty().set(Color.WHITE);
          newLabel.fontProperty().setValue(Font.font("Serif", 16));
          newLabel.setWrapText(true);
          newLabel.setMaxWidth(170);
          newLabel.setStyle("-fx-background-color: #123090");
          newLabel.setStyle("-fx-border-color: #ffffff");
          newLabel.setStyle("-fx-border-radius: 1");
          newLabel.setTextAlignment(TextAlignment.CENTER);
          newAlert.buttonTypeProperty().set(JFXButton.ButtonType.RAISED);
          appAlertVbox.getChildren().add(newLabel);
          dashBoardNodeList.getChildren().add(newAlert);

          this.appAlertLabel.setText(String.valueOf(totalAlertCount));
        }
      }
      //      for (List<Alert> floorAlert : allFloorAlerts) {
      //        for (Alert inFloorAlert : floorAlert) {
      //          JFXButton newAlert = new JFXButton(inFloorAlert.getMessage());
      //          Label newLabel = new Label(inFloorAlert.getMessage());
      //          newLabel.textFillProperty().set(Color.WHITE);
      //          newLabel.fontProperty().setValue(Font.font("Serif", 12));
      //          newLabel.setWrapText(true);
      //          newAlert.buttonTypeProperty().set(JFXButton.ButtonType.RAISED);
      //          appAlertVbox.getChildren().add(newLabel);
      //          dashBoardNodeList.getChildren().add(newAlert);
      //
      //          this.appAlertLabel.setText(String.valueOf(totalAlertCount));
      //
      //          System.out.println(totalAlertCount);
      //        }
      //      }
    }
  }

  public void updateButtons() {
    //    System.out.println("In class & button are" + !buttonsToUpdate.isEmpty());

    if (!buttonsToUpdate.isEmpty()) {
      //      System.out.println("Floor Alert Counts");
      buttonsToUpdate.get(0).setText(ll2Count + "  LL2");
      buttonsToUpdate.get(1).setText(ll1Count + "  LL1");
      buttonsToUpdate.get(2).setText(fl1Count + "  FL1");
      buttonsToUpdate.get(3).setText(fl2Count + "  LL2");
      buttonsToUpdate.get(4).setText(fl3Count + "  LL3");
      buttonsToUpdate.get(5).setText(fl4Count + "  LL4");
      buttonsToUpdate.get(6).setText(fl5Count + "  LL5");
      buttonsToUpdate.get(7).setText(totalAlertCount + " Alerts");

      //      System.out.println(ll2Count);
      //      System.out.println(ll1Count);
      //      System.out.println(fl1Count);
      //      System.out.println(fl2Count);
      //      System.out.println(fl3Count);
      //      System.out.println(fl4Count);
      //      System.out.println(fl5Count);
    }
  }
}
