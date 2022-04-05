package edu.wpi.furious_furrets;

import com.jfoenix.controls.JFXButton;
import edu.wpi.furious_furrets.controllers.general.DatabaseManager;
import edu.wpi.furious_furrets.entities.location.Location;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class fullLocationController implements Initializable {

  @FXML TableView<Location> table;

  @FXML TableColumn<Location, String> nodeID;
  @FXML TableColumn<Location, Integer> xcoord;
  @FXML TableColumn<Location, Integer> ycoord;
  @FXML TableColumn<Location, String> Floor;
  @FXML TableColumn<Location, String> Building;
  @FXML TableColumn<Location, String> nodeType;
  @FXML TableColumn<Location, String> longName;
  @FXML TableColumn<Location, String> shortName;

  @FXML JFXButton cancel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    xcoord.setCellValueFactory(new PropertyValueFactory<Location, Integer>("xcoord"));
    ycoord.setCellValueFactory(new PropertyValueFactory<Location, Integer>("ycoord"));
    Floor.setCellValueFactory(new PropertyValueFactory<Location, String>("Floor"));
    Building.setCellValueFactory(new PropertyValueFactory<Location, String>("Building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));

    ArrayList<Location> nLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ObservableList<Location> locationList = FXCollections.observableList(nLocations);
    table.setItems(locationList);
  }

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }
}
