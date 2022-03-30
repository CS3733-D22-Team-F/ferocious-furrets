package edu.wpi.furious_furrets;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MedEquipServReqDAOImpl implements MedEquipServReqDAO {

  private Connection connection;
  private ArrayList<MedEquipServReq> requests = new ArrayList<>();

  public void connectDatabase() throws SQLException, NoSuchElementException {

    try {
      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
      e.printStackTrace();
    }

    System.out.println("Driver registered");
    connection = null;

    try {
      connection =
          DriverManager.getConnection("jdbc:derby:myDB;create=true"); // CONNECT TO DATABASE
      assert (connection != null);
      initTable();

    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    System.out.println("Derby connection established");
    connection.close();
  }

  private void initTable() throws SQLException {
    Statement stm = connection.createStatement();
    DatabaseMetaData databaseMetadata = connection.getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "MEDSERVREQ", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE MEDSERVREQ");
    }
    stm.execute(
        // TODO update the foreign key constraints for employee and nodeID
        // TODO update status constraint when status is decided
        "CREATE TABLE medServReq (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), employeeID varChar(16), status int, longName varChar(255), FOREIGN KEY (nodeID) REFERENCES Locations(nodeID))"); // FOREIGN KEY (employeeID) REFERENCES Employee(EmployeeID))
  }

  public ArrayList<MedEquipServReq> getAllRequests() throws SQLException {
    updateDatabase();
    return requests;
    //    Statement stm = connection.createStatement();
    //    String q = "SELECT * FROM Locations";
    //    ResultSet rset = stm.executeQuery(q);
    //    while (rset.next()) {
    //      requests.add(new MedEquipServReq(rset.getString("reqID"), rset.getString("nodeID"),
    // rset.getString("employeeID"), rset.getInt("status"), rset.))
    //    }
    //      return null;
  }

  // todo test
  public void addRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException {
    requests.add(new MedEquipServReq(reqID, nodeID, employeeIDofAssignedTo, status, longName));
    updateDatabase();
  }

  // TODO test
  public void deleteRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, int status, String longName)
      throws SQLException {
    MedEquipServReq theReq =
        new MedEquipServReq(reqID, nodeID, employeeIDofAssignedTo, status, longName);
    for (MedEquipServReq currentReq : requests) {
      if (theReq.equals(currentReq)) {
        requests.remove(currentReq);
        System.out.println("found and removed :)");
        break;
      }
    }
    updateDatabase();
  }

  // TODO test
  public void updateRequest(
      String old_reqID,
      String old_nodeID,
      String reqID,
      String nodeID,
      String employeeIDofAssignedTo,
      int status,
      String longName)
      throws SQLException {
    MedEquipServReq newReq =
        new MedEquipServReq(reqID, nodeID, employeeIDofAssignedTo, status, longName);
    for (MedEquipServReq currentReq : requests) {
      if (old_reqID.equals(currentReq.getReqID())) {
        requests.remove(currentReq);
        requests.add(newReq);
        System.out.println("found and replaced :)");
        break;
      }
    }
    updateDatabase();
  }

  // TODO finish
  private void updateDatabase() throws SQLException {

    Statement stm = connection.createStatement();
    String q = "SELECT * FROM Locations";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      for (MedEquipServReq currentReq : requests) {
        // some sort of checker.....
      }
    }
  }
}
