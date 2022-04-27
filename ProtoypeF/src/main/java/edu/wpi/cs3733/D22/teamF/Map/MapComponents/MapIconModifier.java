package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import afester.javafx.svg.SvgLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.layout.VBox;

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
                Fapp.class.getResourceAsStream("Map/Icons/MapIcons/Service.png")));
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
              new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/add.png")));
          break;
        }
      case "history":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/history.png")));
          break;
        }
      case "home":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/home.png")));
          break;
        }
      case "load":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/load.png")));
          break;
        }
      case "menu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/menu.png")));
          break;
        }
      case "save":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/save.png")));
          break;
        }
      case "table":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/table.png")));
          break;
        }
      case "audioMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/audio.png")));
          break;
        }
      case "dashboardMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/dashboard.png")));
          break;
        }
      case "equipmentMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/equipment.png")));
          break;
        }
      case "giftMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/gift.png")));
          break;
        }
      case "labMenu":
        {
          image =
              new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/lab.png")));
          break;
        }
      case "mapMenu":
        {
          image =
              new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/map.png")));
          break;
        }
      case "mealMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/meal.png")));
          break;
        }
      case "medicineMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/medicine.png")));
          break;
        }
      case "outMenu":
        {
          image =
              new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/out.png")));
          break;
        }
      case "scanMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/scan.png")));
          break;
        }
      case "serviceMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/service.png")));
          break;
        }
      case "settingsMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/settings.png")));
          break;
        }
      case "physicalMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/physical.png")));
          break;
        }
      case "securityMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/security.png")));
          break;
        }
      case "toolMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/tool.png")));
          break;
        }
      case "cleanMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/clean.png")));
          break;
        }
      case "infoMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/info.png")));
          break;
        }
      case "backMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/back.png")));
          break;
        }
      case "queueMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/queue.png")));
          break;
        }
      case "employeeMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/employee.png")));
          break;
        }
      case "linkMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/link.png")));
          break;
        }
      case "apiMenu":
        {
          image =
              new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/api.png")));
          break;
        }
      case "patientMenu":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/patient.png")));
          break;
        }
      case "logo":
        {
          image =
              new Image(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream("MenuIcon/logo.png")));
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
    if (showIconButton.getText().equals("All Icon")) {
      showFloorIcon(MapLocationModifier.currentFloor);
      showIconButton.setText("Hide Icon");
    } else if (showIconButton.getText().equals("Hide Icon")) {
      showFloorIcon("99");
      showIconButton.setText("All Icon");
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
  public static void addIcon(
      TableView<Location> table,
      AnchorPane iconPane,
      Location location,
      VBox infoBox,
      JFXTextArea locationArea)
      throws FileNotFoundException, SQLException {
    if (!location.getShortName().equalsIgnoreCase("done")) {
      JFXButton newButton = new JFXButton("", MapIconModifier.getIcon(location.getNodeType()));
      newButton.setPrefSize(20, 20);
      newButton.setMinSize(20, 20);
      newButton.setMaxSize(20, 20);
      if (UserType.getUserType().equalsIgnoreCase("admin")) {
        if (getLocType(location).equalsIgnoreCase("location")) {
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
                      MapTableHolder.loadMap(table, iconPane, infoBox, locationArea);
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
                if (e.getButton() == MouseButton.PRIMARY) {
                  newButton.setCursor(Cursor.HAND);
                  ArrayList<Location> list = new ArrayList<>(locationIconList.keySet());
                  ArrayList<JFXButton> bList = new ArrayList<>(locationIconList.values());
                  Location loc = list.get(bList.indexOf(newButton));
                  String oldID = loc.getNodeID();
                  String xValue =
                      (newButton.getLayoutX() / (iconPane.getPrefWidth() * 0.95) * 4450.0) + "";
                  String yValue =
                      (newButton.getLayoutY() / (iconPane.getPrefHeight() * 0.95) * 3550.0) + "";
                  try {
                    MapLocationModifier.modifyLocation(
                        oldID,
                        loc.getNodeType(),
                        xValue,
                        yValue,
                        loc.getFloor(),
                        loc.getLongName(),
                        loc.getShortName());
                    MapTableHolder.loadMap(table, iconPane, infoBox, locationArea);
                  } catch (SQLException ex) {
                    ex.printStackTrace();
                  } catch (IOException ex) {
                    ex.printStackTrace();
                  }
                }
              });
          newButton.setOnMouseDragged(
              e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                  newButton.setLayoutX(e.getSceneX() + dragDelta.x);
                  newButton.setLayoutY(e.getSceneY() + dragDelta.y);
                }
              });
          newButton.setOnMouseEntered(
              e -> {
                MapPageController.inButton = true;
                int i = 0;
                ArrayList<Location> loList = new ArrayList<>(table.getItems());
                for (Location lo : loList) {
                  if (lo.equals(location)) {
                    i = loList.indexOf(lo);
                  }
                }
                table.scrollTo(i);
                table.getSelectionModel().select(i);
                newButton.setCursor(Cursor.HAND);
                infoBox.setVisible(true);
                try {
                  for (String s : MapTableHolder.getAllLocOnNID(location)) {
                    locationArea.setText(locationArea.getText() + "\n" + s);
                  }
                  locationArea.setText(locationArea.getText().trim());
                } catch (SQLException | IOException ex) {
                  ex.printStackTrace();
                }
              });
          newButton.setOnMouseExited(
              e -> {
                MapPageController.inButton = false;
                locationArea.clear();
                infoBox.setVisible(false);
              });
          newButton.setOnScroll(
              e -> {
                locationArea
                    .scrollTopProperty()
                    .set(locationArea.scrollTopProperty().get() + 1 * e.getDeltaY());
              });
        } else if (getLocType(location).equalsIgnoreCase("service")
            && !location.getShortName().equalsIgnoreCase("done")) {
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
                    MapTableHolder.loadMap(table, iconPane, infoBox, locationArea);
                  } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                  }
                }
              });
          newButton.setOnMouseEntered(
              e -> {
                MapPageController.inButton = true;
                int i = 0;
                ArrayList<Location> loList = new ArrayList<>(table.getItems());
                for (Location lo : loList) {
                  if (lo.equals(location)) {
                    i = loList.indexOf(lo);
                  }
                }
                table.scrollTo(i);
                table.getSelectionModel().select(i);
                newButton.setCursor(Cursor.HAND);
                infoBox.setVisible(true);
                try {
                  for (String s : MapTableHolder.getAllLocOnNID(location)) {
                    locationArea.setText(locationArea.getText() + "\n" + s);
                  }
                  locationArea.setText(locationArea.getText().trim());
                } catch (SQLException | IOException ex) {
                  ex.printStackTrace();
                }
              });
          newButton.setOnMouseExited(
              e -> {
                MapPageController.inButton = false;
                locationArea.clear();
                infoBox.setVisible(false);
              });
          newButton.setOnScroll(
              e -> {
                locationArea
                    .scrollTopProperty()
                    .set(locationArea.scrollTopProperty().get() + 1 * e.getDeltaY());
              });
        } else if (getLocType(location).equalsIgnoreCase("Equipment")) {
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
                      MapPopUp.popUpEquipModify(table, iconPane, lo);
                      MapTableHolder.loadMap(table, iconPane, infoBox, locationArea);
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
                if (e.getButton() == MouseButton.PRIMARY) {
                  newButton.setCursor(Cursor.HAND);
                  ArrayList<Location> list = new ArrayList<>(locationIconList.keySet());
                  ArrayList<JFXButton> bList = new ArrayList<>(locationIconList.values());
                  Location loc = list.get(bList.indexOf(newButton));
                  Location nearLoc = null;
                  double currentDis = 99999.9;
                  for (Location l : list) {
                    if (calculateDistance(l, loc) < currentDis
                        && l.getFloor().equals(loc.getFloor())
                        && getLocType(l).equalsIgnoreCase("location")) {
                      nearLoc = l;
                      currentDis = calculateDistance(l, loc);
                    }
                  }
                  try {
                    MapEquipmentModifier.modifyEquipment(
                        nearLoc.getNodeID(), loc.getBuilding(), loc.getShortName());
                  } catch (SQLException ex) {
                    ex.printStackTrace();
                  }
                  try {
                    MapTableHolder.loadMap(table, iconPane, infoBox, locationArea);
                  } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                  }
                }
              });
          newButton.setOnMouseDragged(
              e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                  newButton.setLayoutX(e.getSceneX() + dragDelta.x);
                  newButton.setLayoutY(e.getSceneY() + dragDelta.y);
                }
              });
          newButton.setOnMouseEntered(
              e -> {
                MapPageController.inButton = true;
                int i = 0;
                ArrayList<Location> loList = new ArrayList<>(table.getItems());
                for (Location lo : loList) {
                  if (lo.equals(location)) {
                    i = loList.indexOf(lo);
                  }
                }
                table.scrollTo(i);
                table.getSelectionModel().select(i);
                newButton.setCursor(Cursor.HAND);
                infoBox.setVisible(true);
                try {
                  for (String s : MapTableHolder.getAllLocOnNID(location)) {
                    locationArea.setText(locationArea.getText() + "\n" + s);
                  }
                  locationArea.setText(locationArea.getText().trim());
                } catch (SQLException | IOException ex) {
                  ex.printStackTrace();
                }
              });
          newButton.setOnMouseExited(
              e -> {
                MapPageController.inButton = false;
                locationArea.clear();
                infoBox.setVisible(false);
              });
          newButton.setOnScroll(
              e -> {
                locationArea
                    .scrollTopProperty()
                    .set(locationArea.scrollTopProperty().get() + 1 * e.getDeltaY());
              });
        }
      }
      double x =
          (location.getXcoord() / 4450.0)
              * (iconPane.getPrefWidth() * 0.95); // change the image resolution to pane resolution
      double y = (location.getYcoord() / 3550.0) * (iconPane.getPrefHeight() * 0.95);
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
    } else if (location.getNodeType().equals("Audio/Visual")
        || location.getNodeType().equals("Equipment")
        || location.getNodeType().equals("Gift")
        || location.getNodeType().equals("Lab")
        || location.getNodeType().equals("Meal")
        || location.getNodeType().equals("Medicine")
        || location.getNodeType().equals("Maintenance")
        || location.getNodeType().equals("Security")
        || location.getNodeType().equals("Facilities")
        || location.getNodeType().equals("ExternalPatient")
        || location.getNodeType().equals("InternalPatient")
        || location.getNodeType().equals("Scan")) {
      return "service";
    } else {
      return "location";
    }
  }

  public static double calculateDistance(Location random, Location thisLoc) {
    JFXButton button1 = locationIconList.get(random);
    JFXButton button2 = locationIconList.get(thisLoc);
    double temp =
        Math.pow((button1.getLayoutY() - button2.getLayoutY()), 2)
            + Math.pow((button1.getLayoutX() - button2.getLayoutX()), 2);
    return Math.pow(temp, 0.5);
  }
}

class Delta {
  double x, y;
}
