package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class DashboardController implements Initializable {

  ObservableList<RequestObject> requests = FXCollections.observableArrayList();

  @FXML BorderPane masterPane;
  @FXML MFXTableView table;

  public void initialize(URL location, ResourceBundle resources) {
    updateRequest();
    initServiceRequestTable();
  }

  private void initServiceRequestTable() {
    MFXTableColumn<RequestObject> reqIDColumn =
        new MFXTableColumn<>("RequestID", true, Comparator.comparing(RequestObject::getReqID));
    MFXTableColumn<RequestObject> getNodeIDColumn =
        new MFXTableColumn<>("NodeID", true, Comparator.comparing(RequestObject::getNodeID));
    MFXTableColumn<RequestObject> getAssignedEmpColumn =
        new MFXTableColumn<>(
            "Assigned Employee ID", true, Comparator.comparing(RequestObject::getAssignedEmpID));
    MFXTableColumn<RequestObject> getRequesterEmpColumn =
        new MFXTableColumn<>(
            "Requester Employee ID", true, Comparator.comparing(RequestObject::getRequesterEmpID));
    MFXTableColumn<RequestObject> statusColumn =
        new MFXTableColumn<>("Status", true, Comparator.comparing(RequestObject::getStatus));

    reqIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getReqID));
    getNodeIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getNodeID));
    getAssignedEmpColumn.setRowCellFactory(
        request -> new MFXTableRowCell<>(RequestObject::getAssignedEmpID));
    getRequesterEmpColumn.setRowCellFactory(
        request -> new MFXTableRowCell<>(RequestObject::getRequesterEmpID));
    statusColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getStatus));

    table
        .getTableColumns()
        .addAll(
            reqIDColumn,
            getNodeIDColumn,
            getAssignedEmpColumn,
            getRequesterEmpColumn,
            statusColumn);
    table
        .getFilters()
        .addAll(
            new StringFilter<>("RequestID", Request::getReqID),
            new StringFilter<>("NodeID", Request::getNodeID),
            new StringFilter<>("Assigned Employee ID", Request::getAssignedEmpID),
            new StringFilter<>("Requester Employee ID", Request::getRequesterEmpID),
            new StringFilter<>("Status", Request::getStatus));

    table.setItems(requests); // INSERT OBSERVABLE ARRAYLIST OF ALL REQUEST HERE
  }

  private void updateRequest() {

    try {
      ResultSet allRequestRset = DatabaseManager.getInstance().getRequestDAO().get();

      while (allRequestRset.next()) {
        String reqID = allRequestRset.getString("reqID");
        String nodeID = allRequestRset.getString("nodeID");
        String assignedEmployeeID = allRequestRset.getString("assignedEmployeeID");
        String requesterEmployeeID = allRequestRset.getString("requesterEmployeeID");
        String status = allRequestRset.getString("status");

        requests.add(
            new RequestObject(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status));
      }

      allRequestRset.close();

    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}

class RequestObject {

  private String reqID; // id of request
  private String assignedEmpID; // Employee that is assigned the task (First name, Last name)
  private String requesterEmpID; // ID of the employee that requested the task (5 Digit int)
  private String nodeID;
  private String status;
  // TODO enum

  /**
   * @param reqID reqID
   * @param nodeID location node ID
   * @param assignedEmpID requester name
   * @param requesterEmpID requester id
   * @param status request status processing/done
   */
  public RequestObject(
      String reqID, String nodeID, String assignedEmpID, String requesterEmpID, String status) {
    this.reqID = reqID;
    this.nodeID = nodeID;
    this.assignedEmpID = assignedEmpID;
    this.requesterEmpID = requesterEmpID;
    this.status = status;
  }

  public String getReqID() {
    return reqID;
  }

  public void setReqID(String reqID) {
    this.reqID = reqID;
  }

  public String getAssignedEmpID() {
    return assignedEmpID;
  }

  public void setAssignedEmpID(String assignedEmpID) {
    this.assignedEmpID = assignedEmpID;
  }

  public String getRequesterEmpID() {
    return requesterEmpID;
  }

  public void setRequesterEmpID(String requesterEmpID) {
    this.requesterEmpID = requesterEmpID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
