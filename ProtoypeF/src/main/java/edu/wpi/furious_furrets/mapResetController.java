package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class mapResetController implements Initializable {

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
        resetFromCSV(idField.getText());
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
  public void resetFromCSV(String filename) throws SQLException, IOException {

    // String csvName = "/edu/wpi/furious_furrets/TowerLocationsBackedUp.csv";
    // TODO: Incorporate JavaFX FileChooser
    DatabaseManager.getLdao().resetMapFromCSV("/edu/wpi/furious_furrets/" + filename + ".csv");
  }
}
