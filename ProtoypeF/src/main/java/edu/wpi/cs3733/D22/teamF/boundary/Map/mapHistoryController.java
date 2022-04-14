package edu.wpi.cs3733.D22.teamF.boundary.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.boundary.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.boundary.Map.MapComponents.MapOperation;
import edu.wpi.cs3733.D22.teamF.boundary.Map.MapComponents.mapUserHistory;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** controller for saving and recovering the map history */
public class mapHistoryController implements Initializable {
  @FXML TableView<MapOperation> table;

  @FXML TableColumn<MapOperation, String> type;
  @FXML TableColumn<MapOperation, Location> detail;

  @FXML JFXButton cancel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    type.setCellValueFactory(new PropertyValueFactory<MapOperation, String>("type"));
    detail.setCellValueFactory(new PropertyValueFactory<MapOperation, Location>("location"));

    loadList();
  }

  public void recoverLocation() throws SQLException {
    recover(table.getSelectionModel().getSelectedItem());
  }

  public void recover(MapOperation operation) throws SQLException {
    if (operation.getType().equals("add")) {
      delete(operation.getLocation());
      mapUserHistory.userHistory.remove(operation);
    } else {
      add(operation.getLocation());

      mapUserHistory.userHistory.remove(operation);
    }
    loadList();
  }

  public void add(Location l) throws SQLException {
    DatabaseManager.getLocationDAO().addLocation(l);
  }

  public void delete(Location location) throws SQLException {
    MapIconModifier.deleteIcon(location);
    DatabaseManager.getLocationDAO().deleteLocation(location.getNodeID());
  }
  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void loadList() {
    ObservableList<MapOperation> operationList =
        FXCollections.observableList(mapUserHistory.userHistory);
    table.setItems(operationList);
  }
}
