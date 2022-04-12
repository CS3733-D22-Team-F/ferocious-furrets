package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.*;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * for dealing with modifying icons on the map
 */
public class MapIconModifier {
  static String currentIcon = "";

  public static HashMap<Location, JFXButton> locationIconList = new HashMap<>();

  /**
   * delete a location icon from the map
   *
   * @param location
   */
  public static void deleteIcon(Location location) {
    if (locationIconList.containsKey(location)) {
      ((AnchorPane) (locationIconList.get(location).getParent()))
          .getChildren()
          .remove(locationIconList.get(location));
      locationIconList.remove(location);
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
      case "add":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/add.png"));
          break;
        }
      case "history":
        {
          image =
              new Image(
                  mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/history.png"));
          break;
        }
      case "home":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/home.png"));
          break;
        }
      case "load":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/load.png"));
          break;
        }
      case "menu":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/menu.png"));
          break;
        }
      case "save":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/save.png"));
          break;
        }
      case "table":
        {
          image =
              new Image(mapPageController.class.getResourceAsStream("Icons/MapMenuIcon/table.png"));
          break;
        }
    }
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(20);
    imageView.setFitWidth(20);
    return imageView;
  }

  /**
   * Shows an icon on the map
   * @param type String type of icon
   */
  public static void showOneIcon(String type) {
    Collection<JFXButton> iconList = locationIconList.values();
    ArrayList<JFXButton> iconArrayList = new ArrayList<>(iconList);
    Collection<Location> locationList = locationIconList.keySet();
    ArrayList<Location> locationArrayList = new ArrayList<>(locationList);
    for (int i = 0; i < iconList.size(); i++) {
      iconArrayList
          .get(i)
          .setVisible(
              locationArrayList.get(i).getNodeType().equals(type)
                  && locationArrayList.get(i).getFloor().equals(MapLocationModifier.currentFloor));
    }
  }

  /**
   * shows an icon taking in a button
   * @param showIconButton JFXButton
   */
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
    Collection<JFXButton> iconList = locationIconList.values();
    ArrayList<JFXButton> iconArrayList = new ArrayList<>(iconList);
    Collection<Location> locationList = locationIconList.keySet();
    ArrayList<Location> locationArrayList = new ArrayList<>(locationList);
    for (int i = 0; i < iconList.size(); i++) {
      iconArrayList.get(i).setVisible(locationArrayList.get(i).getFloor().equals(floor));
    }
  }

  public static <Location, JFXButton> Set<Location> getKeysByValue(
      HashMap<Location, JFXButton> map, JFXButton value) {
    return map.entrySet().stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }
}
