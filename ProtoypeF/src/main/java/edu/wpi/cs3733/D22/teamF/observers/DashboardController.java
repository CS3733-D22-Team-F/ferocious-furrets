package edu.wpi.cs3733.D22.teamF.observers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class DashboardController implements Initializable {

    @FXML BorderPane masterPane;
    @FXML MFXTableView table;

  public void initialize(URL location, ResourceBundle resources) {

      initServiceRequestTable();

  }
  private void initServiceRequestTable() {
      MFXTableColumn<Request> reqIDColumn = new MFXTableColumn<>("RequestID", true, Comparator.comparing(Request::getReqID));
      MFXTableColumn<Request> getNodeIDColumn = new MFXTableColumn<>("NodeID", true, Comparator.comparing(Request::getNodeID));
      MFXTableColumn<Request> getAssignedEmpColumn = new MFXTableColumn<>("Assigned Employee ID", true, Comparator.comparing(Request::getAssignedEmpID));
      MFXTableColumn<Request> getRequesterEmpColumn = new MFXTableColumn<>("Requester Employee ID", true, Comparator.comparing(Request::getRequesterEmpID));
      MFXTableColumn<Request> statusColumn = new MFXTableColumn<>("Status", true, Comparator.comparing(Request::getStatus));

      reqIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(Request::getReqID));
      getNodeIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(Request::getNodeID));
      getAssignedEmpColumn.setRowCellFactory(request -> new MFXTableRowCell<>(Request::getAssignedEmpID));
      getRequesterEmpColumn.setRowCellFactory(request -> new MFXTableRowCell<>(Request::getRequesterEmpID));
      statusColumn.setRowCellFactory(request -> new MFXTableRowCell<>(Request::getStatus));

      table.getTableColumns().addAll(reqIDColumn, getNodeIDColumn, getAssignedEmpColumn, getRequesterEmpColumn, statusColumn);
      table.getFilters().addAll(
              new StringFilter<>("RequestID", Request::getReqID),
              new StringFilter<>("NodeID", Request::getNodeID),
              new StringFilter<>("Assigned Employee ID", Request::getAssignedEmpID),
              new StringFilter<>("Requester Employee ID", Request::getRequesterEmpID),
              new StringFilter<>("Status", Request::getStatus)
      );

//      table.setItems(); // INSERT OBSERVABLE ARRAYLIST OF ALL REQUEST HERE
  }

  private ObservableList<Request> getAllRequest() {
      return null;
  }
}
