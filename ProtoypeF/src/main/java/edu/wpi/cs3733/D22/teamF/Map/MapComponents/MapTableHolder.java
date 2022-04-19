package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.Cache;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
  public static ArrayList<Location> equipToLocation(ArrayList<equipment> medList)
      throws SQLException {
    ArrayList<Location> returnList = new ArrayList<>();
    int x = -1;
    int y = -1;
    String floor = "";
    String specificID = "";
    String equipID = "";
    String status = "";
    for (equipment med : medList) {
      Location fetchedNode = Cache.getLocation(med.getNodeID());

      try {
        equipID = med.getEquipID();
        status = med.getStatus();
        x = fetchedNode.getXcoord();
        y = fetchedNode.getYcoord();
        floor = fetchedNode.getFloor();
      } catch (NullPointerException e) {
        System.out.println("Error couldn't get node: " + med.getNodeID());
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
  public static void loadMap(TableView<Location> table, AnchorPane iconPane)
      throws SQLException, IOException {
    wipeMap();
    loadTable(table);
    displayMap(table, iconPane);
  }

  /**
   * @param table
   * @throws SQLException
   */
  public static void loadTable(TableView<Location> table) throws SQLException, IOException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    ArrayList<Location> eLocations = MapTableHolder.equipToLocation(eList);
    ArrayList<Location> oldLocs = DatabaseManager.getLocationDAO().getAllLocationsFromDB();
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
  public static void displayMap(TableView<Location> table, AnchorPane iconPane)
      throws SQLException, IOException {
    ArrayList<equipment> eList = DatabaseManager.getMedEquipDAO().getAllEquipment();
    ArrayList<Location> nLocations = null;
    ArrayList<Location> eLocations = null;
    try {
      nLocations = DatabaseManager.getLocationDAO().getAllLocationsFromDB();
      eLocations = MapTableHolder.equipToLocation(eList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    nLocations.addAll(eLocations);
    nLocations.addAll(getAllReq());
    MapIconModifier.locationIconList.clear();
    for (Location lo : nLocations) {
      try {
        MapIconModifier.addIcon(table, iconPane, lo);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    MapIconModifier.showAllIcon();
  }

  /** @throws SQLException */
  public static ArrayList<Location> getAllReq() throws SQLException, IOException {
    ArrayList<Location> reqList = new ArrayList<>();
    reqList.addAll(getAllEquipDeli());
    reqList.addAll(getAllMedicine());
    reqList.addAll(getAllGift());
    reqList.addAll(getAllScan());
    reqList.addAll(getAllLab());
    return reqList;
  }

  /**
   * @return
   * @throws SQLException
   */
  public static ArrayList<Location> getAllScan() throws SQLException {
    ArrayList<Location> scans = new ArrayList<>();
    // scan requests
    ResultSet scanReqs = DatabaseManager.getScanRequestDAO().get();
    while (scanReqs.next()) {
      String reqID = scanReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.runQuery(
              String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "scan";
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
        System.out.println("Error couldn't get node: " + nodeID);
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
    ResultSet labReqs = DatabaseManager.getLabRequestDAO().get();
    while (labReqs.next()) {
      String reqID = labReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.runQuery(
              String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "lab";
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
        System.out.println("Error couldn't get node: " + nodeID);
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
    ResultSet giftReqs = DatabaseManager.getGiftDAO().get();
    while (giftReqs.next()) {
      String reqID = giftReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.runQuery(
              String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "gift";
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
        System.out.println("Error couldn't get node: " + nodeID);
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
    Statement stm = DatabaseManager.getConn().createStatement();
    ResultSet medicineReqs = stm.executeQuery("SELECT * FROM MEDICINEREQUEST");
    while (medicineReqs.next()) {
      String reqID = medicineReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.runQuery(
              String.format("SELECT * FROM MEDICINEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "medicine";
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
        System.out.println("Error couldn't get node: " + nodeID);
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
    ResultSet equipReqs = DatabaseManager.getMedEquipDelReqDAO().get();
    while (equipReqs.next()) {
      String reqID = equipReqs.getString("reqID");
      ResultSet reqInfo =
          DatabaseManager.runQuery(
              String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", reqID));
      String status = "";
      String nodeID = "";
      int x = -1;
      int y = -1;
      String floor = "";
      String type = "equipment";
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
        System.out.println("Error couldn't get node: " + nodeID);
      }
      equip.add(new Location(nodeID, x, y, floor, "N/A", type, reqID, status));
    }
    equipReqs.close();
    return equip;
  }
}
