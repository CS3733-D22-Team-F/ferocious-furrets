package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestFilter implements IFilter {

  String filterBy = "";

  public RequestFilter(String filterBy) {
    this.filterBy = filterBy;
  }

  @Override
  public ArrayList<RequestTree> apply(ResultSet ServiceRequestRset) throws SQLException {
    ArrayList<RequestTree> listReturn = new ArrayList<>();
    RequestTree rt;
    while (ServiceRequestRset.next()) {
      rt =
          new RequestTree(
              ServiceRequestRset.getString("reqID"),
              ServiceRequestRset.getString("nodeID"),
              ServiceRequestRset.getString("assignedEmployeeID"),
              ServiceRequestRset.getString("requesterEmployeeID"),
              ServiceRequestRset.getString("status"));
      listReturn.add(rt);
    }
    return listReturn;
  }

  public ArrayList<RequestTree> filterByEmpID() throws SQLException {
    String cmd =
        String.format("SELECT * FROM ServiceRequest WHERE assignedEmployeeID = '%s'", filterBy);
    ResultSet filteredReq = DatabaseManager.getInstance().runQuery(cmd);
    return apply(filteredReq);
  }

  public ArrayList<RequestTree> filterByReqID() throws SQLException {
    String reqIDs = " ";
    String cmd = String.format("SELECT * FROM SERVICEREQUEST WHERE REQID = '%s'", filterBy);
    ResultSet filteredReqs = DatabaseManager.getInstance().runQuery(cmd);
    return apply(filteredReqs);
  }

  public ArrayList<RequestTree> filterByLocationNodeID() throws SQLException {
    String cmd = String.format("SELECT * FROM SERVICEREQUEST WHERE NODEID = '%s'", filterBy);
    ResultSet filteredReqs = DatabaseManager.getInstance().runQuery(cmd);
    return apply(filteredReqs);
  }

  public ArrayList<RequestTree> filterByLocationLongName() throws SQLException, IOException {
    String cmd = String.format("SELECT * FROM LOCATIONS");
    ResultSet allLocationsRset = DatabaseManager.getInstance().runQuery(cmd);
    ArrayList<String> containsNodeIDs = new ArrayList<>();
    String currentNodeID;
    String currentLongName;
    while (allLocationsRset.next()) {
      currentNodeID = allLocationsRset.getString("nodeID");
      currentLongName = DatabaseManager.getInstance().getLocationDAO().nodeIDToName(currentNodeID);
      System.out.println("currnet long name: " + currentLongName);
      System.out.println("current filter by: " + filterBy);
      System.out.println("");
      if (currentLongName.toUpperCase().contains(filterBy.toUpperCase())) {
        containsNodeIDs.add(currentNodeID);
        System.out.println("Matched!");
      }
    }
    allLocationsRset.close();

    ResultSet allRequestRset =
        DatabaseManager.getInstance().runQuery("SELECT * FROM SERVICEREQUEST");
    ArrayList<RequestTree> allRequestList = apply(allRequestRset);
    ArrayList<RequestTree> containsRequestList = new ArrayList<>();
    allRequestRset.close();

    for (RequestTree r : allRequestList) {
      for (String currentNID : containsNodeIDs) {
        if (r.getNodeID().equals(currentNID)) {
          containsRequestList.add(r);
        }
      }
    }

    return containsRequestList;
  }

  public ArrayList<RequestTree> filterByStatus() throws SQLException {
    String cmd = String.format("SELECT * FROM SERVICEREQUEST WHERE STATUS = '%s'", filterBy);
    ResultSet filteredReqs = DatabaseManager.getInstance().runQuery(cmd);
    return apply(filteredReqs);
  }
}
