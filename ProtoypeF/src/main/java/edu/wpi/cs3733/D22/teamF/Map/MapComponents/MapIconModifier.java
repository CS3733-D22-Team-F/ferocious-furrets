package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import afester.javafx.svg.SvgLoader;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.*;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

/** for dealing with modifying icons on the map */
public class MapIconModifier {
  public static SvgLoader iconLoader = new SvgLoader();

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
    } else {
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
        new Image(
            Objects.requireNonNull(
                Fapp.class.getResourceAsStream("Map/Icons/MapIcons/PATI Icon.png")));
    switch (type) {
      case "PATI":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/PATI Icon.png")));
          break;
        }
      case "STOR":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/STOR Icon.png")));
          break;
        }
      case "DIRT":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/DIRT Icon.png")));
          break;
        }
      case "HALL":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/HALL Icon.png")));
          break;
        }
      case "ELEV":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/ELEV Icon.png")));
          break;
        }
      case "REST":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/REST Icon.png")));
          break;
        }
      case "STAI":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/STAI Icon.png")));
          break;
        }
      case "DEPT":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/DEPT Icon.png")));
          break;
        }
      case "LABS":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/LABS Icon.png")));
          break;
        }
      case "INFO":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/INFO Icon.png")));
          break;
        }
      case "CONF":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/CONF Icon.png")));
          break;
        }
      case "EXIT":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/EXIT Icon.png")));
          break;
        }
      case "RETL":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/RETL Icon.png")));
          break;
        }
      case "SERV":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapIcons/SERV Icon.png")));
          break;
        }
      case "Infusion Pump":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/EquipmentIcons/IPMP Icon.png")));
          break;
        }
      case "Bed":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/EquipmentIcons/PTBD Icon.png")));
          break;
        }
      case "Recliner":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/EquipmentIcons/RECL Icon.png")));
          break;
        }
      case "Xray":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/EquipmentIcons/XRAY Icon.png")));
          break;
        }
      case "add":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/add.png")));
          break;
        }
      case "history":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/history.png")));
          break;
        }
      case "home":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/home.png")));
          break;
        }
      case "load":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/load.png")));
          break;
        }
      case "menu":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/menu.png")));
          break;
        }
      case "save":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/save.png")));
          break;
        }
      case "table":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/MapMenuIcon/table.png")));
          break;
        }
      case "audio&visual":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/AudioVisualIcon.png")));
          break;
        }
      case "equip":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/EquipmentIcon.png")));
          break;
        }
      case "gift":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/GiftIcon.png")));
          break;
        }
      case "lab":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/LabIcon.png")));
          break;
        }
      case "meal":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/MealIcon.png")));
          break;
        }
      case "medical":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/MedicineIcon.png")));
          break;
        }
      case "scan":
        {
          image =
              new Image(
                  Objects.requireNonNull(
                      Fapp.class.getResourceAsStream("Map/Icons/ServiceIcon/ScanIcon.png")));
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
   *
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
   *
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

  /**
   * Add an icon to the map at a location node to provide a graphical representation of the location
   *
   * @param location
   * @throws FileNotFoundException
   */
  public static void addIcon(TableView<Location> table, AnchorPane iconPane, Location location)
      throws FileNotFoundException, SQLException {
    if (!location.getShortName().equals("done")) {
      JFXButton newButton = new JFXButton("", MapIconModifier.getIcon(location.getNodeType()));
      newButton.setPrefSize(20, 20);
      newButton.setMinSize(20, 20);
      newButton.setMaxSize(20, 20);
      if (UserType.getUserType().equals("admin")) {
        if (getLocType(location).equals("location")) {
          final Delta dragDelta = new Delta();
          newButton.setOnMouseClicked(
              e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                  if (MapIconModifier.locationIconList.containsValue(newButton)) {
                    Location lo =
                        new ArrayList<>(
                                MapIconModifier.getKeysByValue(
                                    MapIconModifier.locationIconList, newButton))
                            .get(0);
                    try {
                      MapPopUp.popUpLocModify(table, iconPane, lo);
                      MapTableHolder.loadMap(table, iconPane);
                    } catch (IOException | SQLException ex) {
                      ex.printStackTrace();
                    }
                  }
                }
              });

          newButton.setOnMousePressed(
              e -> {
                dragDelta.x = newButton.getLayoutX() - e.getSceneX();
                dragDelta.y = newButton.getLayoutY() - e.getSceneY();
                newButton.setCursor(Cursor.MOVE);
              });
          newButton.setOnMouseReleased(
              e -> {
                newButton.setCursor(Cursor.HAND);
              });
          newButton.setOnMouseEntered(
              e -> {
                newButton.setCursor(Cursor.HAND);
              });
          newButton.setOnMouseDragged(
              e -> {
                newButton.setLayoutX(e.getSceneX() + dragDelta.x);
                newButton.setLayoutY(e.getSceneY() + dragDelta.y);
              });
        } else if (getLocType(location).equals("service")
            && !location.getShortName().equals("done")) {
          newButton.setOnAction(
              e -> {
                if (MapIconModifier.locationIconList.containsValue(newButton)) {
                  Location lo =
                      new ArrayList<>(
                              MapIconModifier.getKeysByValue(
                                  MapIconModifier.locationIconList, newButton))
                          .get(0);
                  try {
                    MapPopUp.popUpDone(table, iconPane, lo);
                    MapTableHolder.loadMap(table, iconPane);
                  } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                  }
                }
              });
        } else if (getLocType(location).equals("equipment")) {
          newButton.setOnAction(
              e -> {
                if (MapIconModifier.locationIconList.containsValue(newButton)) {
                  Location lo =
                      new ArrayList<>(
                              MapIconModifier.getKeysByValue(
                                  MapIconModifier.locationIconList, newButton))
                          .get(0);
                  try {
                    MapPopUp.popUpEquipModify(table, iconPane, lo);
                    MapTableHolder.loadMap(table, iconPane);
                  } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                  }
                }
              });
        }
      }
      double x =
          (location.getXcoord() / 4450.0) * 880; // change the image resolution to pane resolution
      double y = (location.getYcoord() / 3550.0) * 700;
      newButton.setLayoutX(x);
      newButton.setLayoutY(y);
      iconPane.getChildren().add(newButton);
      MapIconModifier.locationIconList.put(location, newButton);
    }
  }

  public static String getLocType(Location location) {
    if (location.getNodeType().equals("Infusion Pump")
        || location.getNodeType().equals("Bed")
        || location.getNodeType().equals("Recliner")
        || location.getNodeType().equals("Xray")) {
      return "equipment";
    } else if (location.getNodeType().equals("audio&visual")
        || location.getNodeType().equals("equip")
        || location.getNodeType().equals("gift")
        || location.getNodeType().equals("lab")
        || location.getNodeType().equals("meal")
        || location.getNodeType().equals("medicine")
        || location.getNodeType().equals("scan")) {
      return "service";
    } else {
      return "location";
    }
  }
}

class Delta {
  double x, y;
}
