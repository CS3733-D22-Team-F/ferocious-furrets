package edu.wpi.cs3733.D22.teamF.Map;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** controller for the map reset functinos including reseting fields and using the file explorer */
public class MapResetController implements Initializable {

  @FXML private TextField idField;
  @FXML Button cancel;
  @FXML Button select;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /** cancels stage */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /**
   * Opens a file chooser allowing the user to select a file from the file explorer
   *
   * @throws SQLException
   * @throws IOException
   */
  public void selectFile() throws SQLException, IOException {
    FileChooser fChoose = new FileChooser();
    fChoose.setTitle("Open CSV File");
    Stage stage = (Stage) select.getScene().getWindow();
    File file = fChoose.showOpenDialog(stage);
    //    resetFromCSVFile(file);
    if (file != null) {
      DatabaseManager.getInstance().getLocationDAO().initTable(file);
    }
    //    String path = file.getPath();
    stage.close();
  }

  /** clears fields */
  public void reset() {
    idField.clear();
  }

  /**
   * Calls method to reset map locations to locations from a user specified file
   *
   * @param event
   * @throws SQLException
   * @throws IOException
   */
  public void resetFile(ActionEvent event) throws SQLException, IOException {

    if (idField.getText() != null) {
      try {
        loadFromCSV(idField.getText());
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Calls LocationDAOImpl to reset the Locations table to a specified csv
   *
   * @param filename is a csv file that contains location nodes
   * @throws SQLException
   * @throws IOException
   */
  public void loadFromCSV(String filename) throws SQLException, IOException {

    // String csvName = "/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getInstance()
        .getLocationDAO()
        .initTable("/edu/wpi/cs3733/D22/teamF/csv/" + filename + ".csv");
  }

  /** @deprecated */
  //  public void resetFromCSVFile(File file) throws SQLException, IOException {
  //    // System.out.println(file.getPath());
  //    DatabaseManager.getLocationDAO().initTable(file);
  //  }
}
