package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.MedEquip;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    ArrayList<MedEquip> eList = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocations();
      eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
      for (MedEquip e : eList) {
        System.out.println(e.getNodeID());
      }
      eLocations = equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    nLocations.addAll(eLocations);
    ObservableList<Location> nlocationList = FXCollections.observableList(nLocations);
    table.setItems(nlocationList);
  }

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public ArrayList<Location> equipToLocation(ArrayList<MedEquip> medList) throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    for (MedEquip med : medList) {
      specificID = med.getNodeID();
      Statement stm = DatabaseManager.getConn().createStatement();
      String cmd = "SELECT * FROM Locations WHERE nodeID = '" + specificID + "'";
      ResultSet rset = stm.executeQuery(cmd);
      while (rset.next()) {
        x = rset.getInt(2);
        y = rset.getInt(3);
        floor = rset.getString(4);
      }
      Location tempLocation =
          new Location(med.getNodeID(), x, y, floor, "N/A", med.getEquipType(), "Equipment", "N/A");
      returnList.add(tempLocation);
    }
    return returnList;
  }
}
