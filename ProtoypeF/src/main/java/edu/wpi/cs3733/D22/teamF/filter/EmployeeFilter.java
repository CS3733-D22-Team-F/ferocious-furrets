package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFilter implements IFilter {
  String filterBy;
  ResultSet rset;

  public EmployeeFilter(String filterBy) {
    this.filterBy = filterBy;
  }

  @Override
  public ArrayList<Object> apply(ResultSet rSet) {
    this.rset = rSet;
    return null;
  }

  public String employeeIDFinder() throws SQLException {
    String empID = "";
    String[] employeeName = filterBy.split(",");
    String last = employeeName[0].trim();
    String first = employeeName[1].trim();
    last = last.strip();
    first = first.strip();
    String cmd =
        String.format(
            "SELECT EMPLOYEEID FROM EMPLOYEE WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'",
            first, last);
    rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
  }
}
