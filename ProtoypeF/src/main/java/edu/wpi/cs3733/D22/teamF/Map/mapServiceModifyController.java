package edu.wpi.cs3733.D22.teamF.Map;

import edu.wpi.cs3733.D22.teamF.*;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.locTempHolder;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class mapServiceModifyController implements Initializable {

  @FXML private Button cancel;

  @FXML private Label currentNode;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentNode.setText(locTempHolder.getLocation().getLongName());
  }

  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }
  /**
   * @throws SQLException
   * @throws IOException
   *     <p>Submit turns the fields of the Add Location window into a new Location object then
   *     passes that object to the LocationDAOImpl class to add to the database
   */
  public void submit() throws SQLException, IOException {
    String reqID = locTempHolder.getLocation().getLongName(); // request ID store in Long Name
    boolean isMedicine = false;
    String cmd = "";
    ResultSet rset =
        DatabaseManager.runQuery("SELECT * FROM MEDICINEREQUEST WHERE REQID = '" + reqID + "'");
    if (rset.next()) isMedicine = true;
    if (isMedicine) {
      cmd = String.format("UPDATE MEDICINEREQUEST SET status = 'done' WHERE reqID = '%s'", reqID);
      MedicineRequest.backUpDatabase(
          "src/main/resources/apiCSV/medicine.csv", "src/main/resources/apiCSV/employees.csv");
    } else {
      cmd = String.format("UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '%s'", reqID);
    }
    MapIconModifier.deleteIcon(locTempHolder.getLocation());
    DatabaseManager.runStatement(cmd);
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }
}
