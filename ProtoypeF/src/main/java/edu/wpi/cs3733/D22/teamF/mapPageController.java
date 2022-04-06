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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapPageController extends returnHomePage implements Initializable {

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

  @FXML ArrayList<ArrayList<Object>> locationIconList = new ArrayList<>();

  Image F1 = new Image(getClass().getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(getClass().getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(getClass().getResourceAsStream("FloorMap/Floor3.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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

    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    table.setItems(nlocationList);

    nLocations.addAll(eLocations);
    changeToF1();

    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  void openFullTable(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("fullLocationPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

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
  }

  public void popUpAdd() throws IOException, SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    Parent root = FXMLLoader.load(getClass().getResource("mapAddPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    for (Location loc : oldLocs) {
      deleteIcon(loc.getNodeID());
    }
    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  public void popUpDelete() throws IOException, SQLException {
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocations();
    Parent root = FXMLLoader.load(getClass().getResource("mapDeletePage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();

    for (Location loc : oldLocs) {
      deleteIcon(loc.getNodeID());
    }

    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);

    for (Location lo : nLocations) {
      try {
        addIcon(lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  public void popUpSave() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapBackUpPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

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
    for (Location loc : oldLocs) {
      deleteIcon(loc.getNodeID());
    }
    ArrayList<Location> nLocations = null;

    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();

      eLocations = equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
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
   * @param location
   * @throws FileNotFoundException
   */
  public void addIcon(Location location) throws FileNotFoundException {
    JFXButton newButton = new JFXButton("", getIcon(location.getNodeType()));
    newButton.setPrefSize(25, 25);
    newButton.setMinSize(25, 25);
    newButton.setMaxSize(25, 25);
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
    temp.add(location.getNodeID());
    temp.add(newButton);
    locationIconList.add(temp);
  }

  public void addnewIcon(Location location) throws FileNotFoundException {
    JFXButton newButton = new JFXButton("", getIcon(location.getNodeType()));
    newButton.setPrefSize(25, 25);
    newButton.setMinSize(25, 25);
    newButton.setMaxSize(25, 25);
    double x =
        (location.getXcoord() / 790) * 1070; // change the image resolution to pane resolution
    double y = (location.getYcoord() / 630) * 856;
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
    temp.add(location.getNodeID());
    temp.add(newButton);
    locationIconList.add(temp);
  }

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
}
