package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import edu.wpi.furious_furrets.database.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

  public ArrayList<Location> locationsFromRSET(ResultSet rset) throws SQLException {
    ArrayList<Location> allLocations = new ArrayList<Location>();
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
      allLocations.add(newL);
    }
    return allLocations;
  }

  public void backUpToCSV(String filename) throws SQLException, IOException {

    // String csvName = "/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getLdao()
        .backUpToCSV("src/main/resources/edu/wpi/furious_furrets/" + filename + ".csv");
  }
}