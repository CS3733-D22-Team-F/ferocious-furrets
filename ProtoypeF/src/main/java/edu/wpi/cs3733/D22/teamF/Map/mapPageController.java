package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapLocationModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapPopUp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapTableHolder;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class mapPageController implements Initializable {
  private double scaleValue = 1;
  private double zoomIntensity = 0.02;

  @FXML ImageView mapHolder;

  @FXML public TableView<Location> table;

  @FXML TitledPane legend;

  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> longName;

  @FXML ScrollPane scrollPane;
  @FXML Group mapGroup;
  @FXML AnchorPane iconPane;

  @FXML JFXButton showIconButton;

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

  Image F1 = new Image(getClass().getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(getClass().getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(getClass().getResourceAsStream("FloorMap/Floor3.jpg"));
  Image F4 = new Image(getClass().getResourceAsStream("FloorMap/Floor4.jpg"));
  Image F5 = new Image(getClass().getResourceAsStream("FloorMap/Floor5.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

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
    legend.setExpanded(false);

    ArrayList<Location> nLocations = null;
    ArrayList<equipment> eList = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
      eLocations = MapTableHolder.equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      loadTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    nLocations.addAll(eLocations);

    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    loadAllLegend();
    changeToF1();
  }

  /** change map to floor 1, same for f2, f3, l1, l2 */
  public void changeToF1() {
    mapHolder.setImage(F1);
    MapLocationModifier.currentFloor = "1";
    MapIconModifier.showFloorIcon("1");
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    MapLocationModifier.currentFloor = "2";
    MapIconModifier.showFloorIcon("2");
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    MapLocationModifier.currentFloor = "3";
    MapIconModifier.showFloorIcon("3");
  }

  public void changeToF4() {
    mapHolder.setImage(F4);
    MapLocationModifier.currentFloor = "4";
    MapIconModifier.showFloorIcon("4");
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    MapLocationModifier.currentFloor = "5";
    MapIconModifier.showFloorIcon("5");
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    MapLocationModifier.currentFloor = "L1";
    MapIconModifier.showFloorIcon("L1");
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    MapLocationModifier.currentFloor = "L2";
    MapIconModifier.showFloorIcon("L2");
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

  private Node outerNode(Node node) {
    Node outerNode = centeredNode(node);
    outerNode.setOnScroll(
        e -> {
          e.consume();
          onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
        });
    return outerNode;
  }

  private Node centeredNode(Node node) {
    VBox vBox = new VBox(node);
    vBox.setAlignment(Pos.CENTER);
    return vBox;
  }

  private void updateScale() {
    iconPane.setScaleX(scaleValue);
    iconPane.setScaleY(scaleValue);
  }

  private void onScroll(double wheelDelta, Point2D mousePoint) {
    double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

    Bounds innerBounds = mapGroup.getLayoutBounds();
    Bounds viewportBounds = scrollPane.getViewportBounds();

    // calculate pixel offsets from [0, 1] range
    double valX = scrollPane.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
    double valY = scrollPane.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

    scaleValue = scaleValue * zoomFactor;
    updateScale();
    scrollPane.layout(); // refresh ScrollPane scroll positions & target bounds

    // convert target coordinates to zoomTarget coordinates
    Point2D posInZoomTarget = iconPane.parentToLocal(mapGroup.parentToLocal(mousePoint));

    // calculate adjustment of scroll position (pixels)
    Point2D adjustment =
        iconPane
            .getLocalToParentTransform()
            .deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

    // convert back to [0, 1] range
    // (too large/small values are automatically corrected by ScrollPane)
    Bounds updatedInnerBounds = mapGroup.getBoundsInLocal();
    scrollPane.setHvalue(
        (valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
    scrollPane.setVvalue(
        (valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
  }

  public void initializeScroll() {
    scrollPane.setContent(outerNode(mapGroup));

    scrollPane.setPannable(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToHeight(true); // center
    scrollPane.setFitToWidth(true); // center

    updateScale();
  }

  public void zoomIn() {
    onScroll(2.5, new Point2D(scrollPane.getWidth() / 2, scrollPane.getHeight() / 2));
  }

  public void zoomOut() {
    onScroll(-2.5, new Point2D(scrollPane.getWidth() / 2, scrollPane.getHeight() / 2));
  }

  @FXML
  void popUpReset() throws SQLException, IOException {
    MapPopUp.popUpReset();
    loadMap();
  }

  @FXML
  void popUpAdd() throws SQLException, IOException {
    MapPopUp.popUpAdd();
    loadMap();
  }

  @FXML
  void popUpDelete() throws SQLException, IOException {
    MapPopUp.popUpDelete();
    loadMap();
  }

  @FXML
  void popUpSave() throws SQLException, IOException {
    MapPopUp.popUpSave();
    loadMap();
  }

  @FXML
  void openHistory() throws SQLException, IOException {
    MapPopUp.openHistory();
    loadMap();
  }

  @FXML
  void openFullTable() throws SQLException, IOException {
    MapPopUp.openFullTable();
    loadMap();
  }

  @FXML
  private void homePage(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }

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

  public void wipeMap() throws SQLException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    oldLocs.addAll(eLocations);
    for (Location loc : oldLocs) {
      MapIconModifier.deleteIcon(loc);
    }
  }

  public void displayMap() throws SQLException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();

    ArrayList<Location> nLocations = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eLocations = MapTableHolder.equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    nLocations.addAll(eLocations);
    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    MapIconModifier.showAllIcon();
  }

  public void loadTable() throws SQLException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    oldLocs.addAll(eLocations);
    ObservableList<Location> nlocationList = FXCollections.observableList(oldLocs);
    table.setItems(nlocationList);
  }

  public void loadMap() throws SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    loadTable();
    wipeMap();
    displayMap();
  }

  /**
   * Add an icon to the map at a location node to provide a graphical representation of the location
   *
   * @param location
   * @throws FileNotFoundException
   */
  public void addIcon(Location location) throws FileNotFoundException, SQLException {
    JFXButton newButton = new JFXButton("", MapIconModifier.getIcon(location.getNodeType()));
    newButton.setPrefSize(20, 20);
    newButton.setMinSize(20, 20);
    newButton.setMaxSize(20, 20);
    newButton.setOnAction(
        e -> {
          if (MapIconModifier.locationIconList.containsValue(newButton)) {
            Location lo =
                new ArrayList<>(
                        MapIconModifier.getKeysByValue(MapIconModifier.locationIconList, newButton))
                    .get(0);
            try {
              MapPopUp.popUpModify(lo);
              loadMap();
            } catch (IOException | SQLException ex) {
              ex.printStackTrace();
            }
          }
        });
    double x =
        (location.getXcoord() / 1070.0) * 790; // change the image resolution to pane resolution
    double y = (location.getYcoord() / 856.0) * 630;
    newButton.setLayoutX(x);
    newButton.setLayoutY(y);
    iconPane.getChildren().add(newButton);
    MapIconModifier.locationIconList.put(location, newButton);
  }

  @FXML
  public void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }

  public void showAll() {
    MapIconModifier.showAllIcon();
  }
}
