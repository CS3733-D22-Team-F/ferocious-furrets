package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.*;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MapIconModifier {
  static String currentIcon = "";

  public static ArrayList<ArrayList<Object>> locationIconList = new ArrayList<>();

  /**
   * delete a location icon from the map
   *
   * @param nodeID
   */
  public static void deleteIcon(String nodeID) {
    for (int i = 0; i < locationIconList.size(); i++) {
      if (((Location) locationIconList.get(i).get(0)).getNodeID().equals(nodeID)) {
        ((AnchorPane) ((JFXButton) locationIconList.get(i).get(1)).getParent())
            .getChildren()
            .remove((JFXButton) locationIconList.get(i).get(1));
        locationIconList.remove(i);
      }
    }
  }

  /**
   * Gets the correct type of icon depending on the nodeType of the location
   *
   * @param type
   * @return
   * @throws FileNotFoundException
   */
  public static ImageView getIcon(String type) {
    Image image =
        new Image(mapPageController.class.getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
    switch (type) {
      case "PATI":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
          break;
        }
      case "STOR":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/STOR Icon.png"));
          break;
        }
      case "DIRT":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/DIRT Icon.png"));
          break;
        }
      case "HALL":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/HALL Icon.png"));
          break;
        }
      case "ELEV":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/ELEV Icon.png"));
          break;
        }
      case "REST":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/REST Icon.png"));
          break;
        }
      case "STAI":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/STAI Icon.png"));
          break;
        }
      case "DEPT":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/DEPT Icon.png"));
          break;
        }
      case "LABS":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/LABS Icon.png"));
          break;
        }
      case "INFO":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/INFO Icon.png"));
          break;
        }
      case "CONF":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/CONF Icon.png"));
          break;
        }
      case "EXIT":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/EXIT Icon.png"));
          break;
        }
      case "RETL":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/RETL Icon.png"));
          break;
        }
      case "SERV":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapIcons/SERV Icon.png"));
          break;
        }
      case "Infusion Pump":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/IPMP Icon.png"));
          break;
        }
      case "Bed":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/PTBD Icon.png"));
          break;
        }
      case "Recliner":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/RECL Icon.png"));
          break;
        }
      case "Xray":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/XRAY Icon.png"));
          break;
        }
    }
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(25);
    imageView.setFitWidth(25);
    return imageView;
  }

  public static void showOneIcon(String type) {
    for (ArrayList<Object> objects : locationIconList) {
      ((JFXButton) objects.get(1))
          .setVisible(
              ((Location) objects.get(0)).getNodeType().equals(type)
                  && ((Location) objects.get(0))
                      .getFloor()
                      .equals(MapLocationModifier.currentFloor));
    }
  }

  public static void showIcon(JFXButton showIconButton) {
    if (showIconButton.getText().equals("ALL ICON")) {
      showFloorIcon(MapLocationModifier.currentFloor);
      showIconButton.setText("HIDE ICON");
      showIconButton.setStyle("-fx-background-color: red");
    } else if (showIconButton.getText().equals("HIDE ICON")) {
      showFloorIcon("99");
      showIconButton.setText("ALL ICON");
      showIconButton.setStyle("-fx-background-color: #062558");
    }
  }

  public static void showAllIcon() {
    showFloorIcon(MapLocationModifier.currentFloor);
  }

  public static void showPatient() {
    showOneIcon("PATI");
    currentIcon = "PATI";
  }

  public static void showStorage() {
    showOneIcon("STOR");
    currentIcon = "STOR";
  }

  public static void showDirty() {
    showOneIcon("DIRT");
    currentIcon = "DIRT";
  }

  public static void showHallway() {
    showOneIcon("HALL");
    currentIcon = "HALL";
  }

  public static void showElev() {
    showOneIcon("ELEV");
    currentIcon = "ELEV";
  }

  public static void showRestroom() {
    showOneIcon("REST");
    currentIcon = "REST";
  }

  public static void showStair() {
    showOneIcon("STAI");
    currentIcon = "STAI";
  }

  public static void showDepartment() {
    showOneIcon("DEPT");
    currentIcon = "DEPT";
  }

  public static void showLab() {
    showOneIcon("LABS");
    currentIcon = "LABS";
  }

  public static void showInformation() {
    showOneIcon("INFO");
    currentIcon = "INFO";
  }

  public static void showConference() {
    showOneIcon("CONF");
    currentIcon = "CONF";
  }

  public static void showExit() {
    showOneIcon("EXIT");
    currentIcon = "EXIT";
  }

  public static void showRetail() {
    showOneIcon("RETL");
    currentIcon = "RETL";
  }

  public static void showService() {
    showOneIcon("SERV");
    currentIcon = "SERV";
  }

  public static void showBad() {
    showOneIcon("Bed");
    currentIcon = "Bed";
  }

  public static void showXray() {
    showOneIcon("Xray");
    currentIcon = "Xray";
  }

  public static void showPump() {
    showOneIcon("Infusion Pump");
    currentIcon = "Infusion Pump";
  }

  public static void showRecliner() {
    showOneIcon("Recliner");
    currentIcon = "Recliner";
  }

  public static void showFloorIcon(String floor) {
    for (ArrayList<Object> objects : locationIconList) {
      ((JFXButton) objects.get(1)).setVisible(((Location) objects.get(0)).getFloor().equals(floor));
    }
  }
}
