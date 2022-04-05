package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapPageController extends returnHomePage implements Initializable {

  @FXML ImageView mapHolder;

  @FXML TableView<Location> table;

  @FXML TitledPane legend;

  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> longName;

  private Connection connection = DatabaseManager.getConn();

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
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
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
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
  }

  public void popUpAdd() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapAddPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
  }

  public void popUpDelete() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapDeletePage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
  }

  public void popUpSave() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapBackUpPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  public void popUpReset() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapResetPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
    // update table view
    // LocationsDAOImpl LDAOImpl = new LocationsDAOImpl(DatabaseManager.getConn());
    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
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
}
