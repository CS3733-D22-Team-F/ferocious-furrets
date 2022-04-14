package edu.wpi.cs3733.D22.teamF.boundary.requests;

import java.sql.SQLException;

public interface IRequestController {

  public void submit() throws SQLException;

  public void reset();
  // public void cancel();

}
