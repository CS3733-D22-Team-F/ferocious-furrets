package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.controllers.requests.facilitiesController;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

  private List<equipment> rawListEquip = new ArrayList<equipment>();
  private List<equipment> listOfMedEquip = new ArrayList<>(); // list

  private Floor currFloor; // the floor the observer watches
  private List<Alert> floorAlerts = new ArrayList<>();
  private static List<List<Alert>> allFloorAlerts = new ArrayList<List<Alert>>();

  private int floorEquipmentCount = 0;
  private Label totalFloorCount;
  private List<equipment> cleanList = new ArrayList<>();
  private List<equipment> dirtyList = new ArrayList<>();
  private List<equipment> podList = new ArrayList<>();
  private List<equipment> inUseList = new ArrayList<>();

  private List<equipment> cBedCount;
  private List<equipment> cInfusionPumpCount;
  private List<equipment> cRecliner;
  private List<equipment> cXRay;

  private List<equipment> dBedCount;
  private List<equipment> dInfusionPumpCount;
  private List<equipment> dRecliner;
  private List<equipment> dXRay;

  private List<equipment> pBedCount;
  private List<equipment> pInfusionPumpCount;
  private List<equipment> pRecliner;
  private List<equipment> pXRay;

  private List<equipment> iBedCount;
  private List<equipment> iInfusionPumpCount;
  private List<equipment> iRecliner;
  private List<equipment> iXRay;

  private List<Label> clabels = new ArrayList<>();
  private List<Label> dlabels = new ArrayList<>();
  private List<Label> plabels = new ArrayList<>();
  private List<Label> ilabels = new ArrayList<>();

  public DashboardObserver() {}

  public DashboardObserver(Floor floorToSet) {
    this.currFloor = floorToSet;
  }

  public List<equipment> getCleanList() {
    return cleanList;
  }

  public List<equipment> getDirtyList() {
    return dirtyList;
  }

  public List<equipment> getPodList() {
    return podList;
  }

  public List<equipment> getInUse() {
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
    for (equipment eq : rawListEquip) {
      String equipFloor = eq.getNodeID().substring(8);
      System.out.println(
          currFloor.toFloorString() + " is currFloor." + equipFloor + " is equipFloor");

      if (equipFloor.equals(currFloor.toFloorString())) {
        System.out.println(
            currFloor.toFloorString() + "Adding to medEquipList, size:  " + listOfMedEquip.size());
        listOfMedEquip.add(eq);
      }
    }
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

      if (totalFloorCount != null) {
        totalFloorCount.setText(String.valueOf(floorEquipmentCount));
      }
    }
  }

  public void updateAllAlerts() throws SQLException {
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
      System.out.println("SHOULD FIRE");
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
    rawListEquip = (List<equipment>) evt.getNewValue();

    System.out.println(this.currFloor.toFloorString() + " :observer recieved event");
    System.out.println("Raw list amount in observers: " + rawListEquip.size());

    this.setFloorFilter();
    System.out.println(
        this.currFloor.toFloorString() + " :observer filtered list: " + listOfMedEquip.size());
    this.updateLabels();

    try {
      updateAllAlerts();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * filters floor list for list of clean eqipment
   *
   * @return a list of clean equip for floor
   */
  private List<equipment> filterInClean() {

    List<equipment> cleanEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
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
  private List<equipment> filterInDirty() {
    List<equipment> dirtyEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
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
  private List<equipment> filterInPod() {
    List<equipment> podEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
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
  private List<equipment> filterInUse(
      List<equipment> list1, List<equipment> list2, List<equipment> list3) {

    List<equipment> allOtherEquip = new ArrayList<>();
    allOtherEquip.addAll(list1);
    allOtherEquip.addAll(list2);
    allOtherEquip.addAll(list3);
    List<equipment> inUseEquip = new ArrayList<>();
    for (equipment eq : listOfMedEquip) {
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

  private List<equipment> filterBeds(List<equipment> equip) {
    List<equipment> bedList = new ArrayList<>();
    for (equipment eq : equip) {
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
  private List<equipment> filterInfusionPump(List<equipment> equip) {
    List<equipment> pumpList = new ArrayList<>();
    for (equipment eq : equip) {
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
  private List<equipment> filterRecliner(List<equipment> equip) {
    List<equipment> reclinerList = new ArrayList<>();
    for (equipment eq : equip) {
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
  private List<equipment> filterXRay(List<equipment> equip) {
    List<equipment> xrayList = new ArrayList<>();
    for (equipment eq : equip) {
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
  public void checkAlerts() throws SQLException {
    floorAlerts.clear();
    String formatString = currFloor.toFloorString();
    boolean needsInfusionAlert = false;
    boolean needsBedAlert = false;

    if (dInfusionPumpCount.size() >= 10) {
      needsInfusionAlert = true;
      System.out.println(
          "Needs infusion pump alert"); /////////////////////////////////////////////////////////////
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
      System.out.println(
          "Needs infusion pump alert"); /////////////////////////////////////////////////////////////

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

  public boolean createDashboardServiceRequests(
      int floor, boolean dInfusionReqNeeded, boolean dBedReqNeeded) throws SQLException {
    System.out.println(
        "Got into service request test"); /////////////////////////////////////////////////////////////

    facilitiesController fController = new facilitiesController();
    RequestSystem facilitiesReqSystem = new RequestSystem("Facilities");
    boolean requestsUpToDate = false;
    boolean dirtyInfusionRequestExists = false;
    boolean dirtyBedsRequestExists = false;
    String dirtyEquipNodeID = "fSTOR0050" + floor;

    if (dInfusionReqNeeded) {
      // search for service request for the dirty equipment storage area from the request ID saved

      ResultSet dirtyInfusionList =
          DatabaseManager.runQuery(
              "SELECT * FROM FACILITIESREQUEST WHERE (nodeID = "
                  + dirtyEquipNodeID
                  + ") AND (accessObject = 'Clean Beds to OR Park')");
      if (dirtyInfusionList == null) { // if does not already exist in completed request
        System.out.println(
            "Needs clean infusion pumps request"); /////////////////////////////////////////////////////////////

        ArrayList<String> reqArrayString =
            generateFacilitiesRequest(fController, "Infusion", floor);
        facilitiesReqSystem.placeRequest(reqArrayString);
      }
    }
    if (dBedReqNeeded) {
      // search for service request for the dirty equipment storage area from the request ID saved
      ResultSet dirtyBedList =
          DatabaseManager.runQuery(
              "SELECT * FROM FACILITIESREQUEST WHERE (nodeID = "
                  + dirtyEquipNodeID
                  + ") AND (accessObject = 'Infusion Pumps to West Plaza')");
      if (dirtyBedList
          == null) { // if request at the dirty bed park with infusion pump specification does not
        // already exist
        System.out.println(
            "Needs clean beds request"); /////////////////////////////////////////////////////////////

        ArrayList<String> reqArrayString = generateFacilitiesRequest(fController, "Bed", floor);
        facilitiesReqSystem.placeRequest(reqArrayString);
      }
    }

    return requestsUpToDate;
  }

  private ArrayList<String> generateFacilitiesRequest(
      facilitiesController fController, String infusionOrBed, int floor) throws SQLException {
    System.out.println(
        "Got into generate request"); /////////////////////////////////////////////////////////////

    // TODO generate reqID
    String nNodeType = infusionOrBed;
    int reqNum = 0;
    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();
    if (reqNum == 0) {
      reqNum = 1;
    }
    String reqID = "f" + nNodeType + reqNum;

    // TODO generate nodeID from the dirty storage area depending on the floor (3,4,5)
    String nodeID = "fSTOR0050" + Integer.toString(floor);

    // TODO assignedEmployeeID = General Facilities
    String assignedEmployeeID = fController.employeeIDFinder("Facilities,General");

    // TODO requesterEmployeeID = General Facilities
    String requesterEmployeeID = fController.employeeIDFinder("Facilities,General");

    // TODO status = processing
    String status = "Processing";
    // TODO accessObject
    String accessObject = "";
    if (infusionOrBed.equals("Bed")) {
      accessObject = "Clean Beds to OR Park";
    } else if (infusionOrBed.equals("Infusion")) {
      accessObject = "Infusion Pumps to West Plaza";
    }

    ArrayList<String> fields = new ArrayList<>();
    fields.add(reqID);
    fields.add(nodeID);
    fields.add(assignedEmployeeID);
    fields.add(requesterEmployeeID);
    fields.add(status);
    fields.add(accessObject);
    return fields;
  }
}
