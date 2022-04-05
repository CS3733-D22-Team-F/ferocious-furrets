/**
 * DAO for the Medical Equipmend Service Request DB with the necessary add, delete and update
 * functions
 *
 * @version 1.0
 */
package edu.wpi.furious_furrets.entities.request.deliveryRequest.equipmentDeliveryRequest;

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
public class MedDelReqDAOImpl implements MedDelReqDAO {

  Connection conn = DatabaseManager.getConn();
  private final ArrayList<MedDelReq> requests = new ArrayList<>();
  private final ArrayList<MedDelReq> updatedRequests = new ArrayList<MedDelReq>();
  private final ArrayList<String> reqIDs = new ArrayList<String>();

  /** Constructor */
  public MedDelReqDAOImpl() {}

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
                MedDelReqDAOImpl.class.getResourceAsStream(
                    "/edu/wpi/furious_furrets/MedEquipReq.csv"),
                StandardCharsets.UTF_8));
    String lineText = null;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");
      String reqID = data[0];
      String equipmentID = data[1];
      String nodeID = data[2];
      String assignedEmployeeID = data[3];
      String requesterEmployeeID = data[4];
      String status = data[5];
      // String longName = data[4];
      MedDelReq l =
          new MedDelReq(
              reqID,
              nodeID,
              assignedEmployeeID,
              requesterEmployeeID,
              status,
              "Delivery",
              "Equipment",
              equipmentID);
      requests.add(l);
      reqIDs.add(l.getReqID());
    }
    Statement stm = conn.createStatement();
    DatabaseMetaData databaseMetadata = conn.getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "MEDEQUIPREQ", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE MEDEQUIPREQ");
    }
    stm.execute(
        // TODO update the foreign key constraints for employee and nodeID
        // TODO update status constraint when status is decided
        "CREATE TABLE medEquipReq (reqID varchar(16) PRIMARY KEY, equipmentID varchar(16), nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16))"); // FOREIGN KEY (employeeID) REFERENCES Employee(EmployeeID))
    for (MedDelReq currentReq : requests) {
      stm.execute(currentReq.generateInsertStatement());
    }
  }

  public ArrayList<MedDelReq> getAllRequests() throws SQLException {
    updateDatabase();
    return requests;
  }

  public void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String equipmentID)
      throws SQLException {
    Statement stm = DatabaseManager.getConn().createStatement();
    requests.add(
        new MedDelReq(
            reqID,
            nodeID,
            assignedEmpID,
            requesterEmpID,
            status,
            "Delivery",
            "Equipment",
            equipmentID));
    reqIDs.add(reqID);
    String add =
        "INSERT INTO MEDSERVREQ values ("
            + "'"
            + reqID
            + "', '"
            + equipmentID
            + "', '"
            + nodeID
            + "', '"
            + assignedEmpID
            + "', '"
            + requesterEmpID
            + "', '"
            + status
            + "')";
    System.out.println(add);
    stm.execute(add);
    // updateDatabase();
  }

  public void deleteRequest(MedDelReq deletedObject) throws SQLException {
    for (MedDelReq currentReq : requests) {
      if (deletedObject.equals(currentReq)) {
        requests.remove(currentReq);
        System.out.println("found and removed :)");
        break;
      }
    }
    updateDatabase();
  }

  public void updateRequest(
      MedDelReq updatingRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String requestedEquipmentID)
      throws SQLException {
    MedDelReq newReq =
        new MedDelReq(
            reqID,
            nodeID,
            assignedEmpID,
            requesterEmpID,
            status,
            "Delivery",
            "Equipment",
            requestedEquipmentID);
    for (MedDelReq currentReq : requests) {
      if (updatingRequest.getReqID().equals(currentReq.getReqID())) {
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

    Statement stm = conn.createStatement();
    String q = "SELECT * FROM Locations";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      for (MedDelReq currentReq : requests) {
        // some sort of checker.....
      }
    }
  }

  public void requestsFromRSET(ResultSet rset) throws SQLException {
    while (rset.next()) {
      String reqID = rset.getString("reqID");
      String equipmentID = rset.getString("equipmentID");
      String nodeID = rset.getString("nodeID");
      String assignedEmpID = rset.getString("assignedEmployeeID");
      String requesterEmpID = rset.getString("requesterEmployeeID");
      String status = rset.getString("status");
      // String longName = rset.getString("longName");
      MedDelReq newR =
          new MedDelReq(
              reqID,
              nodeID,
              assignedEmpID,
              requesterEmpID,
              status,
              "Delivery",
              "Equipment",
              equipmentID);
      updatedRequests.add(newR);
    }
  }

  public void saveRequestToCSV() {

    String csvName = "src/main/resources/edu/wpi/furious_furrets/MedEquipReq.csv";

    Statement stm = null;
    try {
      stm = conn.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      for (String id : reqIDs) {
        ResultSet rset;
        rset = stm.executeQuery("SELECT * FROM MEDSERVREQ WHERE REQID = '" + id + "'");

        requestsFromRSET(rset);

        rset.close();
        File newCSV = new File(csvName);
        FileWriter fw = new FileWriter(csvName);
        fw.write("reqID, nodeID, employee, status\n");
        for (MedDelReq l : updatedRequests) {
          fw.write(
              l.getReqID()
                  + ","
                  + l.getRequestedEquipmentID()
                  + ","
                  + l.getNodeID()
                  + ","
                  + l.getAssignedEmpID()
                  + ","
                  + l.getRequesterEmpID()
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
