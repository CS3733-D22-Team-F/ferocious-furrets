package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IFilter {

  public ArrayList<RequestTree> apply(ResultSet rSet) throws SQLException;
}
