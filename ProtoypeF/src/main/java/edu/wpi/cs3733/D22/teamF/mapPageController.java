package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.MedEquip;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapPageController extends returnHomePage implements Initializable {

  String currentFloor;
  String currentIcon = "";
  private double scaleValue = 0.7;
  private double zoomIntensity = 0.02;

  @FXML ImageView mapHolder;

  @FXML TableView<Location> table;

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

  @FXML ArrayList<ArrayList<Object>> locationIconList = new ArrayList<>();

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
    ArrayList<MedEquip> eList = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
      eLocations = equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    loadTable(nLocations);

    nLocations.addAll(eLocations);

    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    loadAllLegend();
    changeToF1();
  }

  /**
   * open a fullscreen view of the locations table on the right side of the page
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void openFullTable(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("fullLocationPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  @FXML
  void openHistory(ActionEvent event) throws IOException, SQLException {
    Parent root = FXMLLoader.load(getClass().getResource("mapHistoryPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    wipeMap(oldLocs);
    displayMap();
  }
  /** change map to floor 1, same for f2, f3, l1, l2 */
  public void changeToF1() {
    mapHolder.setImage(F1);
    currentFloor = "1";
    showFloorIcon("1");
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    currentFloor = "2";
    showFloorIcon("2");
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    currentFloor = "3";
    showFloorIcon("3");
  }

  public void changeToF4() {
    mapHolder.setImage(F4);
    currentFloor = "4";
    showFloorIcon("4");
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    currentFloor = "5";
    showFloorIcon("5");
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    currentFloor = "L1";
    showFloorIcon("L1");
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    currentFloor = "L2";
    showFloorIcon("L2");
  }

  /**
   * Pop up window to add a location to the map After adding a location the table, wipe all icons
   * off the map and redisplay to update with new location
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpAdd() throws IOException, SQLException {
    Parent root = FXMLLoader.load(getClass().getResource("mapAddPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    wipeMap(oldLocs);
    displayMap();
  }

  public void wipeMap(ArrayList<Location> oldLocs) throws SQLException {
    for (Location loc : oldLocs) {
      deleteIcon(loc.getNodeID());
    }
  }

  public void displayMap() throws SQLException {
    ArrayList<MedEquip> eList = null;
    eList = DatabaseManager.getMedEquipDAO().getAllEquipment();

    ArrayList<Location> nLocations = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eLocations = equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
    nLocations.addAll(eLocations);
    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    showAllIcon();
  }

  /**
   * pop up window to delete a location node After deletion from Locations table, wipe all icons and
   * redisplay to update
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpDelete() throws IOException, SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    Parent root = FXMLLoader.load(getClass().getResource("mapDeletePage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();

    wipeMap(oldLocs);

    displayMap();
  }

  /**
   * popup window to save location table to external csv file
   *
   * @throws IOException
   */
  public void popUpSave() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapBackUpPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  /**
   * popup window to reset the map from an external csv, after upating table, wipe all icons off map
   * and redisplay to update view
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpReset() throws IOException, SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    ArrayList<MedEquip> eList = null;
    eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    Parent root = FXMLLoader.load(getClass().getResource("mapResetPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
    // update table view
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    wipeMap(oldLocs);
    displayMap();
  }

  public String generateNodeID(String nodeType, String floor, int x, int y)
      throws SQLException, IOException {
    String nNodeType = nodeType;
    String nFloor = floor;
    int roomNum = 0;
    String rNum;
    LocationsDAOImpl LDAOImpl = DatabaseManager.getLocationDAO();

    Statement stm = DatabaseManager.getConn().createStatement();
    String cmd =
        "SELECT * FROM Locations WHERE nodeType = '" + nNodeType + "' AND floor = '" + nFloor + "'";
    ResultSet rset = stm.executeQuery(cmd);
    while (rset.next()) {
      roomNum++;
    }
    rset.close();
    if ((roomNum / 10.0) >= 10) {
      rNum = "" + roomNum;
    } else if ((roomNum / 10.0) >= 1) {
      rNum = "0" + roomNum;
    } else {
      rNum = "00" + roomNum;
    }

    switch (nFloor) {
      case "5":
        nFloor = "05";
        break;
      case "4":
        nFloor = "05";
        break;
      case "3":
        nFloor = "03";
        break;
      case "2":
        nFloor = "02";
        break;
      case "1":
        nFloor = "01";
        break;
      case "L1":
        nFloor = "L1";
        break;
      case "L2":
        nFloor = "L2";
        break;
      default:
        break;
    }

    String nID;
    nID = "f" + nNodeType + rNum + nFloor;
    stm.close();
    return nID;
  }

  /**
   * Add an icon to the map at a location node to provide a graphical representation of the location
   *
   * @param location
   * @throws FileNotFoundException
   */
  public void addIcon(Location location) throws FileNotFoundException, SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    JFXButton newButton = new JFXButton("", getIcon(location.getNodeType()));
    newButton.setPrefSize(25, 25);
    newButton.setMinSize(25, 25);
    newButton.setMaxSize(25, 25);
    MenuItem dItem = new MenuItem("Delete");
    MenuItem mItem = new MenuItem("Modify");
    newButton.setOnAction(
        e -> {
          for (int i = 0; i < locationIconList.size(); i++) {
            if (locationIconList.get(i).get(1).equals(newButton)) {
              Location tempLocation = (Location) locationIconList.get(i).get(0);
              try {
                popUpModify(tempLocation);
              } catch (IOException ex) {
                ex.printStackTrace();
              } catch (SQLException ex) {
                ex.printStackTrace();
              }
            }
          }
        });
    double x =
        (location.getXcoord() / 1070.0) * 790; // change the image resolution to pane resolution
    double y = (location.getYcoord() / 856.0) * 630;
    newButton.setLayoutX(x);
    newButton.setLayoutY(y);
    iconPane.getChildren().add(newButton);
    ArrayList<Object> temp = new ArrayList<Object>();
    temp.add(location);
    temp.add(newButton);
    locationIconList.add(temp);
  }

  /**
   * delete a location icon from the map
   *
   * @param nodeID
   */
  public void deleteIcon(String nodeID) {
    for (int i = 0; i < locationIconList.size(); i++) {
      if (locationIconList.get(i).get(0).equals(nodeID)) {
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
  public ImageView getIcon(String type) {
    Image image = new Image(getClass().getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
    switch (type) {
      case "PATI":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
          break;
        }
      case "STOR":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/STOR Icon.png"));
          break;
        }
      case "DIRT":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/DIRT Icon.png"));
          break;
        }
      case "HALL":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/HALL Icon.png"));
          break;
        }
      case "ELEV":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/ELEV Icon.png"));
          break;
        }
      case "REST":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/REST Icon.png"));
          break;
        }
      case "STAI":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/STAI Icon.png"));
          break;
        }
      case "DEPT":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/DEPT Icon.png"));
          break;
        }
      case "LABS":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/LABS Icon.png"));
          break;
        }
      case "INFO":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/INFO Icon.png"));
          break;
        }
      case "CONF":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/CONF Icon.png"));
          break;
        }
      case "EXIT":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/EXIT Icon.png"));
          break;
        }
      case "RETL":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/RETL Icon.png"));
          break;
        }
      case "SERV":
        {
          image = new Image(getClass().getResourceAsStream("Icons/MapIcons/SERV Icon.png"));
          break;
        }
      case "Infusion Pump":
        {
          image = new Image(getClass().getResourceAsStream("Icons/EquipmentIcons/IPMP Icon.png"));
          break;
        }
      case "Bed":
        {
          image = new Image(getClass().getResourceAsStream("Icons/EquipmentIcons/PTBD Icon.png"));
          break;
        }
      case "Recliner":
        {
          image = new Image(getClass().getResourceAsStream("Icons/EquipmentIcons/RECL Icon.png"));
          break;
        }
      case "Xray":
        {
          image = new Image(getClass().getResourceAsStream("Icons/EquipmentIcons/XRAY Icon.png"));
          break;
        }
    }
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(25);
    imageView.setFitWidth(25);
    return imageView;
  }

  /**
   * converts an equipment object to a temporary location to get the x-y coords to display icon on
   * map
   *
   * @param medList
   * @return
   * @throws SQLException
   */
  public ArrayList<Location> equipToLocation(ArrayList<MedEquip> medList) throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    for (MedEquip med : medList) {
      specificID = med.getNodeID();
      Statement stm = DatabaseManager.getConn().createStatement();
      String cmd = "SELECT * FROM Locations WHERE nodeID = '" + specificID + "'";
      ResultSet rset = stm.executeQuery(cmd);
      while (rset.next()) {
        x = rset.getInt(2);
        y = rset.getInt(3);
        floor = rset.getString(4);
      }
      rset.close();
      Location tempLocation =
          new Location(med.getNodeID(), x, y, floor, "N/A", med.getEquipType(), "Equipment", "N/A");
      returnList.add(tempLocation);
    }
    return returnList;
  }

  public void showOneIcon(String type) {
    for (ArrayList<Object> objects : locationIconList) {
      ((JFXButton) objects.get(1))
          .setVisible(
              ((Location) objects.get(0)).getNodeType().equals(type)
                  && ((Location) objects.get(0)).getFloor().equals(currentFloor));
    }
  }

  public void showIcon() {
    if (showIconButton.getText().equals("ALL ICON")) {
      showFloorIcon(currentFloor);
      showIconButton.setText("HIDE ICON");
      showIconButton.setStyle("-fx-background-color: red");
    } else if (showIconButton.getText().equals("HIDE ICON")) {
      showFloorIcon("99");
      showIconButton.setText("ALL ICON");
      showIconButton.setStyle("-fx-background-color: #062558");
    }
  }

  public void showAllIcon() {
    showFloorIcon(currentFloor);
  }

  public void showPatient() {
    showOneIcon("PATI");
    currentIcon = "PATI";
  }

  public void showStorage() {
    showOneIcon("STOR");
    currentIcon = "STOR";
  }

  public void showDirty() {
    showOneIcon("DIRT");
    currentIcon = "DIRT";
  }

  public void showHallway() {
    showOneIcon("HALL");
    currentIcon = "HALL";
  }

  public void showElev() {
    showOneIcon("ELEV");
    currentIcon = "ELEV";
  }

  public void showRestroom() {
    showOneIcon("REST");
    currentIcon = "REST";
  }

  public void showStair() {
    showOneIcon("STAI");
    currentIcon = "STAI";
  }

  public void showDepartment() {
    showOneIcon("DEPT");
    currentIcon = "DEPT";
  }

  public void showLab() {
    showOneIcon("LABS");
    currentIcon = "LABS";
  }

  public void showInformation() {
    showOneIcon("INFO");
    currentIcon = "INFO";
  }

  public void showConference() {
    showOneIcon("CONF");
    currentIcon = "CONF";
  }

  public void showExit() {
    showOneIcon("EXIT");
    currentIcon = "EXIT";
  }

  public void showRetail() {
    showOneIcon("RETL");
    currentIcon = "RETL";
  }

  public void showService() {
    showOneIcon("SERV");
    currentIcon = "SERV";
  }

  public void showBad() {
    showOneIcon("Bed");
    currentIcon = "Bed";
  }

  public void showXray() {
    showOneIcon("Xray");
    currentIcon = "Xray";
  }

  public void showPump() {
    showOneIcon("Infusion Pump");
    currentIcon = "Infusion Pump";
  }

  public void showRecliner() {
    showOneIcon("Recliner");
    currentIcon = "Recliner";
  }

  public void loadAllLegend() {
    patientRoomButton.setGraphic(getIcon("PATI"));
    storageButton.setGraphic(getIcon("STOR"));
    dirtyButton.setGraphic(getIcon("DIRT"));
    hallwayButton.setGraphic(getIcon("HALL"));
    elevButton.setGraphic(getIcon("ELEV"));
    restroomButton.setGraphic(getIcon("REST"));
    stairButton.setGraphic(getIcon("STAI"));
    departmentButton.setGraphic(getIcon("DEPT"));
    labButton.setGraphic(getIcon("LABS"));
    informationButton.setGraphic(getIcon("INFO"));
    conferenceButton.setGraphic(getIcon("CONF"));
    exitButton.setGraphic(getIcon("EXIT"));
    retailButton.setGraphic(getIcon("RETL"));
    serviceButton.setGraphic(getIcon("SERV"));
    bedButton.setGraphic(getIcon("Bed"));
    xrayButton.setGraphic(getIcon("Xray"));
    pumpButton.setGraphic(getIcon("Infusion Pump"));
    reclinerButton.setGraphic(getIcon("Recliner"));
  }

  public void showFloorIcon(String floor) {
    for (ArrayList<Object> objects : locationIconList) {
      ((JFXButton) objects.get(1)).setVisible(((Location) objects.get(0)).getFloor().equals(floor));
    }
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

  public void popUpModify(Location location) throws IOException, SQLException {
    nodeTempHolder.setLocation(location);
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    Parent root = FXMLLoader.load(getClass().getResource("mapModifyPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    wipeMap(oldLocs);
    displayMap();
  }

  public void loadTable(ArrayList<Location> nLocations) {
    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    table.setItems(nlocationList);
  }
}
