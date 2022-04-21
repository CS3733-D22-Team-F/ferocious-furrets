package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** controller for the map backup */
public class MapBackUpController implements Initializable {

  @FXML private TextField idField;
  @FXML Button cancel;
  @FXML Button reset;
  @FXML Button delete;
  @FXML Button select;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /** set all fields to blank */
  public void reset() {
    idField.clear();
  }

  public void selectFile() throws SQLException, IOException {
    FileChooser fChoose = new FileChooser();
    fChoose.setTitle("Open CSV File");
    Stage stage = (Stage) select.getScene().getWindow();
    File file = fChoose.showOpenDialog(stage);
    backUpToCSVFile(file);
    //    String path = file.getPath();
    stage.close();
  }

  /**
   * calls method to back up CSV file upon clicking of button
   *
   * @param event
   * @throws SQLException
   * @throws IOException
   */
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

  /**
   * @param rset
   * @return Array List of all Locations in the Locations table
   * @throws SQLException
   */
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

  /** Take in a filename from the user and call LocationDAOImpl to back up file */
  public void backUpToCSV(String filename) throws SQLException, IOException {

    // String csvName = "/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getInstance()
        .getLocationDAO()
        .backUpToCSV("src/main/resources/edu/wpi/cs3733/D22/teamF/csv/" + filename + ".csv");
  }

  /**
   * calls Databasemanager backnup to csv
   *
   * @param file File
   * @throws SQLException
   * @throws IOException
   */
  public void backUpToCSVFile(File file) throws SQLException, IOException {

    // String csvName = "/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getInstance().getLocationDAO().backUpToCSV(file);
  }
}
