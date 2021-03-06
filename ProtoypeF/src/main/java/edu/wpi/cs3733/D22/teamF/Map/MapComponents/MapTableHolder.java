package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
import edu.wpi.cs3733.D22.teamB.api.Request;
import edu.wpi.cs3733.D22.teamD.API.SanitationReqAPI;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.Equipment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/** object for a holder for the map tables */
public class MapTableHolder {
  /**
   * converts an equipment object to a temporary location to get the x-y coords to display icon on
   * map
   *
   * @param medList ArrayList </equipment>
   * @return ArrayList </Location>
   * @throws SQLException
   */
  public static ArrayList<Location> equipToLocation(ArrayList<Equipment> medList)
      throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    String equipID = "";
    String status = "";
    for (Equipment med : medList) {
      Location fetchedNode = Cache.getLocation(med.getNodeID());

      try {
        equipID = med.getEquipID();
        status = med.getStatus();
        x = fetchedNode.getXcoord();
        y = fetchedNode.getYcoord();
        floor = fetchedNode.getFloor();
      } catch (NullPointerException e) {
        continue;
      }

      Location tempLocation =
          new Location(
              med.getNodeID(),
              x,
              y,
              floor,
              status,
              med.getEquipType(),
              "Equipment" + " - " + med.getEquipType() + " - " + equipID,
              equipID);
      // TODO: building = status, longName= type, shortName = equipID
      returnList.add(tempLocation);
    }
    return returnList;
  }

  /**
   * @param table
   * @param iconPane
   * @throws SQLException
   */
  public static void loadMap(
      TableView<Location> table, AnchorPane iconPane, VBox infoBox, JFXTextArea locationField)
      throws SQLException, IOException {
    wipeMap();
    loadTable(table);
    displayMap(table, iconPane, infoBox, locationField);
  }

  /**
   * @param table
   * @throws SQLException
   */
  public static void loadTable(TableView<Location> table) throws SQLException, IOException {
    ArrayList<Equipment> eList = DatabaseManager.getInstance().getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs =
        DatabaseManager.getInstance().getLocationDAO().getAllLocationsFromDB();
    oldLocs.addAll(eLocations);
    ArrayList<Location> rList = new ArrayList<>(getAllReq());
    oldLocs.addAll(rList);
    ObservableList<Location> nlocationList = FXCollections.observableList(oldLocs);
    table.setItems(nlocationList);
  }

  /** @throws SQLException */
  public static void wipeMap() throws SQLException {
    ArrayList<Location> oldLocs = new ArrayList<>(MapIconModifier.locationIconList.keySet());
    for (Location loc : oldLocs) {
      MapIconModifier.deleteIcon(loc);
    }
  }

  /**
   * @param table
   * @param iconPane
   * @throws SQLException
   */
  public static void displayMap(
      TableView<Location> table, AnchorPane iconPane, VBox infoBox, JFXTextArea locationField)
      throws SQLException, IOException {
    ArrayList<Equipment> eList = DatabaseManager.getInstance().getMedEquipDAO().getAllEquipment();
    ArrayList<Location> nLocations = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getInstance().getLocationDAO().getAllLocationsFromDB();
      eLocations = MapTableHolder.equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    nLocations.addAll(eLocations);
    nLocations.addAll(getAllReq());
    MapIconModifier.locationIconList.clear();
    for (Location lo : nLocations) {
      try {
        MapIconModifier.addIcon(table, iconPane, lo, infoBox, locationField);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    MapIconModifier.showAllIcon();
  }

  /** @throws SQLException */
  public static ArrayList<Location> getAllReq() throws SQLException, IOException {
    ArrayList<Location> reqList = new ArrayList<>();
    reqList.addAll(getAllPT());
    reqList.addAll(getAllPatientTransports());
    reqList.addAll(getAllFacilities());
    reqList.addAll(getAllSecurity());
    reqList.addAll(getAllAudioVis());
    reqList.addAll(getAllMaintenance());
    reqList.addAll(getAllMeal());
    reqList.addAll(getAllEquipDeli());
    reqList.addAll(getAllMedicine());
    reqList.addAll(getAllGift());
    reqList.addAll(getAllScan());
    reqList.addAll(getAllLab());
    reqList.addAll(getAllInternalPatientTransports());
    return reqList;
  }

  /**
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> getAllScan() throws SQLException {
    ArrayList<Location> scans = new ArrayList<>();
    // scan requests
    ResultSet scanReqs = DatabaseManager.getInstance().getScanRequestDAO().get();
    while (scanReqs.next()) {
      String reqID = scanReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Scan";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }

      Location node = Cache.getLocation(nodeID); // Location is null
      if (node == null) System.out.println("Null location dumbass");
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      scans.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    scanReqs.close();
    return scans;
  }

  /**
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> getAllLab() throws SQLException {
    ArrayList<Location> labs = new ArrayList<>();
    // lab requests
    ResultSet labReqs = DatabaseManager.getInstance().getLabRequestDAO().get();
    while (labReqs.next()) {
      String reqID = labReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Lab";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("NODEID");
      }
      Location node = Cache.getLocation(nodeID);

      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      labs.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    labReqs.close();
    return labs;
  }

  /**
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> getAllGift() throws SQLException {
    ArrayList<Location> gifts = new ArrayList<>();
    // gift requests
    ResultSet giftReqs = DatabaseManager.getInstance().getGiftDAO().get();
    while (giftReqs.next()) {
      String reqID = giftReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Gift";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      gifts.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    giftReqs.close();
    return gifts;
  }

  /**
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> getAllMedicine() throws SQLException {
    ArrayList<Location> medi = new ArrayList<>();
    // medicine requests
    Statement stm = DatabaseManager.getInstance().getDatabaseConnection().createStatement();
    ResultSet medicineReqs = stm.executeQuery("SELECT * FROM MEDICINEREQUEST");
    while (medicineReqs.next()) {
      String reqID = medicineReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM MEDICINEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Medicine";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      medi.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    medicineReqs.close();
    return medi;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllEquipDeli() throws SQLException, IOException {
    ArrayList<Location> equip = new ArrayList<>();
    // equipment delivery requests
    ResultSet equipReqs = DatabaseManager.getInstance().getMedEquipDelReqDAO().get();
    while (equipReqs.next()) {
      String reqID = equipReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Equipment";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      equip.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    equipReqs.close();
    return equip;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllPT() throws SQLException, IOException {
    ArrayList<Location> pt = new ArrayList<>();
    // equipment delivery requests
    ResultSet ptReqs = DatabaseManager.getInstance().getPTDAO().get();
    while (ptReqs.next()) {
      String reqID = ptReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "PT";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      pt.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    ptReqs.close();
    return pt;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllMeal() throws SQLException, IOException {
    ArrayList<Location> meals = new ArrayList<>();
    // equipment delivery requests
    ResultSet mealReqs = DatabaseManager.getInstance().getMealDAO().get();
    while (mealReqs.next()) {
      String reqID = mealReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Meal";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      meals.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    mealReqs.close();
    return meals;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllMaintenance() throws SQLException, IOException {
    ArrayList<Location> maint = new ArrayList<>();
    // equipment delivery requests
    ResultSet maintReqs = DatabaseManager.getInstance().getMaintenanceDAO().get();
    while (maintReqs.next()) {
      String reqID = maintReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Maintenance";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      maint.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    maintReqs.close();
    return maint;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllAudioVis() throws SQLException, IOException {
    ArrayList<Location> audiovis = new ArrayList<>();
    // equipment delivery requests
    ResultSet audvisReqs = DatabaseManager.getInstance().getAudioVisDAO().get();
    while (audvisReqs.next()) {
      String reqID = audvisReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Audio/Visual";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      audiovis.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    audvisReqs.close();
    return audiovis;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllSecurity() throws SQLException, IOException {
    ArrayList<Location> sec = new ArrayList<>();
    // equipment delivery requests
    ResultSet secReqs = DatabaseManager.getInstance().getSecurityDAO().get();
    while (secReqs.next()) {
      String reqID = secReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Security";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      sec.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    secReqs.close();
    return sec;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllFacilities() throws SQLException, IOException {
    ArrayList<Location> facil = new ArrayList<>();

    SanitationReqAPI reqAPI = new SanitationReqAPI();
    List<SanitationIRequest> sanitationRequests = reqAPI.getAllRequests();
    for (SanitationIRequest i : sanitationRequests) {
      String reqID = i.getNodeID();
      String status = i.getCleanStatus().toString();
      String nodeID = i.getRoomID();
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "Facilities";
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      facil.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    return facil;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllPatientTransports() throws SQLException, IOException {
    ArrayList<Location> pats = new ArrayList<>();
    // equipment delivery requests
    ResultSet patReqs = DatabaseManager.getInstance().getExtPatDAO().get();
    while (patReqs.next()) {
      String reqID = patReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.getInstance()
              .runQuery(String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "ExternalPatient";
      if (reqInfo.next()) {
        status = reqInfo.getString("status");
        nodeID = reqInfo.getString("nodeID");
      }
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
      }
      pats.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    patReqs.close();
    return pats;
  }

  /**
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static ArrayList<Location> getAllInternalPatientTransports()
      throws SQLException, IOException {
    ArrayList<Location> pats = new ArrayList<>();
    // equipment delivery requests
    DatabaseController dbc = new DatabaseController();
    List<Request> transportRequests = dbc.listRequests();

    String reqID = "";
    String status = "";
    String nodeID = "";
    String type = "";
    int x = -1;
    int y = -1;
    String floor = "";
    for (Request r : transportRequests) {
      reqID = r.getRequestID();
      status = r.getStatus();
      nodeID = r.getStartLocation().getNodeID();
      type = "InternalPatient";
      Location node = Cache.getLocation(nodeID);
      try {
        x = node.getXcoord();
        y = node.getYcoord();
        floor = node.getFloor();
      } catch (NullPointerException e) {
        System.out.println("Error couldn't get node: " + nodeID);
      }
      pats.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    return pats;
  }

  public static ArrayList<String> getAllLocOnNID(Location existLocation)
      throws SQLException, IOException {
    ArrayList<Equipment> eList = DatabaseManager.getInstance().getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs =
        DatabaseManager.getInstance().getLocationDAO().getAllLocationsFromDB();
    oldLocs.addAll(eLocations);
    ArrayList<Location> rList = new ArrayList<>(getAllReq());
    oldLocs.addAll(rList);
    ArrayList<String> output = new ArrayList<>();
    for (Location lo : oldLocs) {
      if (lo.getNodeID().equals(existLocation.getNodeID())) {
        String temp;
        if (MapIconModifier.getLocType(lo).equals("location")) {
          temp = lo.getLongName();
          output.add(temp);
        } else if (MapIconModifier.getLocType(lo).equals("service")
            && !lo.getShortName().equals("done")) {
          temp = lo.getNodeType() + " - " + lo.getLongName();
          output.add(temp);
        } else if (MapIconModifier.getLocType(lo).equals("equipment")) {
          temp = lo.getLongName();
          output.add(temp);
        }
      }
    }
    return output;
  }
}
