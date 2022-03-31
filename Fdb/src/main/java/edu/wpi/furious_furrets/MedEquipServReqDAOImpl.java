/**
 * DAO for the Medical Equipmend Service Request DB with the necessary add, delete and update functions
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipServReqDAOImpl implements MedEquipServReqDAO {

  private Connection connection;
  private ArrayList<MedEquipServReq> requests = new ArrayList<>();
  private ArrayList<Location> updatedRequests = new ArrayList<Location>();
  private ArrayList<String> reqIDs = new ArrayList<String>();

  /**
   * Constructor that takes in a Connection object to the DB
   * @param dbConnection
   */
  public MedEquipServReqDAOImpl(Connection dbConnection) {
    this.connection = dbConnection;
  }

  //  public void connectDatabase() throws SQLException, NoSuchElementException {
  //
  //    try {
  //      Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
  //    } catch (ClassNotFoundException e) {
  //      System.out.println("Driver not found");
  //      e.printStackTrace();
  //    }
  //
  //    System.out.println("Driver registered");
  //    connection = null;
  //
  //    try {
  //      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true"); // CONNECT TO
  // DATABASE
  //      assert (connection != null);
  //      initTable();
  //
  //    } catch (SQLException e) {
  //      System.out.println("Connection failed");
  //      e.printStackTrace();
  //      return;
  //    }
  //
  //    System.out.println("Derby connection established");
  //    connection.close();
  //  }

  /**
   *<p>An ArrayList of all the service requests in the embedded CSV file. Provided as a data structure for
   *    * the Java objects made from the embedded CSV file. The ArrayList is used to create a SQL table
   *    * by generating INSERT INTO statements.
   *    @Design The method
   *    * creates a SQL statement, and checks to see if the service request table has been created already. If
   *    * it has, DROP the table and create a new one, just create a new one if it doesn't exist
   *    * already.
   * @throws SQLException
   * @throws IOException
   */
  public void initTable() throws SQLException, IOException {
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                Main.class.getResourceAsStream("/edu/wpi/furious_furrets/MedEquipReq.csv"),
                StandardCharsets.UTF_8));
    String lineText = null;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String reqID = data[0];
      String nodeID = data[1];
      String employeeID = data[2];
      int status = Integer.parseInt(data[3]);
      String longName = data[4];
      MedEquipServReq l = new MedEquipServReq(reqID, nodeID, employeeID, status, longName);
      requests.add(l);
      reqIDs.add(l.getNodeID());
    }
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
        "CREATE TABLE medServReq (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), employeeID varChar(16), status int, longName varChar(255))"); // FOREIGN KEY (employeeID) REFERENCES Employee(EmployeeID))
    for (MedEquipServReq currentReq : requests) {
      stm.execute(currentReq.generateInsertStatement());
    }
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
