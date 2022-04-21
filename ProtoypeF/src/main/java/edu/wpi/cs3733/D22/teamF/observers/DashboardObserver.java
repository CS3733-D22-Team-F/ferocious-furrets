package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.controllers.requests.FacilitiesController;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.filter.FloorFilter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

/**
 * An observer attaches to each floor of the hospital & updates based on any change from the
 * database
 */
class DashboardObserver implements PropertyChangeListener {
  private PropertyChangeSupport alertSystem = new PropertyChangeSupport(this);
  private List<PropertyChangeListener> observerList = new ArrayList<>();

  private List<Equipment> rawListEquip = new ArrayList<Equipment>();
  private List<Equipment> listOfMedEquip = new ArrayList<>(); // list

  private Floor currFloor; // the floor the observer watches
  private List<Alert> floorAlerts = new ArrayList<>();
  private static List<List<Alert>> allFloorAlerts = new ArrayList<List<Alert>>();

  private int floorEquipmentCount = 0;
  private Label totalFloorCount;
  private List<Equipment> cleanList = new ArrayList<>();
  private List<Equipment> dirtyList = new ArrayList<>();
  private List<Equipment> podList = new ArrayList<>();
  private List<Equipment> inUseList = new ArrayList<>();

  private List<Equipment> cBedCount;
  private List<Equipment> cInfusionPumpCount;
  private List<Equipment> cRecliner;
  private List<Equipment> cXRay;

  private List<Equipment> dBedCount;
  private List<Equipment> dInfusionPumpCount;
  private List<Equipment> dRecliner;
  private List<Equipment> dXRay;

  private List<Equipment> pBedCount;
  private List<Equipment> pInfusionPumpCount;
  private List<Equipment> pRecliner;
  private List<Equipment> pXRay;

  private List<Equipment> iBedCount;
  private List<Equipment> iInfusionPumpCount;
  private List<Equipment> iRecliner;
  private List<Equipment> iXRay;

  private List<Label> clabels = new ArrayList<>();
  private List<Label> dlabels = new ArrayList<>();
  private List<Label> plabels = new ArrayList<>();
  private List<Label> ilabels = new ArrayList<>();

  public DashboardObserver() {}

  public DashboardObserver(Floor floorToSet) {
    this.currFloor = floorToSet;
  }

  public List<Equipment> getCleanList() {
    return cleanList;
  }

  public List<Equipment> getDirtyList() {
    return dirtyList;
  }

  public List<Equipment> getPodList() {
    return podList;
  }

  public List<Equipment> getInUse() {
    return this.inUseList;
  }

  public Floor getFloor() {
    return currFloor;
  }

  public List<Alert> getFloorAlerts() {
    return floorAlerts;
  }

  public static List<List<Alert>> getAllFloorAlerts() {
    return allFloorAlerts;
  }

  public void setFloor(Floor floorToSet) {
    currFloor = floorToSet;
    setFloorFilter();
  }

  /**
   * Filters all hospital equipment for the specfic floor of observer
   *
   * <p>//@param eqip
   */
  public void setFloorFilter() {
    listOfMedEquip.clear();

    FloorFilter floorFilter = new FloorFilter(currFloor);
    listOfMedEquip = floorFilter.apply(rawListEquip);
    /*
    for (Equipment eq : rawListEquip) {
      String equipFloor = eq.getNodeID().substring(8);
      //      System.out.println(
      //          currFloor.toFloorString() + " is currFloor." + equipFloor + " is equipFloor");

      if (equipFloor.equals(currFloor.toFloorString())) {
        //        System.out.println(
        //            currFloor.toFloorString() + "Adding to medEquipList, size:  " +
        // listOfMedEquip.size());
        listOfMedEquip.add(eq);
      }
    }
    *
     */
    // System.out.println(listOfMedEquip.toString());
    cleanList = filterInClean();
    dirtyList = filterInDirty();
    podList = filterInPod();
    inUseList = filterInUse(cleanList, dirtyList, podList);
  }

  public void setLabels(
      List<Label> cLabels, List<Label> dlabels, List<Label> plabels, List<Label> iLabels) {
    this.clabels = cLabels;
    this.dlabels = dlabels;
    this.plabels = plabels;
    this.ilabels = iLabels;
  }

  public void setTotalCountLabels(Label countlabel) {
    this.totalFloorCount = countlabel;
  }

  public void updateLabels() {
    if (SceneManager.getInstance().getCurrentScene().equals("views/dashboardPage.fxml")) {
      if (!clabels.isEmpty()) {
        clabels.get(0).setText(String.valueOf(cBedCount.size()));
        clabels.get(1).setText(String.valueOf(cInfusionPumpCount.size()));
        clabels.get(2).setText(String.valueOf(cRecliner.size()));
        clabels.get(3).setText(String.valueOf(cXRay.size()));
      }

      if (!dlabels.isEmpty()) {
        dlabels.get(0).setText(String.valueOf(dBedCount.size()));
        dlabels.get(1).setText(String.valueOf(dInfusionPumpCount.size()));
        dlabels.get(2).setText(String.valueOf(dRecliner.size()));
        dlabels.get(3).setText(String.valueOf(dXRay.size()));
      }

      if (!plabels.isEmpty()) {
        plabels.get(0).setText(String.valueOf(pBedCount.size()));
        plabels.get(1).setText(String.valueOf(pInfusionPumpCount.size()));
        plabels.get(2).setText(String.valueOf(pRecliner.size()));
        plabels.get(3).setText(String.valueOf(pXRay.size()));
      }

      if (!ilabels.isEmpty()) {
        ilabels.get(0).setText(String.valueOf(iBedCount.size()));
        ilabels.get(1).setText(String.valueOf(iInfusionPumpCount.size()));
        ilabels.get(2).setText(String.valueOf(iRecliner.size()));
        ilabels.get(3).setText(String.valueOf(iXRay.size()));
      }

      // if (totalFloorCount != null) {
      //    totalFloorCount.setText(String.valueOf(floorEquipmentCount));
      // }
    }
  }

  public void updateAllAlerts() throws SQLException, IOException {
    checkAlerts();
    List<List<Alert>> pastAllAlerts = getAllFloorAlerts();

    if (!(allFloorAlerts.size() == 7)) allFloorAlerts.add(floorAlerts);
    else {
      allFloorAlerts.clear();
      allFloorAlerts.add(floorAlerts);
    }
    // alertSystem.hasListeners("allFloorAlerts");
    if (alertSystem.hasListeners("allFloorAlerts")) {
      alertSystem.firePropertyChange("allFloorAlerts", false, true);
      //      System.out.println("SHOULD FIRE");
    }
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.alertSystem.addPropertyChangeListener(listener);
    observerList.add(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    this.alertSystem.removePropertyChangeListener(listener);
  }

  /**
   * receives any state changes in floor observable and applies approite filter
   *
   * @param evt the event of an observable changing
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    rawListEquip = (List<Equipment>) evt.getNewValue();

    //    System.out.println(this.currFloor.toFloorString() + " :observer recieved event");
    //    System.out.println("Raw list amount in observers: " + rawListEquip.size());

    this.setFloorFilter();
    //    System.out.println(
    //        this.currFloor.toFloorString() + " :observer filtered list: " +
    // listOfMedEquip.size());
    this.updateLabels();

    try {
      updateAllAlerts();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * filters floor list for list of clean eqipment
   *
   * @return a list of clean equip for floor
   */
  private List<Equipment> filterInClean() {

    List<Equipment> cleanEquip = new ArrayList<>();
    for (Equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Clean")) {
        cleanEquip.add(eq);
      }
    }

    cBedCount = filterBeds(cleanEquip);
    cInfusionPumpCount = filterInfusionPump(cleanEquip);
    cRecliner = filterRecliner(cleanEquip);
    cXRay = filterXRay(cleanEquip);
    return cleanEquip;
  }

  /**
   * filters floor list for dirty equipment
   *
   * @return
   */
  private List<Equipment> filterInDirty() {
    List<Equipment> dirtyEquip = new ArrayList<>();
    for (Equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Dirty")) {
        dirtyEquip.add(eq);
      }
    }
    dBedCount = filterBeds(dirtyEquip);
    dInfusionPumpCount = filterInfusionPump(dirtyEquip);
    dRecliner = filterRecliner(dirtyEquip);
    dXRay = filterXRay(dirtyEquip);
    return dirtyEquip;
  }

  /**
   * filters floor list for equipment in pods
   *
   * @return a list of equipment in pods
   */
  private List<Equipment> filterInPod() {
    List<Equipment> podEquip = new ArrayList<>();
    for (Equipment eq : listOfMedEquip) {
      Location loc = Cache.getLocation(eq.getNodeID());
      if (loc.getNodeType().equals("PATI")) {
        podEquip.add(eq);
      }
    }

    pBedCount = filterBeds(podEquip);
    pInfusionPumpCount = filterInfusionPump(podEquip);
    pRecliner = filterRecliner(podEquip);
    pXRay = filterXRay(podEquip);
    return podEquip;
  }

  /**
   * filters floor list for equipment in use
   *
   * @return a list of in use equipment
   */
  private List<Equipment> filterInUse(
      List<Equipment> list1, List<Equipment> list2, List<Equipment> list3) {

    List<Equipment> allOtherEquip = new ArrayList<>();
    allOtherEquip.addAll(list1);
    allOtherEquip.addAll(list2);
    allOtherEquip.addAll(list3);
    List<Equipment> inUseEquip = new ArrayList<>();
    for (Equipment eq : listOfMedEquip) {
      if (!allOtherEquip.contains(eq)) {
        inUseEquip.add(eq);
      }
    }

    iBedCount = filterBeds(inUseEquip);
    iInfusionPumpCount = filterInfusionPump(inUseEquip);
    iRecliner = filterRecliner(inUseEquip);
    iXRay = filterXRay(inUseEquip);
    return inUseEquip;
  }

  private List<Equipment> filterBeds(List<Equipment> equip) {
    List<Equipment> bedList = new ArrayList<>();
    for (Equipment eq : equip) {
      if (eq.getEquipType().equals("Bed")) {
        bedList.add(eq);
      }
    }
    return bedList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only Infusion Pumps
   *
   * @param equip
   * @return
   */
  private List<Equipment> filterInfusionPump(List<Equipment> equip) {
    List<Equipment> pumpList = new ArrayList<>();
    for (Equipment eq : equip) {
      if (eq.getEquipType().equals("Infusion Pump")) {
        pumpList.add(eq);
      }
    }
    return pumpList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only Recliners
   *
   * @param equip
   * @return
   */
  private List<Equipment> filterRecliner(List<Equipment> equip) {
    List<Equipment> reclinerList = new ArrayList<>();
    for (Equipment eq : equip) {
      if (eq.getEquipType().equals("Recliner")) {
        reclinerList.add(eq);
      }
    }
    return reclinerList;
  }

  /**
   * Takes in the list of equipment on the floor and returns only XRays
   *
   * @param equip
   * @return
   */
  private List<Equipment> filterXRay(List<Equipment> equip) {
    List<Equipment> xrayList = new ArrayList<>();
    for (Equipment eq : equip) {
      if (eq.getEquipType().equals("Xray")) {
        xrayList.add(eq);
      }
    }
    return xrayList;
  }

  /**
   * gets list of all alerts for a floor
   *
   * @return list of alerts
   */
  // TODO counter for alerts
  public void checkAlerts() throws SQLException, IOException {
    floorAlerts.clear();
    String formatString = currFloor.toFloorString();
    boolean needsInfusionAlert = false;
    boolean needsBedAlert = false;

    if (dInfusionPumpCount.size() >= 10) {
      needsInfusionAlert = true;
      floorAlerts.add(
          new Alert(
              this.currFloor,
              dInfusionPumpCount.size() + " dirty infusion pumps on floor " + formatString + "!"));
    }
    if (cInfusionPumpCount.size() <= 5) {
      floorAlerts.add(
          new Alert(
              this.currFloor,
              cInfusionPumpCount.size() + " clean infusion pumps on floor " + formatString + "!"));
    }
    if (dBedCount.size() >= 6) {
      needsBedAlert = true;
      floorAlerts.add(
          new Alert(
              this.currFloor, dBedCount.size() + " dirty beds on floor " + formatString + "!"));
    }
    if (needsInfusionAlert || needsBedAlert) {
      int floor = 0;
      if (currFloor.toInt() == 4) floor = 3;
      else if (currFloor.toInt() == 5) floor = 4;
      else if (currFloor.toInt() == 6) floor = 5;
      createDashboardServiceRequests(floor, needsInfusionAlert, needsBedAlert);
    }
  }

  public void createDashboardServiceRequests(
      int floor, boolean dInfusionReqNeeded, boolean dBedReqNeeded)
      throws SQLException, IOException {

    FacilitiesController fController = new FacilitiesController();
    RequestSystem facilitiesReqSystem = new RequestSystem("Facilities");
    boolean requestsUpToDate = false;
    boolean dirtyInfusionRequestExists = false;
    boolean dirtyBedsRequestExists = false;
    String dirtyEquipNodeID = "FSTOR0050" + floor;

    if (dInfusionReqNeeded) {
      // search for service request for the dirty equipment storage area from the request ID saved
      ResultSet dirtyInfusionList =
          DatabaseManager.getInstance()
              .runQuery(
                  "SELECT * FROM FACILITIESREQUEST WHERE accessObject = 'Infusion Pumps to West Plaza'");
      if (dirtyInfusionList == null
          || dirtyInfusionList.next() == false) { // if does not already exist in completed request

        ArrayList<String> reqArrayString = generateFacilitiesRequest(fController, "INF", floor);
        facilitiesReqSystem.placeRequest(reqArrayString);
        //        fController.startTable();
      } else {
        dirtyInfusionList.close();
      }
    }
    if (dBedReqNeeded) {
      // search for service request for the dirty equipment storage area from the request ID saved
      ResultSet dirtyBedList =
          DatabaseManager.getInstance()
              .runQuery(
                  "SELECT * FROM FACILITIESREQUEST WHERE accessObject = 'Clean Beds to OR Park'");
      if (dirtyBedList == null
          || dirtyBedList.next()
              == false) { // if request at the dirty bed park with infusion pump specification does
        // not
        // already exist

        ArrayList<String> reqArrayString = generateFacilitiesRequest(fController, "BED", floor);
        facilitiesReqSystem.placeRequest(reqArrayString);
        //        fController.startTable();
      } else {
        dirtyBedList.close();
      }
    }
  }

  private ArrayList<String> generateFacilitiesRequest(
      FacilitiesController fController, String infusionOrBed, int floor) throws SQLException {

    // TODO generate reqID
    String nNodeType = infusionOrBed.substring(0, 3);
    int reqNum = 0;
    ResultSet rset = DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();
    if (reqNum == 0) {
      reqNum = 1;
    }
    String reqID = "f" + nNodeType + reqNum;

    // TODO generate nodeID from the dirty storage area depending on the floor (3,4,5)
    String nodeID = "FSTOR0050" + Integer.toString(floor);

    // TODO assignedEmployeeID = General Facilities
    String assignedEmployeeID = fController.employeeIDFinder("Facilities,General");

    // TODO requesterEmployeeID = General Facilities
    String requesterEmployeeID = fController.employeeIDFinder("Facilities,General");

    // TODO status = processing
    String status = "Processing";
    // TODO accessObject
    String accessObject = "";
    if (infusionOrBed.equals("BED")) {
      accessObject = "Clean Beds to OR Park";
    } else if (infusionOrBed.equals("INF")) {
      accessObject = "Infusion Pumps to West Plaza";
    }

    ArrayList<String> fields = new ArrayList<>();
    fields.add(reqID);
    fields.add(nodeID);
    fields.add(assignedEmployeeID);
    fields.add(requesterEmployeeID);
    fields.add(status);
    fields.add(accessObject);
    //    System.out.println(fields);
    return fields;
  }
}
