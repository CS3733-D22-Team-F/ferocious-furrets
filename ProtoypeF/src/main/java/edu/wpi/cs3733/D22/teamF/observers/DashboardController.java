package edu.wpi.cs3733.D22.teamF.observers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class DashboardController implements Initializable {

  ObservableList<RequestObject> requests = FXCollections.observableArrayList();

  @FXML BorderPane masterPane;
  @FXML MFXTableView table;
  @FXML Label clock;

  List<Label> cleanLabels = new ArrayList<>();
  List<Label> dirtyLabels = new ArrayList<>();
  List<Label> podLabels = new ArrayList<>();
  List<Label> inUseLabels = new ArrayList<>();
  List<Label> equipCountLabels = new ArrayList<>();

  static Floor currentFloor = Floor.FL3;

  @FXML JFXNodesList layoutAlerts;
  @FXML TextField floorSelect;

  @FXML Label cBed;
  @FXML Label cXRay;
  @FXML Label cRecliner;
  @FXML Label cInfusionPump;

  @FXML Label dBed;
  @FXML Label dXRay;
  @FXML Label dRecliner;
  @FXML Label dInfusionPump;

  @FXML Label pBed;
  @FXML Label pXRay;
  @FXML Label pRecliner;
  @FXML Label pInfusionPump;

  @FXML Label iBed;
  @FXML Label iXRay;
  @FXML Label iRecliner;
  @FXML Label iInfusionPump;

  @FXML Label outstandingServiceRequestLabel;
  @FXML Label welcomeLabel;

  @FXML JFXButton updateTableButton;

  CategoryAxis xAxis = new CategoryAxis();
  NumberAxis yAxis = new NumberAxis();
  @FXML StackedBarChart stackedBarChart = new StackedBarChart(xAxis, yAxis);

  XYChart.Series cleanEquipment = new XYChart.Series();
  XYChart.Series dirtyEquipment = new XYChart.Series();

  //  @FXML Label ll2Count;
  //  @FXML Label ll1Count;
  //  @FXML Label fl1Count;
  //  @FXML Label fl2Count;
  //  @FXML Label fl3Count;
  //  @FXML Label fl4Count;
  //  @FXML Label fl5Count;

  Time clockWork = new Time(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()));
  Timeline timeline =
      new Timeline(
          new KeyFrame(
              Duration.seconds(1),
              event -> {
                clockWork.onSecondPass();
                clock.setText(clockWork.getCurrentTime());
                setLabels();
              }));

  public void initialize(URL location, ResourceBundle resources) {
    updateRequest();
    initServiceRequestTable();

    cleanLabels.add(cBed);
    cleanLabels.add(cInfusionPump);
    cleanLabels.add(cRecliner);
    cleanLabels.add(cXRay);

    dirtyLabels.add(dBed);
    dirtyLabels.add(dInfusionPump);
    dirtyLabels.add(dRecliner);
    dirtyLabels.add(dXRay);

    podLabels.add(pBed);
    podLabels.add(pInfusionPump);
    podLabels.add(pRecliner);
    podLabels.add(pXRay);

    inUseLabels.add(iBed);
    inUseLabels.add(iInfusionPump);
    inUseLabels.add(iRecliner);
    inUseLabels.add(iXRay);

    FloorWatchManager.getInstance()
        .setLabelsToUpdate(cleanLabels, dirtyLabels, podLabels, inUseLabels);
    FloorWatchManager.getInstance().setEquipCountLabels(equipCountLabels);
    setLabels();
    setAlerts();

    try {
      FloorObservable.getInstance().setState();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    initializeChart();
  }

  private void updateOutstandingSRLabel() {
    outstandingServiceRequestLabel.setText(
        String.format("There are %s outstanding Service Requests", requests.size()));
  }

  private void initServiceRequestTable() {
    MFXTableColumn<RequestObject> reqIDColumn =
        new MFXTableColumn<>("RequestID", true, Comparator.comparing(RequestObject::getReqID));
    MFXTableColumn<RequestObject> getNodeIDColumn =
        new MFXTableColumn<>("NodeID", true, Comparator.comparing(RequestObject::getNodeID));
    MFXTableColumn<RequestObject> getAssignedEmpColumn =
        new MFXTableColumn<>(
            "Assigned Employee", true, Comparator.comparing(RequestObject::getAssignedEmpFullName));
    MFXTableColumn<RequestObject> getRequesterEmpColumn =
        new MFXTableColumn<>(
            "Requester Employee ID",
            true,
            Comparator.comparing(RequestObject::getRequesterEmpFullName));
    MFXTableColumn<RequestObject> statusColumn =
        new MFXTableColumn<>("Status", true, Comparator.comparing(RequestObject::getStatus));

    reqIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getReqID));
    getNodeIDColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getNodeID));
    getAssignedEmpColumn.setRowCellFactory(
        request -> new MFXTableRowCell<>(RequestObject::getAssignedEmpFullName));
    getRequesterEmpColumn.setRowCellFactory(
        request -> new MFXTableRowCell<>(RequestObject::getRequesterEmpFullName));
    statusColumn.setRowCellFactory(request -> new MFXTableRowCell<>(RequestObject::getStatus));

    table
        .getTableColumns()
        .setAll(
            reqIDColumn,
            getNodeIDColumn,
            getAssignedEmpColumn,
            getRequesterEmpColumn,
            statusColumn);
    table
        .getFilters()
        .setAll(
            new StringFilter<>("RequestID", RequestObject::getReqID),
            new StringFilter<>("NodeID", RequestObject::getNodeID),
            new StringFilter<>("Assigned Employee", RequestObject::getAssignedEmpFullName),
            new StringFilter<>("Requester Employee", RequestObject::getRequesterEmpFullName),
            new StringFilter<>("Status", RequestObject::getStatus));

    table.setItems(requests); // INSERT OBSERVABLE ARRAYLIST OF ALL REQUEST HERE
  }

  public void updateTable(ActionEvent event) {
    updateRequest();
  }

  private void updateRequest() {

    ObservableList<RequestObject> newRequests = FXCollections.observableArrayList();

    try {
      ResultSet allRequestRset = DatabaseManager.getInstance().getRequestDAO().get();

      while (allRequestRset.next()) {
        String reqID = allRequestRset.getString("reqID");
        String nodeID = allRequestRset.getString("nodeID");
        String assignedEmployeeID = allRequestRset.getString("assignedEmployeeID");
        String requesterEmployeeID = allRequestRset.getString("requesterEmployeeID");
        String status = allRequestRset.getString("status");

        newRequests.add(
            new RequestObject(reqID, nodeID, assignedEmployeeID, requesterEmployeeID, status));
      }

      allRequestRset.close();

    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }

    requests = newRequests;

    initServiceRequestTable();
    updateOutstandingSRLabel();
  }

  public void setFloor(Floor floorToSet) {
    currentFloor = floorToSet;
  }

  /** Increases the value of the current floor by 1 */
  public void nextFloor() {

    setFloor(currentFloor.next());
    // allFloorsObserver.setFloor(currentFloor);
    //    System.out.println(currentFloor.toInt());
    setLabels();
    // System.out.println("Next observer");
  }

  /** Increases the value of the current floor by 1 */
  public void prevFloor() {
    setFloor(currentFloor.prev());
    //    System.out.println(currentFloor + " for labels");
    // allFloorsObserver.setFloor(currentFloor);
    setLabels();
    //    System.out.println("Prev observer");

  }

  public void setAlerts() {

    layoutAlerts.getChildren().removeAll();

    List<List<Alert>> allFloorAlerts = AlertObserver.getInstance().getAllFloorAlerts();

    for (List<Alert> floorAlert : allFloorAlerts)
      for (Alert inFloorAlert : floorAlert) {
        JFXButton newAlert = new JFXButton(inFloorAlert.getMessage());
        layoutAlerts.getChildren().add(new JFXButton(inFloorAlert.getMessage()));
      }
  }

  public void readFloorInput() {
    currentFloor = currentFloor.toFloorEnum(floorSelect.getText());
    setLabels();
  }

  /** takes all labels and applies appropriate amount based current observed floor */
  public void setLabels() {

    List<DashboardObserver> floorObservers = FloorWatchManager.getInstance().getAllFloorObservers();
    //    System.out.println(currentFloor);
    //    System.out.println(
    //        "Current Observer Floor"
    //            + floorObservers.get(currentFloor.toInt()).getFloor().toFloorString());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " clean List:"
    //            + floorObservers.get(currentFloor.toInt()).getCleanList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " dirty List:"
    //            + floorObservers.get(currentFloor.toInt()).getDirtyList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " pod List:"
    //            + floorObservers.get(currentFloor.toInt()).getPodList().size());
    //    System.out.println(
    //        currentFloor.toFloorString()
    //            + " in-use List:"
    //            + floorObservers.get(currentFloor.toInt()).getInUse().size());

    List<Alert> currentAlerts = floorObservers.get(currentFloor.toInt()).getFloorAlerts();
    floorObservers.get(currentFloor.toInt()).updateLabels();
    floorSelect.setText(currentFloor.toFloorString());
    AlertObserver.getInstance().setFloorAlertCount();
  }

  private void initializeChart() {
    List<DashboardObserver> observerList = FloorWatchManager.getInstance().getAllFloorObservers();

    xAxis.setLabel("Floor");
    xAxis.getCategories().addAll("Clean", "Dirty");
    yAxis.setLabel("Equipment #s");
    yAxis.setTickUnit(1);

    stackedBarChart.setTitle("Clean and Dirty Equipment");

    cleanEquipment.setName("Clean");

    cleanEquipment
        .getData()
        .add(new XYChart.Data("F3", observerList.get(4).getFloorCleanEquipmentCount()));
    cleanEquipment
        .getData()
        .add(new XYChart.Data("F4", observerList.get(5).getFloorCleanEquipmentCount()));
    cleanEquipment
        .getData()
        .add(new XYChart.Data("F5", observerList.get(6).getFloorCleanEquipmentCount()));

    stackedBarChart.getData().add(cleanEquipment);

    dirtyEquipment.setName("Dirty");

    dirtyEquipment
        .getData()
        .add(new XYChart.Data("F3", observerList.get(4).getFloorDirtyEquipmentCount()));
    dirtyEquipment
        .getData()
        .add(new XYChart.Data("F4", observerList.get(5).getFloorDirtyEquipmentCount()));
    dirtyEquipment
        .getData()
        .add(new XYChart.Data("F5", observerList.get(6).getFloorDirtyEquipmentCount()));

    stackedBarChart.getData().add(dirtyEquipment);

    stackedBarChart.setHorizontalGridLinesVisible(false);
    stackedBarChart.setVerticalGridLinesVisible(false);
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

  public String getAssignedEmpFullName() {
    String first;
    String last;
    try {
      last = DatabaseManager.getInstance().getEmployeeDAO().empIDToLastName(this.assignedEmpID);
      first = DatabaseManager.getInstance().getEmployeeDAO().empIDToFirstName(this.assignedEmpID);
      return String.format("%s, %s", last, first);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getRequesterEmpFullName() {
    String first;
    String last;
    try {
      last = DatabaseManager.getInstance().getEmployeeDAO().empIDToLastName(this.requesterEmpID);
      first = DatabaseManager.getInstance().getEmployeeDAO().empIDToFirstName(this.requesterEmpID);
      return String.format("%s, %s", last, first);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
