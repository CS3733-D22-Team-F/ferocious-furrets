package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamF.AGlobalMethods;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.*;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.observers.AlertObserver;
import edu.wpi.cs3733.D22.teamF.observers.FloorObservable;
import edu.wpi.cs3733.D22.teamF.observers.FloorWatchManager;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the map page including functionality for contolling the map scene up changing
 * views
 */
public class MapPageController implements Initializable {

  private static final String ANIMATED_OPTION_BUTTON = "animated-option-button";
  private static final String ANIMATED_OPTION_SUB_BUTTON = "animated-option-sub-button";
  private SimpleDoubleProperty mapScale;

  public static boolean inButton = false;

  @FXML ImageView mapHolder;

  @FXML TableView<Location> table;

  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> longName;

  @FXML ScrollPane scrollPane;
  @FXML Group mapGroup;
  @FXML AnchorPane iconPane;

  @FXML JFXSlider zoomSlider;

  @FXML JFXButton alertButton;
  @FXML JFXButton showIconButton;
  @FXML VBox infoBox;
  @FXML JFXTextArea locationField;

  @FXML JFXButton patientRoomButton;
  @FXML JFXButton storageButton;
  @FXML JFXButton dirtyButton;
  @FXML JFXButton hallwayButton;
  @FXML JFXButton elevButton;
  @FXML JFXButton restroomButton;
  @FXML JFXButton stairButton;
  @FXML JFXButton departmentButton;
  @FXML JFXButton labButton;
  @FXML JFXButton informationButton;
  @FXML JFXButton conferenceButton;
  @FXML JFXButton exitButton;
  @FXML JFXButton retailButton;
  @FXML JFXButton serviceButton;
  @FXML JFXButton bedButton;
  @FXML JFXButton xrayButton;
  @FXML JFXButton pumpButton;
  @FXML JFXButton reclinerButton;

  @FXML JFXNodesList floorNodeList = new JFXNodesList();
  @FXML JFXButton openFloorMenu;
  @FXML JFXButton changeToF5;
  @FXML JFXButton changeToF4;
  @FXML JFXButton changeToF3;
  @FXML JFXButton changeToF2;
  @FXML JFXButton changeToF1;
  @FXML JFXButton changeToL1;
  @FXML JFXButton changeToL2;

  @FXML JFXNodesList menuNodeList = new JFXNodesList();
  @FXML JFXButton openMenu;
  @FXML JFXButton addButton;
  @FXML JFXButton saveButton;
  @FXML JFXButton loadButton;
  @FXML JFXButton tableButton;
  @FXML JFXButton historyButton;

  @FXML JFXNodesList alertNodeList = new JFXNodesList();
  @FXML JFXButton alertButton1;
  @FXML JFXButton alertButton2;
  @FXML JFXButton alertButton3;
  @FXML JFXButton alertButton4;
  @FXML JFXButton alertButton5;
  @FXML JFXButton alertButtonL1;
  @FXML JFXButton alertButtonL2;

  Image F1 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor1.jpg")));
  Image F2 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor2.jpg")));
  Image F3 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor3.jpg")));
  Image F4 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor4.jpg")));
  Image F5 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor5.jpg")));
  Image L1 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Lower1.jpg")));
  Image L2 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Lower2.jpg")));

  /**
   * Initialize the map page, get all the equipment and locations from the database, get the x-y
   * coordinates of the equipment to plot on the map Initialize the table of all the locations to
   * display on the page Add the icons to the map to graphically display the locations and equipment
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeScroll();
    Floor.setCellValueFactory(new PropertyValueFactory<Location, String>("Floor"));
    Building.setCellValueFactory(new PropertyValueFactory<Location, String>("Building"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));

    try {
      MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }

    loadAllLegend();
    setUpNode();
    changeToF1();
    iniSlider();

    // alert buttons code
    List<JFXButton> alertbtnList = new ArrayList<>();
    alertbtnList.add(alertButtonL2);
    alertbtnList.add(alertButtonL1);
    alertbtnList.add(alertButton1);
    alertbtnList.add(alertButton2);
    alertbtnList.add(alertButton3);
    alertbtnList.add(alertButton4);
    alertbtnList.add(alertButton5);
    alertbtnList.add(alertButton);

    AlertObserver.getInstance().setAlertNotifications(alertbtnList);

    FloorWatchManager.getInstance(); // instantiation
    try {
      FloorObservable.getInstance().setState();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    AlertObserver.getInstance().updateButtons();
  }

  /** change map to floor 1, same for f2, f3, l1, l2 */
  public void changeToF1() {
    mapHolder.setImage(F1);
    MapLocationModifier.currentFloor = "1";
    MapIconModifier.showFloorIcon("1");
    openFloorMenu.setText("F1");
    floorNodeList.animateList(false);
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    MapLocationModifier.currentFloor = "2";
    MapIconModifier.showFloorIcon("2");
    openFloorMenu.setText("F2");
    floorNodeList.animateList(false);
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    MapLocationModifier.currentFloor = "3";
    MapIconModifier.showFloorIcon("3");
    openFloorMenu.setText("F3");
    floorNodeList.animateList(false);
  }

  public void changeToF4() {
    mapHolder.setImage(F4);
    MapLocationModifier.currentFloor = "4";
    MapIconModifier.showFloorIcon("4");
    openFloorMenu.setText("F4");
    floorNodeList.animateList(false);
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    MapLocationModifier.currentFloor = "5";
    MapIconModifier.showFloorIcon("5");
    openFloorMenu.setText("F5");
    floorNodeList.animateList(false);
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    MapLocationModifier.currentFloor = "L1";
    MapIconModifier.showFloorIcon("L1");
    openFloorMenu.setText("L1");
    floorNodeList.animateList(false);
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    MapLocationModifier.currentFloor = "L2";
    MapIconModifier.showFloorIcon("L2");
    openFloorMenu.setText("L2");
    floorNodeList.animateList(false);
  }

  public void loadAllLegend() {
    patientRoomButton.setGraphic(MapIconModifier.getIcon("PATI"));
    storageButton.setGraphic(MapIconModifier.getIcon("STOR"));
    dirtyButton.setGraphic(MapIconModifier.getIcon("DIRT"));
    hallwayButton.setGraphic(MapIconModifier.getIcon("HALL"));
    elevButton.setGraphic(MapIconModifier.getIcon("ELEV"));
    restroomButton.setGraphic(MapIconModifier.getIcon("REST"));
    stairButton.setGraphic(MapIconModifier.getIcon("STAI"));
    departmentButton.setGraphic(MapIconModifier.getIcon("DEPT"));
    labButton.setGraphic(MapIconModifier.getIcon("LABS"));
    informationButton.setGraphic(MapIconModifier.getIcon("INFO"));
    conferenceButton.setGraphic(MapIconModifier.getIcon("CONF"));
    exitButton.setGraphic(MapIconModifier.getIcon("EXIT"));
    retailButton.setGraphic(MapIconModifier.getIcon("RETL"));
    serviceButton.setGraphic(MapIconModifier.getIcon("SERV"));
    bedButton.setGraphic(MapIconModifier.getIcon("Bed"));
    xrayButton.setGraphic(MapIconModifier.getIcon("Xray"));
    pumpButton.setGraphic(MapIconModifier.getIcon("Infusion Pump"));
    reclinerButton.setGraphic(MapIconModifier.getIcon("Recliner"));
  }

  @FXML
  private void onScroll(ScrollEvent event) {
    if (!inButton) {
      if (event.getDeltaY() != 0) {
        zoomSlider.setValue(zoomSlider.getValue() + event.getDeltaY());
      }
      event.consume();
    }
  }

  public void initializeScroll() {
    mapScale = new SimpleDoubleProperty(1.0);
    scrollPane.addEventFilter(ScrollEvent.SCROLL, this::onScroll);
    mapGroup.scaleXProperty().bind(mapScale);
    mapGroup.scaleYProperty().bind(mapScale);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
  }

  public void iniSlider() {
    zoomSlider
        .valueProperty()
        .addListener(
            new ChangeListener<Number>() {
              public void changed(
                  ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                if (old_val != new_val) {
                  // Change scale of map based on slider position.
                  double sliderValue = new_val.doubleValue();
                  // double d = SettlementMapPanel.DEFAULT_SCALE;
                  double newScale = 1 + sliderValue * 0.01;
                  mapScale.setValue(newScale);
                }
              }
            });
  }

  @FXML
  void popUpReset() throws SQLException, IOException {
    if (UserType.getUserType() != "admin") {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.show();
    } else {
      MapTableHolder.wipeMap();
      MapPopUp.popUpReset(table, iconPane);
      MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
    }
  }

  @FXML
  void popUpAdd() throws SQLException, IOException {
    if (UserType.getUserType() != "admin") {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.show();
    } else {
      MapPopUp.popUpAdd(table, iconPane);
      MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
    }
  }

  @FXML
  void popUpSave() throws SQLException, IOException {
    if (UserType.getUserType() != "admin") {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.show();
    } else {
      MapPopUp.popUpSave();
      MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
    }
  }

  @FXML
  void openHistory() throws SQLException, IOException {
    if (UserType.getUserType() != "admin") {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.show();
    } else {
      MapPopUp.openHistory();
      MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
    }
  }

  @FXML
  void openFullTable() throws SQLException, IOException {
    MapPopUp.openFullTable();
    MapTableHolder.loadMap(table, iconPane, infoBox, locationField);
  }
  // START show functions
  public void showPatient() {
    MapIconModifier.showPatient();
  }

  public void showStorage() {
    MapIconModifier.showStorage();
  }

  public void showDirty() {
    MapIconModifier.showDirty();
  }

  public void showHallway() {
    MapIconModifier.showHallway();
  }

  public void showElev() {
    MapIconModifier.showElev();
  }

  public void showRestroom() {
    MapIconModifier.showRestroom();
  }

  public void showStair() {
    MapIconModifier.showStair();
  }

  public void showDepartment() {
    MapIconModifier.showDepartment();
  }

  public void showLab() {
    MapIconModifier.showLab();
  }

  public void showInformation() {
    MapIconModifier.showInformation();
  }

  public void showConference() {
    MapIconModifier.showConference();
  }

  public void showExit() {
    MapIconModifier.showExit();
  }

  public void showRetail() {
    MapIconModifier.showRetail();
  }

  public void showService() {
    MapIconModifier.showService();
  }

  public void showBad() {
    MapIconModifier.showBad();
  }

  public void showXray() {
    MapIconModifier.showXray();
  }

  public void showPump() {
    MapIconModifier.showPump();
  }

  public void showRecliner() {
    MapIconModifier.showRecliner();
  }

  public void showIcon() {
    MapIconModifier.showIcon(showIconButton);
  }

  public void setUpNode() {
    floorNodeList.setSpacing(10);
    menuNodeList.setSpacing(10);
    alertNodeList.setSpacing(10);
    menuNodeList.setRotate(180);
    floorNodeList.setRotate(180);
    alertNodeList.setRotate(180);
    AGlobalMethods.setCircleButton(openFloorMenu, 55);
    openFloorMenu.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToF5, 40);
    changeToF5.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToF4, 40);
    changeToF4.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToF3, 40);
    changeToF3.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToF2, 40);
    changeToF2.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToF1, 40);
    changeToF1.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToL1, 40);
    changeToL1.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(changeToL2, 40);
    changeToL2.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(openMenu, 55);
    openMenu.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    openMenu.setGraphic(MapIconModifier.getIcon("menu"));
    AGlobalMethods.setCircleButton(addButton, 40);
    addButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    addButton.setGraphic(MapIconModifier.getIcon("add"));
    AGlobalMethods.setCircleButton(saveButton, 40);
    saveButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    saveButton.setGraphic(MapIconModifier.getIcon("save"));
    AGlobalMethods.setCircleButton(loadButton, 40);
    loadButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    loadButton.setGraphic(MapIconModifier.getIcon("load"));
    AGlobalMethods.setCircleButton(tableButton, 40);
    tableButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    tableButton.setGraphic(MapIconModifier.getIcon("table"));
    AGlobalMethods.setCircleButton(historyButton, 40);
    historyButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    historyButton.setGraphic(MapIconModifier.getIcon("history"));
    AGlobalMethods.setCircleButton(alertButton, 55);
    alertButton.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButton1, 40);
    alertButton1.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButton2, 40);
    alertButton2.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButton3, 40);
    alertButton3.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButton4, 40);
    alertButton4.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButton5, 40);
    alertButton5.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButtonL1, 40);
    alertButtonL1.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
    AGlobalMethods.setCircleButton(alertButtonL2, 40);
    alertButtonL2.getStyleClass().addAll(ANIMATED_OPTION_BUTTON, ANIMATED_OPTION_SUB_BUTTON);
  }
}
