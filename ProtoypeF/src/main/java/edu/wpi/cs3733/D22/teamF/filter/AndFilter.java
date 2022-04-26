package edu.wpi.cs3733.D22.teamF.filter;

import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.RequestTree;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AndFilter implements IFilter {
  private IFilter filter;
  private IFilter filter2;

  public AndFilter(IFilter filter, IFilter filter2) {
    this.filter = filter;
    this.filter2 = filter2;
  }

  @Override
  public ArrayList<RequestTree> apply(ResultSet rSet) {
    return null;
  }
}
