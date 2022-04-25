package edu.wpi.cs3733.D22.teamF.observers;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class DashboardController implements Initializable {
    @FXML BorderPane masterPane;
    @FXML MFXTableView table;

  public void initialize(URL location, ResourceBundle resources) {

  }
  private void initServiceRequestTable() {
      MFXTableColumn<Request> reqIDColumn = new MFXTableColumn<>("RequestID", true, Comparator.comparing(Request::getReqID));
      MFXTableColumn<Request> getNodeIDColumn = new MFXTableColumn<>("NodeID", true, Comparator.comparing(Request::getNodeID));
      MFXTableColumn<Request> getAssignedEmpColumn = new MFXTableColumn<>("Assigned Employee ID", true, Comparator.comparing(Request::getAssignedEmpID));
      MFXTableColumn<Request> getRequesterEmpColumn = new MFXTableColumn<>("Requester Employee ID", true, Comparator.comparing(Request::getRequesterEmpID));
      MFXTableColumn<Request> statusColumn = new MFXTableColumn<>("Status", true, Comparator.comparing(Request::getStatus));

      reqIDColumn.setRowCellFactory(Request -> new MFXTableRowCell<>(Request::getReqID));



  }
}
