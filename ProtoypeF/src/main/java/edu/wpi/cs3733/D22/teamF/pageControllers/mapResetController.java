package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.LocationsDAOImpl;
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

public class mapResetController implements Initializable {

  @FXML private TextField idField;
  @FXML Button cancel;
  @FXML Button reset;
  @FXML Button delete;
  @FXML Button select;

  LocationsDAOImpl LDAOImpl = new LocationsDAOImpl();

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void selectFile() throws SQLException, IOException {
    FileChooser fChoose = new FileChooser();
    fChoose.setTitle("Open CSV File");
    Stage stage = (Stage) select.getScene().getWindow();
    File file = fChoose.showOpenDialog(stage);
    DatabaseManager.getLocationDAO().initTable(file);
    stage.close();
  }

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
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getLocationDAO()
        .initTable("/edu/wpi/cs3733/D22/teamF/csv/" + filename + ".csv");
  }
}
