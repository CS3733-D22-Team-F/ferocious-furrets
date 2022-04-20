package edu.wpi.cs3733.D22.teamF.observers;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceRequestObservable {

  private PropertyChangeSupport observers;
  private final Connection connection = DatabaseManager.getConn();
  private List<Request> serviceRequests;

  private static ServiceRequestObservable m_serviceRequestObservable =
      new ServiceRequestObservable();

  private ServiceRequestObservable() {
    observers = new PropertyChangeSupport(this);
  }

  public static ServiceRequestObservable getInstance() {
    return m_serviceRequestObservable;
  }

  /**
   * adds observers to watch FloorsObservable
   *
   * @param listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    observers.addPropertyChangeListener(listener);
  }

  /**
   * removes observers that are watching ServiceRequestObservable
   *
   * @param listener
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    observers.removePropertyChangeListener(listener);
  }

  /**
   * gets a list of all requests from database
   *
   * @return a unfiltered list of all requests
   * @throws SQLException
   */
  private List<Request> pullRequestData() throws SQLException {
    //        ArrayList<Request> allRequests = DatabaseManager.getRequestDAO();
    return null;
  }

  public void setState() throws SQLException {
    //        List<Request> newEquip =
  }
}
