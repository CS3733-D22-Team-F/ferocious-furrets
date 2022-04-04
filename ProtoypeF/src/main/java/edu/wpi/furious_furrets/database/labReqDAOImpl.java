/**
 * DAO for the Medical Equipmend Service Request DB with the necessary add, delete and update
 * functions
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets.database;

import edu.wpi.furious_furrets.controllers.entities.DatabaseManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of the MedDelReq Interface
 *
 * @see MedDelReq
 * @see MedDelReqDAO
 */
public class labReqDAOImpl implements labReqDAO {

  private final ArrayList<LabRequest> requests = new ArrayList<>();
  private final ArrayList<LabRequest> updatedRequests = new ArrayList<LabRequest>();
  private final ArrayList<String> reqIDs = new ArrayList<String>();
  private final Connection connection;

  /**
   * Constructor that takes in a Connection object to the DB
   *
   * @param dbConnection
   * @deprecated
   */
  public labReqDAOImpl(Connection dbConnection) {
    this.connection = dbConnection;
  }

  /**
   * An ArrayList of all the service requests in the embedded CSV file. Provided as a data structure
   * for * the Java objects made from the embedded CSV file. The ArrayList is used to create a SQL
   * table * by generating INSERT INTO statements. @Design The method * creates a SQL statement, and
   * checks to see if the service request table has been created already. If * it has, DROP the
   * table and create a new one, just create a new one if it doesn't exist * already.
   *
   * @throws SQLException
   * @throws IOException
   */
  public void initTable() throws SQLException, IOException {
    BufferedReader lineReader =
        new BufferedReader(
            new InputStreamReader(
                labReqDAOImpl.class.getResourceAsStream("/edu/wpi/furious_furrets/LabReq.csv"),
                StandardCharsets.UTF_8));
    String lineText = null;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String reqID = data[0];
      String nodeID = data[1];
      String employeeID = data[2];
      String status = data[3];
      String type = data[4];
      // String longName = data[4];
      LabRequest l = new LabRequest(reqID, nodeID, employeeID, status, type);
      requests.add(l);
      reqIDs.add(l.getReqID());
    }
    Statement stm = connection.createStatement();
    DatabaseMetaData databaseMetadata = connection.getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "LABSERVREQ", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE LABSERVREQ");
    }
    stm.execute(
        // TODO update the foreign key constraints for employee and nodeID
        // TODO update status constraint when status is decided
        "CREATE TABLE labServReq (reqID varchar(16) PRIMARY KEY, nodeID varchar(16), employeeID varChar(16), status varChar(16))"); // FOREIGN KEY (employeeID) REFERENCES Employee(EmployeeID))
    for (LabRequest currentReq : requests) {
      stm.execute(currentReq.generateInsertStatement());
    }
  }

  /**
   * @return
   * @throws SQLException
   * @deprecated
   */
  public ArrayList<LabRequest> getAllRequests() throws SQLException {
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

  /**
   * takes user input adds a request
   *
   * @param reqID
   * @param nodeID
   * @param employeeIDofAssignedTo
   * @param status
   * @throws SQLException
   * @see MedDelReq
   */
  public void addRequest(
      String reqID, String nodeID, String employeeIDofAssignedTo, String status, String type)
      throws SQLException {
    Statement stm = DatabaseManager.getConn().createStatement();
    requests.add(new LabRequest(reqID, nodeID, employeeIDofAssignedTo, status, type));
    reqIDs.add(reqID);
    String add =
        "INSERT INTO MEDSERVREQ values ('"
            + reqID
            + "', '"
            + nodeID
            + "', '"
            + employeeIDofAssignedTo
            + "' , '"
            + status
            + "' , '"
            + type
            + "')";
    System.out.println(add);
    stm.execute(add);
    // updateDatabase();
  }

  // TODO test

  /**
   * takes user input deletes a request
   *
   * @throws SQLException
   * @see MedDelReq
   */
  public void deleteRequest(LabRequest delReq) throws SQLException {
    // labReqest delRequest = requests.get(reqID);

    // LabRequest theReq = new LabRequest(reqID, nodeID, employeeIDofAssignedTo, status, type);
    for (LabRequest currentReq : requests) {
      if (delReq.equals(currentReq)) {
        requests.remove(currentReq);
        System.out.println("found and removed :)");
        break;
      }
    }
    updateDatabase();
  }

  // TODO test

  /**
   * @param upReq
   * @param newReqID
   * @param newNodeID
   * @param newEmployeeIDofAssignTo
   * @param newStatus
   * @param newType
   * @throws SQLException
   */
  public void updateRequest(
      LabRequest upReq,
      String newReqID,
      String newNodeID,
      String newEmployeeIDofAssignTo,
      String newStatus,
      String newType)
      throws SQLException {

    if (newReqID == "") {
      newReqID = upReq.getReqID();
    }
    if (newNodeID == "") {
      newNodeID = upReq.getNodeID();
    }
    if (newEmployeeIDofAssignTo == "") {
      newEmployeeIDofAssignTo = upReq.getEmployeeIDofAssignedTo();
    }
    if (newStatus == "") {
      newStatus = upReq.getStatus();
    }
    if (newType == "") {
      newType = upReq.getType();
    }

    LabRequest newReq =
        new LabRequest(newReqID, newNodeID, newEmployeeIDofAssignTo, newStatus, newType);
    for (LabRequest currentReq : requests) {
      if (upReq.equals(currentReq.getReqID())) {
        requests.remove(currentReq);
        requests.add(newReq);
        System.out.println("found and replaced :)");
        break;
      }
    }
    updateDatabase();
  }

  // TODO finish

  /**
   * updates the database
   *
   * @throws SQLException
   */
  private void updateDatabase() throws SQLException {

    Statement stm = connection.createStatement();
    String q = "SELECT * FROM Locations";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      for (LabRequest currentReq : requests) {
        // some sort of checker.....
      }
    }
  }

  public void requestsFromRSET(ResultSet rset) throws SQLException {
    while (rset.next()) {
      String reqID = rset.getString("reqID");
      String nodeID = rset.getString("nodeID");
      String employee = rset.getString("employeeID");
      String status = rset.getString("status");
      String type = rset.getString("type");
      // String longName = rset.getString("longName");
      LabRequest newR = new LabRequest(reqID, nodeID, employee, status, type);
      updatedRequests.add(newR);
    }
  }

  public void saveRequestToCSV() {

    String csvName = "src/main/resources/edu/wpi/furious_furrets/LabReq.csv";

    Statement stm = null;
    try {
      stm = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      for (String id : reqIDs) {
        ResultSet rset;
        rset = stm.executeQuery("SELECT * FROM LABSERVREQ WHERE REQID = '" + id + "'");

        requestsFromRSET(rset);

        rset.close();
        File newCSV = new File(csvName);
        FileWriter fw = new FileWriter(csvName);
        fw.write("reqID, nodeID, employee, status\n");
        for (LabRequest l : updatedRequests) {
          fw.write(
              l.getReqID()
                  + ","
                  + l.getNodeID()
                  + ","
                  + l.getEmployeeIDofAssignedTo()
                  + ","
                  + l.getStatus()
                  + "\n");
        }
        fw.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
