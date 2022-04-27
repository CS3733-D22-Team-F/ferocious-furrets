package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
import edu.wpi.cs3733.D22.teamB.api.Request;
import edu.wpi.cs3733.D22.teamD.backend.Orm;
import edu.wpi.cs3733.D22.teamD.request.IRequest;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import edu.wpi.cs3733.D22.teamF.*;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.LocTempHolder;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.ModifyRequestPageController;
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

public class MapServiceModifyController implements Initializable {

  @FXML private Button cancel;
  @FXML private JFXButton modifyRequest;

  @FXML private Label currentNode;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentNode.setText(
        LocTempHolder.getLocation().getNodeType()
            + ": "
            + LocTempHolder.getLocation().getLongName());
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
    String reqID = LocTempHolder.getLocation().getLongName(); // request ID store in Long Name
    SanitationIRequest sanitationIRequest = new SanitationIRequest();
    Orm<SanitationIRequest> requestOrm = new Orm<>(sanitationIRequest);
    DatabaseController dbc = new DatabaseController();
    boolean isMedicine = false;
    boolean isFacilities = false;
    boolean isIntTransport = false;
    String cmd = "";
    ResultSet rset =
        DatabaseManager.getInstance()
            .runQuery("SELECT * FROM MEDICINEREQUEST WHERE REQID = '" + reqID + "'");
    if (rset.next()) isMedicine = true;
    rset.close();
    if (isMedicine) {
      cmd = String.format("UPDATE MEDICINEREQUEST SET status = 'done' WHERE reqID = '%s'", reqID);
      MedicineRequest.backUpDatabase(
          "src/main/resources/apiCSV/medicine.csv", "src/main/resources/apiCSV/employees.csv");
    } else if (dbc.getRequestByID(reqID) != null) {
      isIntTransport = true;
      Request request = dbc.getRequestByID(reqID);
      Request finishedRequest =
          new Request(
              reqID,
              request.getEmployeeID(),
              request.getStart(),
              request.getFinish(),
              "Done",
              request.getPriority(),
              request.getInformation(),
              request.getTimeCreated(),
              request.getLastEdited(),
              request.getDeadline());
      dbc.update(finishedRequest);
    } else if (LocTempHolder.getLocation().getNodeType().equals("Facilities")) {
      isFacilities = true;
      SanitationIRequest request = requestOrm.get(reqID);
      request.setCleanStatus(IRequest.RequestStatus.COMPLETED);
      requestOrm.delete(reqID);
      requestOrm.add(request);
    } else {
      cmd = String.format("UPDATE SERVICEREQUEST SET status = 'done' WHERE reqID = '%s'", reqID);
    }
    MapIconModifier.deleteIcon(LocTempHolder.getLocation());
    if (!isFacilities && !isIntTransport) {
      DatabaseManager.getInstance().runStatement(cmd);
    }
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void modify() throws SQLException, IOException {
    StringProperty reqType = new SimpleStringProperty((LocTempHolder.getLocation().getNodeType()));
    StringProperty reqID = new SimpleStringProperty(LocTempHolder.getLocation().getLongName());

    FXMLLoader fxmlLoader =
        new FXMLLoader(Fapp.class.getResource("views/request/modifyRequestPage.fxml"));
    fxmlLoader.setControllerFactory(e -> new ModifyRequestPageController(reqType, reqID));

    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(fxmlLoader.load());
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }
}
