package edu.wpi.cs3733.D22.teamF.Map;

import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapOperation;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.mapUserHistory;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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

  /**
   * Call deleteLocation upon clicking of delete button
   *
   * @param event
   * @throws SQLException
   * @throws IOException
   */
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

  /**
   * Calls LocationDAOImpl to delete location
   *
   * @param oldID is the nodeID of the node to be deleted
   * @throws SQLException
   * @throws IOException
   */
  public void deleteLocation(String oldID) throws SQLException, IOException {
    ArrayList<Location> list = DatabaseManager.getLocationDAO().getAllLocations();
    for (Location l : list) {
      if (l.getNodeID().equals(oldID)) {
        Location location =
            new Location(
                l.getNodeID(),
                l.getXcoord(),
                l.getYcoord(),
                l.getFloor(),
                l.getBuilding(),
                l.getNodeType(),
                l.getLongName(),
                l.getShortName());
        mapUserHistory.userHistory.add(new MapOperation("delete", location));
      }
    }
    mapPageController mpc = new mapPageController();
    mpc.deleteIcon(oldID);
    DatabaseManager.getLocationDAO().deleteLocation(oldID);
  }
}
