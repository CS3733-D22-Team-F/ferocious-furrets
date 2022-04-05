package edu.wpi.furious_furrets;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import edu.wpi.furious_furrets.database.DatabaseInitializer;
import edu.wpi.furious_furrets.database.Location;
import edu.wpi.furious_furrets.database.LocationsDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapAddController implements Initializable {

  @FXML ChoiceBox<String> nodeBox;

  @FXML private TextField floorField;
  @FXML private TextField xField;
  @FXML private TextField yField;
  @FXML private TextField longField;
  @FXML private TextField shortField;

  @FXML private Button cancel;

  @FXML private JFXCheckBox isModify;

  private Connection connection = DatabaseInitializer.getConnection().getDbConnection();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<String> temp = new ArrayList<>();
    temp.add("PATI - Patient Room");
    temp.add("STOR - Equipment Storage Room");
    temp.add("DIRT - Dirty Equipment Pickup Locations");
    temp.add("HALL - Hallway");
    temp.add("ELEV - Elevator");
    temp.add("REST - Restroom");
    temp.add("STAI - Staircase");
    temp.add("DEPT - Medical Departments, Clinics, and Waiting Room Areas");
    temp.add("LABS - Labs, Imaging Centers, and Medical Testing Areas");
    temp.add("INFO - Information Desks, Security Desks, Lost and Dound");
    temp.add("CONF - Conference Room");
    temp.add("EXIT - Exit/Entrance");
    temp.add(
        "RETL - Shops, Food, Pay Phone, Areas That Provide Non-medical\n"
            + "Services For Immediate Payment");
    temp.add(
        "SERV - Hospital Non-medical Services, Interpreters, Shuttles, Spiritual Library,\n"
            + "Patient Financial, etc.");
    nodeBox.getItems().addAll(temp);
    nodeBox.setValue("PATI - Patient Room");
  }

  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /** reset fields in add window */
  public void reset() {
    nodeBox.setValue("PATI - Patient Room");
    floorField.clear();
    xField.clear();
    yField.clear();
    longField.clear();
    shortField.clear();
  }

  /**
   * @param event is the click of the submit button
   * @throws SQLException
   * @throws IOException
   *     <p>Submit turns the fields of the Add Location window into a new Location object then
   *     passes that object to the LocationDAOImpl class to add to the database
   */
  public void submit(ActionEvent event) throws SQLException, IOException {

    if (nodeBox.getValue() != null
        && floorField.getText() != null
        && xField.getText() != null
        && yField.getText() != null
        && longField.getText() != null
        && shortField.getText() != null) {
      try {
        String nID =
            generateNodeID(
                nodeBox.getValue(),
                floorField.getText(),
                Integer.parseInt(xField.getText()),
                Integer.parseInt(yField.getText()));
        System.out.println(nID);
        Location l =
            new Location(
                nID,
                Integer.parseInt(xField.getText()),
                Integer.parseInt(yField.getText()),
                floorField.getText(),
                "Tower",
                nodeBox.getValue().substring(0, 4),
                longField.getText(),
                shortField.getText());
        DatabaseManager.getLdao().addLocation(l);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @param nodeType
   * @param floor
   * @param x
   * @param y
   * @return
   * @throws SQLException
   * @throws IOException
   *     <p>Algorithm to create primary key nodeID for Location object following naming standards
   *     specified
   */
  public String generateNodeID(String nodeType, String floor, int x, int y)
      throws SQLException, IOException {
    String nNodeType = nodeType.substring(0, 4);
    String nFloor = floor;
    int roomNum = 1;
    String rNum;
    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();

    Statement stm = DatabaseInitializer.getConnection().dbConnection.createStatement();
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

  public void popUpTracker() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("mapTrackerPage.fxml"));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    xField.setText(coordTempHolder.getxValue());
    yField.setText(coordTempHolder.getyValue());
    floorField.setText(coordTempHolder.getFloorValue());
  }

  //  public void addLocation(
  //      String nodeType, String floor, int x, int y, String longN, String shortN, boolean
  // isModify)
  //      throws SQLException, IOException {
  //
  //    String nID = generateNodeID(nodeType, floor, x, y);
  //    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
  //    Statement stm = DatabaseManager.getConn().createStatement();
  //
  //    Location l = new Location(nID, x, y, floor, "Tower", nodeType, longN, shortN);
  //    ArrayList<Location> nLocations = LDAOImpl.getAllLocations();
  //    ArrayList<String> nLocationID = LDAOImpl.getCsvIDS();
  //    nLocations.add(l);
  //    nLocationID.add(l.getNodeID());
  //    LDAOImpl.setUpdatedLocations(nLocations);
  //    LDAOImpl.setCsvIDS(nLocationID);
  //
  //    String nNodeType = nodeType.substring(0, 4);
  //
  //    String cmd =
  //        "INSERT INTO Locations (nodeID, XCOORD, YCOORD, floor, building, nodeType, longName,
  // shortName) "
  //            + "VALUES ( '"
  //            + nID
  //            + "', "
  //            + x
  //            + ", "
  //            + y
  //            + ", '"
  //            + floor
  //            + "', "
  //            + "'Tower'"
  //            + ", '"
  //            + nNodeType
  //            + "', '"
  //            + longN
  //            + "', '"
  //            + shortN
  //            + "')";
  //    System.out.println(cmd);
  //    stm.execute(cmd);
  //    stm.close();
  //  }
  //
  //  public void deleteLocation(String oldID) throws SQLException, IOException {
  //    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
  //    ArrayList<String> csvIDS = LDAOImpl.getCsvIDS();
  //    ArrayList<Location> nLocation = LDAOImpl.getUpdatedLocations();
  //    csvIDS.remove(oldID);
  //    Statement stm = DatabaseInitializer.getConnection().dbConnection.createStatement();
  //    String cmd = "Delete from Locations where nodeID = '" + oldID + "'";
  //    stm.execute(cmd);
  //    csvIDS.remove(oldID);
  //    nLocation.remove(oldID);
  //    LDAOImpl.setCsvIDS(csvIDS);
  //    LDAOImpl.setUpdatedLocations(nLocation);
  //    stm.close();
  //  }
  //
  //  public void updateLocation(
  //      String nodeID,
  //      String nodeType,
  //      String floor,
  //      int x,
  //      int y,
  //      String longN,
  //      String shortN,
  //      boolean isModify)
  //      throws SQLException, IOException {
  //    deleteLocation(nodeID);
  //    addLocation(nodeType, floor, x, y, longN, shortN, isModify);
  //  }
  //
  //  public void locationsFromRSET(ResultSet rset) throws SQLException {
  //    while (rset.next()) {
  //      String nodeID = rset.getString("nodeID");
  //      String longName = rset.getString("LongName");
  //      String shortName = rset.getString("ShortName");
  //      int xCoord = rset.getInt("xcoord");
  //      int yCoord = rset.getInt("ycoord");
  //      String floor = rset.getString("floor");
  //      String building = rset.getString("building");
  //      String nodeType = rset.getString("nodeType");
  //      Location newL =
  //          new Location(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  //      LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
  //      ArrayList<Location> nLocation = LDAOImpl.getUpdatedLocations();
  //      nLocation.add(newL);
  //      LDAOImpl.setUpdatedLocations(nLocation);
  //    }
  //  }
  //
  //  public void saveLocationToCSV() {
  //
  //    String csvName = "src/main/resources/edu/wpi/furious_furrets/TowerLocationsNew.csv";
  //
  //    Statement stm = null;
  //    try {
  //      stm = connection.createStatement();
  //    } catch (SQLException e) {
  //      e.printStackTrace();
  //    }
  //
  //    LocationsDAOImpl LDAOImpl = DatabaseManager.getLdao();
  //    ArrayList<String> csvIDS = LDAOImpl.getCsvIDS();
  //    ArrayList<Location> updatedLocations = LDAOImpl.getUpdatedLocations();
  //
  //    try {
  //      for (String id : csvIDS) {
  //        ResultSet rset;
  //        rset = stm.executeQuery("SELECT * FROM Locations WHERE nodeID = '" + id + "'");
  //
  //        locationsFromRSET(rset);
  //
  //        rset.close();
  //        File newCSV = new File(csvName);
  //        FileWriter fw = new FileWriter(csvName);
  //        fw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName\n");
  //        for (Location l : updatedLocations) {
  //          fw.write(
  //              l.getNodeID()
  //                  + ","
  //                  + l.getXcoord()
  //                  + ","
  //                  + l.getYcoord()
  //                  + ","
  //                  + l.getFloor()
  //                  + ","
  //                  + l.getBuilding()
  //                  + ","
  //                  + l.getNodeType()
  //                  + ","
  //                  + l.getLongName()
  //                  + ","
  //                  + l.getShortName()
  //                  + "\n");
  //        }
  //        fw.close();
  //      }
  //    } catch (SQLException e) {
  //      e.printStackTrace();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
}
