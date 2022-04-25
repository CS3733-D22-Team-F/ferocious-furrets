package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestFilter implements IFilter {

  String filterBy = "";

  public RequestFilter(String filterBy) {
    this.filterBy = filterBy;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    return null;
  }

  public ResultSet filterByEmpID() throws SQLException {
    String cmd =
        String.format("SELECT * FROM ServiceRequest WHERE assignedEmployeeID = '%s'", filterBy);
    ResultSet filteredReq = DatabaseManager.getInstance().runQuery(cmd);
    return filteredReq;
  }
}
