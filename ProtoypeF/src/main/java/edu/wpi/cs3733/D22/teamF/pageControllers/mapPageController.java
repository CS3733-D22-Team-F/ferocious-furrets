package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import edu.wpi.cs3733.D22.teamF.homePageController;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class mapPageController implements Initializable {

  String currentFloor;

  @FXML ImageView mapHolder;

  @FXML TableView<Location> table;

  @FXML TitledPane legend;

  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> longName;

  @FXML AnchorPane iconPane1;
  @FXML AnchorPane iconPane2;
  @FXML AnchorPane iconPane3;
  @FXML AnchorPane iconPaneL1;
  @FXML AnchorPane iconPaneL2;

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

  Image F1 = new Image(homePageController.class.getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(homePageController.class.getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(homePageController.class.getResourceAsStream("FloorMap/Floor3.jpg"));
  Image L1 = new Image(homePageController.class.getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(homePageController.class.getResourceAsStream("FloorMap/Lower2.jpg"));

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
      eLocations = equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    table.setItems(nlocationList);

    nLocations.addAll(eLocations);
    changeToF1();

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
  }

  /**
   * open a fullscreen view of the locations table on the right side of the page
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void openFullTable(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplayAndWait("fullLocationPage.fxml");
  }

  /** change map to floor 1, same for f2, f3, l1, l2 */
  public void changeToF1() {
    mapHolder.setImage(F1);
    iconPane1.setVisible(true);
    iconPane1.setDisable(false);
    iconPane2.setVisible(false);
    iconPane2.setDisable(true);
    iconPane3.setVisible(false);
    iconPane3.setDisable(true);
    iconPaneL1.setVisible(false);
    iconPaneL1.setDisable(true);
    iconPaneL2.setVisible(false);
    iconPaneL2.setDisable(true);
    currentFloor = "1";
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    iconPane1.setVisible(false);
    iconPane1.setDisable(true);
    iconPane2.setVisible(true);
    iconPane2.setDisable(false);
    iconPane3.setVisible(false);
    iconPane3.setDisable(true);
    iconPaneL1.setVisible(false);
    iconPaneL1.setDisable(true);
    iconPaneL2.setVisible(false);
    iconPaneL2.setDisable(true);
    currentFloor = "2";
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    iconPane1.setVisible(false);
    iconPane1.setDisable(true);
    iconPane2.setVisible(false);
    iconPane2.setDisable(true);
    iconPane3.setVisible(true);
    iconPane3.setDisable(false);
    iconPaneL1.setVisible(false);
    iconPaneL1.setDisable(true);
    iconPaneL2.setVisible(false);
    iconPaneL2.setDisable(true);
    currentFloor = "2";
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    iconPane1.setVisible(false);
    iconPane1.setDisable(true);
    iconPane2.setVisible(false);
    iconPane2.setDisable(true);
    iconPane3.setVisible(false);
    iconPane3.setDisable(true);
    iconPaneL1.setVisible(true);
    iconPaneL1.setDisable(false);
    iconPaneL2.setVisible(false);
    iconPaneL2.setDisable(true);
    currentFloor = "L1";
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    iconPane1.setVisible(false);
    iconPane1.setDisable(true);
    iconPane2.setVisible(false);
    iconPane2.setDisable(true);
    iconPane3.setVisible(false);
    iconPane3.setDisable(true);
    iconPaneL1.setVisible(false);
    iconPaneL1.setDisable(true);
    iconPaneL2.setVisible(true);
    iconPaneL2.setDisable(false);
    currentFloor = "L2";
  }

  /**
   * Pop up window to add a location to the map After adding a location the table, wipe all icons
   * off the map and redisplay to update with new location
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpAdd() throws IOException, SQLException {
    if (UserType.userType != "admin") {
      StageManager.getInstance().setDisplayAndWait("notAvailable.fxml");
    }
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    StageManager.getInstance().setDisplayAndWait("mapAddPage.fxml");
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    wipeMap(oldLocs);
    displayMap();
  }

  public void wipeMap(ArrayList<Location> oldLocs) throws SQLException {
    for (Location loc : oldLocs) {
      deleteIcon(loc.getNodeID());
    }
  }

  public void displayMap() throws SQLException {
    ArrayList<equipment> eList = null;
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
    locationIconList.clear();
    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * pop up window to delete a location node After deletion from Locations table, wipe all icons and
   * redisplay to update
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpDelete() throws IOException, SQLException {
    if (UserType.userType != "admin") {
      //      StageManager.getInstance().setDisplayAndWait("notAvailable.fxml");
      StageManager.getInstance().setHomeScreen();
    }
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    StageManager.getInstance().setDisplayAndWait("mapDeletePage.fxml");

    wipeMap(oldLocs);

    displayMap();
  }

  /**
   * popup window to save location table to external csv file
   *
   * @throws IOException
   */
  public void popUpSave() throws IOException {
    StageManager.getInstance().setDisplayAndWait("mapBackUpPage.fxml");
  }

  /**
   * popup window to reset the map from an external csv, after upating table, wipe all icons off map
   * and redisplay to update view
   *
   * @throws IOException
   * @throws SQLException
   */
  public void popUpReset() throws IOException, SQLException {
    if (UserType.userType != "admin") {
      StageManager.getInstance().setDisplayAndWait("notAvailable.fxml");
    }
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    ArrayList<equipment> eList = null;
    eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    StageManager.getInstance().setDisplayAndWait("mapResetPage.fxml");
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
    if (UserType.userType != "admin") {
      //      StageManager.getInstance().setDisplayAndWait("notAvailable.fxml");
    }
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    JFXButton newButton = new JFXButton("", getIcon(location.getNodeType()));
    newButton.setPrefSize(25, 25);
    newButton.setMinSize(25, 25);
    newButton.setMaxSize(25, 25);
    MenuItem dItem = new MenuItem("Delete");
    MenuItem mItem = new MenuItem("Modify");
    ContextMenu options = new ContextMenu();
    options.getItems().add(dItem);
    options.getItems().add(mItem);
    newButton.setContextMenu(options);
    newButton.setOnAction(
        e -> {
          newButton.getContextMenu();
        });
    dItem.setOnAction(
        e -> {
          deleteIcon(location.getNodeID());
          try {
            DatabaseManager.getLocationDAO().deleteLocation(location.getNodeID());
            this.wipeMap(oldLocs);
            this.displayMap();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });
    double x =
        (location.getXcoord() / 1070.0) * 790; // change the image resolution to pane resolution
    double y = (location.getYcoord() / 856.0) * 630;
    newButton.setLayoutX(x);
    newButton.setLayoutY(y);
    switch (location.getFloor()) {
      case "1":
        iconPane1.getChildren().add(newButton);
        break;
      case "2":
        iconPane2.getChildren().add(newButton);
        break;
      case "3":
        iconPane3.getChildren().add(newButton);
        break;
      case "L1":
        iconPaneL1.getChildren().add(newButton);
        break;
      case "L2":
        iconPaneL2.getChildren().add(newButton);
        break;
    }
    ArrayList<Object> temp = new ArrayList<Object>();
    temp.add(location);
    temp.add(newButton);
    temp.add(location.getNodeID());
    locationIconList.add(temp);
  }

  /**
   * delete a location icon from the map
   *
   * @param nodeID
   */
  public void deleteIcon(String nodeID) {
    if (UserType.userType != "admin") {
      //      StageManager.getInstance().setDisplayAndWait("notAvailable.fxml");
    }
    for (int i = 0; i < locationIconList.size(); i++) {
      if (locationIconList.get(i).get(2).equals(nodeID)) {
        ((AnchorPane) ((JFXButton) locationIconList.get(i).get(1)).getParent())
            .getChildren()
            .remove((JFXButton) locationIconList.get(i).get(1));
        locationIconList.remove(i);
      }
    }
  }

  /**
   * Gets the correct type of icon depending on the nodeType of the location s
   *
   * @param type
   * @return
   * @throws FileNotFoundException
   */
  public ImageView getIcon(String type) {
    Image image =
        new Image(homePageController.class.getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
    switch (type) {
      case "PATI":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/PATI Icon.png"));
          break;
        }
      case "STOR":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/STOR Icon.png"));
          break;
        }
      case "DIRT":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/DIRT Icon.png"));
          break;
        }
      case "HALL":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/HALL Icon.png"));
          break;
        }
      case "ELEV":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/ELEV Icon.png"));
          break;
        }
      case "REST":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/REST Icon.png"));
          break;
        }
      case "STAI":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/STAI Icon.png"));
          break;
        }
      case "DEPT":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/DEPT Icon.png"));
          break;
        }
      case "LABS":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/LABS Icon.png"));
          break;
        }
      case "INFO":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/INFO Icon.png"));
          break;
        }
      case "CONF":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/CONF Icon.png"));
          break;
        }
      case "EXIT":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/EXIT Icon.png"));
          break;
        }
      case "RETL":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/RETL Icon.png"));
          break;
        }
      case "SERV":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream("Icons/MapIcons/SERV Icon.png"));
          break;
        }
      case "Infusion Pump":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/IPMP Icon.png"));
          break;
        }
      case "Bed":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/PTBD Icon.png"));
          break;
        }
      case "Recliner":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/RECL Icon.png"));
          break;
        }
      case "Xray":
        {
          image =
              new Image(
                  homePageController.class.getResourceAsStream(
                      "Icons/EquipmentIcons/XRAY Icon.png"));
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
  public ArrayList<Location> equipToLocation(ArrayList<equipment> medList) throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    for (equipment med : medList) {
      specificID = med.getNodeID();
      Statement stm = DatabaseManager.getConn().createStatement();
      String cmd = "SELECT * FROM Locations WHERE nodeID = '" + specificID + "'";
      ResultSet rset = stm.executeQuery(cmd);
      while (rset.next()) {
        x = rset.getInt(2);
        y = rset.getInt(3);
        floor = rset.getString(4);
      }
      stm.close();
      rset.close();
      Location tempLocation =
          new Location(med.getNodeID(), x, y, floor, "N/A", med.getEquipType(), "Equipment", "N/A");
      returnList.add(tempLocation);
    }
    return returnList;
  }

  public void showOneIcon(String type) {
    for (ArrayList<Object> objects : locationIconList) {
      if (!((Location) objects.get(0)).getNodeType().equals(type)) {
        ((JFXButton) objects.get(1)).setVisible(false);
      } else {
        ((JFXButton) objects.get(1)).setVisible(true);
      }
    }
  }

  public void showPatient() {
    showOneIcon("PATI");
  }

  public void showStorage() {
    showOneIcon("STOR");
  }

  public void showDirty() {
    showOneIcon("DIRT");
  }

  public void showHallway() {
    showOneIcon("HALL");
  }

  public void showElev() {
    showOneIcon("ELEV");
  }

  public void showRestroom() {
    showOneIcon("REST");
  }

  public void showStair() {
    showOneIcon("STAI");
  }

  public void showDepartment() {
    showOneIcon("DEPT");
  }

  public void showLab() {
    showOneIcon("LABS");
  }

  public void showInformation() {
    showOneIcon("INFO");
  }

  public void showConference() {
    showOneIcon("CONF");
  }

  public void showExit() {
    showOneIcon("EXIT");
  }

  public void showRetail() {
    showOneIcon("RETL");
  }

  public void showService() {
    showOneIcon("SERV");
  }

  public void showBad() {
    showOneIcon("Bed");
  }

  public void showXray() {
    showOneIcon("Xray");
  }

  public void showPump() {
    showOneIcon("Infusion Pump");
  }

  public void showRecliner() {
    showOneIcon("Recliner");
  }

  public void showAll() {
    for (ArrayList<Object> objects : locationIconList) {
      ((JFXButton) objects.get(1)).setVisible(true);
    }
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

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }
}
