package edu.wpi.cs3733.D22.teamF.controllers.requests;

import java.sql.SQLException;

public interface IRequestController {

  public void submit() throws SQLException;

  public void reset();
  // public void cancel();

}
