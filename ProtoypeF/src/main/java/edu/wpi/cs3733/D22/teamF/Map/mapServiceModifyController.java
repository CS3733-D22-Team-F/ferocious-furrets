package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.*;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.locTempHolder;
import edu.wpi.cs3733.D22.teamF.boundary.pagecontrollers.request.modifyRequestPageController;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mapServiceModifyController implements Initializable {

  @FXML private Button cancel;
  @FXML private JFXButton modifyRequest;

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

  public void modify() throws SQLException, IOException {
    StringProperty reqType = new SimpleStringProperty((locTempHolder.getLocation().getNodeType()));
    StringProperty reqID = new SimpleStringProperty(locTempHolder.getLocation().getLongName());

    FXMLLoader fxmlLoader =
        new FXMLLoader(Fapp.class.getResource("views/request/modifyRequestPage.fxml"));
    fxmlLoader.setControllerFactory(e -> new modifyRequestPageController(reqType, reqID));

    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(fxmlLoader.load());
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }
}
