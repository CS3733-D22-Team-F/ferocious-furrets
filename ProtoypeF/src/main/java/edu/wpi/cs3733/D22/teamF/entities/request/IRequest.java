package edu.wpi.cs3733.D22.teamF.entities.request;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for requests, allows the use of a Facade design pattern for service requests as seen in
 * RequestSystem
 */
public interface IRequest {

  public void place(ArrayList<String> fields) throws SQLException;

  public void resolve(String reqID);

  public void modify(ArrayList<String> fields);

  public void cancel(String reqID);
}
