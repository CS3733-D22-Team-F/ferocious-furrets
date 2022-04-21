package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapTableHolder;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** controller for the locations full */
public class FullLocationController implements Initializable {

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

  /**
   * inits the locations
   *
   * @param location
   * @param resources
   */
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

    try {
      MapTableHolder.loadTable(table);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /** cancels the scene */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /**
   * pulls medical equipment to locations
   *
   * @param medList ArrayList </equipment>
   * @return ArrayList </Location>
   * @throws SQLException
   */
  public ArrayList<Location> equipToLocation(ArrayList<Equipment> medList) throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    for (Equipment med : medList) {
      specificID = med.getNodeID();
      Statement stm = DatabaseManager.getInstance().getDatabaseConnection().createStatement();
      String cmd = "SELECT * FROM Locations WHERE nodeID = '" + specificID + "'";
      ResultSet rset = stm.executeQuery(cmd);
      while (rset.next()) {
        x = rset.getInt(2);
        y = rset.getInt(3);
        floor = rset.getString(4);
      }
      rset.close();
      Location tempLocation =
          new Location(med.getNodeID(), x, y, floor, "N/A", med.getEquipType(), "Equipment", "N/A");
      returnList.add(tempLocation);
    }
    return returnList;
  }
}
