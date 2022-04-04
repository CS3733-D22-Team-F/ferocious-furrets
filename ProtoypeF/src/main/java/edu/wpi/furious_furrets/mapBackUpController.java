package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import edu.wpi.furious_furrets.database.Location;
import edu.wpi.furious_furrets.database.LocationsDAOImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class mapBackUpController implements Initializable {

  @FXML private TextField idField;
  @FXML Button cancel;
  @FXML Button reset;
  @FXML Button delete;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void reset() {
    idField.clear();
  }

  public void backUp(ActionEvent event) throws SQLException, IOException {

    if (idField.getText() != null) {
      try {
        backUpToCSV(idField.getText());
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
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

  public void backUpToCSV(String filename) throws SQLException, IOException {

    // String csvName = "src/main/resources/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser

    Statement stm = null;
    try {
      stm = DatabaseManager.getConn().createStatement();
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
        File newCSV = new File(filename);
        FileWriter fw = new FileWriter(filename);
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
