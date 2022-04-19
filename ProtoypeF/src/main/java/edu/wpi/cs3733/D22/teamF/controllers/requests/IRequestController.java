package edu.wpi.cs3733.D22.teamF.controllers.requests;

import java.io.IOException;
import java.sql.SQLException;

public interface IRequestController {

  /**
   * submit function for button in request controllers
   *
   * @throws SQLException
   * @throws IOException
   */
  public void submit() throws SQLException, IOException;

  /** clears the fields in the request page */
  public void reset();
  // public void cancel();

  /**
   * Starts the table in the request page
   *
   * @throws SQLException
   * @throws IOException
   */
  public void startTable() throws SQLException, IOException;

  /** clears the table in the request page */
  public void clearTable();

  /**
   * generates a reqID based on fields in the request page
   *
   * @return returns String (a reqID)
   * @throws SQLException
   */
  public String generateReqID() throws SQLException, IOException;
}
