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

public class mapDeleteController implements Initializable {

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

  public void delete(ActionEvent event) throws SQLException, IOException {

    if (idField.getText() != null) {
      try {
        deleteLocation(idField.getText());
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void deleteLocation(String oldID) throws SQLException, IOException {
    DatabaseManager.getLdao().deleteLocation(oldID);
  }
}
