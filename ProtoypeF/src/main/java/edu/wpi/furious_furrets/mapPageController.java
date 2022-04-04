package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import edu.wpi.furious_furrets.database.DatabaseInitializer;
import edu.wpi.furious_furrets.database.Location;
import edu.wpi.furious_furrets.database.LocationsDAOImpl;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapPageController extends returnHomePage implements Initializable {

  @FXML ImageView mapHolder;

  @FXML TableView<Location> table;

  @FXML TableColumn<Location, String> nodeID;
  @FXML TableColumn<Location, Integer> xcoord;
  @FXML TableColumn<Location, Integer> ycoord;
  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> nodeType;
  @FXML TableColumn<Location, String> longName;
  @FXML TableColumn<Location, String> shortName;

  private Connection connection = DatabaseInitializer.getConnection().getDbConnection();

  Image F1 = new Image(getClass().getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(getClass().getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(getClass().getResourceAsStream("FloorMap/Floor3.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    xcoord.setCellValueFactory(new PropertyValueFactory<Location, Integer>("xcoord"));
    ycoord.setCellValueFactory(new PropertyValueFactory<Location, Integer>("ycoord"));
    Floor.setCellValueFactory(new PropertyValueFactory<Location, String>("Floor"));
    Building.setCellValueFactory(new PropertyValueFactory<Location, String>("Building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                LocationsDAOImpl.class.getResourceAsStream(
                    "/edu/wpi/furious_furrets/TowerLocations.csv"),
                StandardCharsets.UTF_8));

    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
    ArrayList<Location> nLocations = null;
    try {
      nLocations = LDAOImpl.getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
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
    popupwindow.showAndWait();
  }

  public void popUpDelete() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapDeletePage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
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
  }

  public String generateNodeID(String nodeType, String floor, int x, int y)
      throws SQLException, IOException {
    String nNodeType = nodeType;
    String nFloor = floor;
    int roomNum = 0;
    String rNum;
    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();

    Statement stm = DatabaseManager.initalizeDatabaseManager().getConn().createStatement();
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
    return nID;
  }

  public void locationsFromRSET(ResultSet rset) throws SQLException {
    while (rset.next()) {
      String nodeID = rset.getString("nodeID");
      String longName = rset.getString("LongName");
      String shortName = rset.getString("ShortName");
      int xCoord = rset.getInt("xcoord");
      int yCoord = rset.getInt("ycoord");
      String floor = rset.getString("floor");
      String building = rset.getString("building");
      String nodeType = rset.getString("nodeType");
      Location newL =
          new Location(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
      LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
      ArrayList<Location> nLocation = LDAOImpl.getUpdatedLocations();
      nLocation.add(newL);
      LDAOImpl.setUpdatedLocations(nLocation);
    }
  }

  public void saveLocationToCSV() {

    String csvName = "src/main/resources/edu/wpi/furious_furrets/TowerLocationsNew.csv";

    Statement stm = null;
    try {
      stm = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
    ArrayList<String> csvIDS = LDAOImpl.getCsvIDS();
    ArrayList<Location> updatedLocations = LDAOImpl.getUpdatedLocations();

    try {
      for (String id : csvIDS) {
        ResultSet rset;
        rset = stm.executeQuery("SELECT * FROM Locations WHERE nodeID = '" + id + "'");

        locationsFromRSET(rset);

        rset.close();
        File newCSV = new File(csvName);
        FileWriter fw = new FileWriter(csvName);
        fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
        for (Location l : updatedLocations) {
          fw.write(
              l.getNodeID()
                  + ","
                  + l.getXcoord()
                  + ","
                  + l.getYcoord()
                  + ","
                  + l.getFloor()
                  + ","
                  + l.getBuilding()
                  + ","
                  + l.getNodeType()
                  + ","
                  + l.getLongName()
                  + ","
                  + l.getShortName()
                  + "\n");
        }
        fw.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
