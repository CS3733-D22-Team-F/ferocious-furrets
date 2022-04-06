/**
 * DAO for the Medical Equipmend Service Request DB with the necessary add, delete and update
 * functions
 *
 * @version 1.0
 */
package edu.wpi.cs3733.D22.teamF.entities.request.medicalRequest.labRequest;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest.MedDelReq;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of the labRequestDAO Interface
 *
 * @see labRequest
 * @see labRequestDAO
 */
public class labRequestDAOImpl implements labRequestDAO {

  private final ArrayList<labRequest> requests = new ArrayList<>();
  private final ArrayList<labRequest> updatedRequests = new ArrayList<labRequest>();
  private final ArrayList<String> reqIDs = new ArrayList<String>();
  Connection conn = DatabaseManager.getConn();

  /** Constructor */
  public labRequestDAOImpl() {}

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
                labRequestDAOImpl.class.getResourceAsStream(
                    "/edu/wpi/cs3733/D22/teamF/csv/LabRequest.csv"),
                StandardCharsets.UTF_8));
    String lineText = null;
    lineReader.readLine(); // skip header line

    while ((lineText = lineReader.readLine()) != null) {
      String[] data = lineText.split(",");

      String reqID = data[0];
      String nodeID = data[1];
      String assignedEmpID = data[2];
      String requesterEmpID = data[3];
      String status = data[4];
      String reqType = data[5];
      String medicalType = data[6];
      String sampleType = data[7];

      labRequest l =
          new labRequest(
              reqID,
              nodeID,
              assignedEmpID,
              requesterEmpID,
              status,
              reqType,
              medicalType,
              sampleType);
      requests.add(l);
      reqIDs.add(l.getReqID());
    }
    Statement stm = conn.createStatement();
    DatabaseMetaData databaseMetadata = conn.getMetaData();
    ResultSet resultSet =
        databaseMetadata.getTables(
            null, null, "LABREQUEST", null); // CARTERS IF STATEMENT IF TABLE EXIST
    if (resultSet.next()) {
      stm.execute("DROP TABLE labRequest");
    }
    resultSet.close();
    stm.execute(
        // TODO update the foreign key constraints for employee and nodeID
        // TODO update status constraint when status is decided
        "CREATE TABLE labRequest (reqID varchar(16) PRIMARY KEY,nodeID varchar(16), assignedEmployeeID varchar(16), requesterEmployeeID varchar(16), status varChar(16), reqType varChar(16), medicalType varChar(16), sampleType varChar(16))"); // FOREIGN KEY (employeeID) REFERENCES Employee(EmployeeID))
    for (labRequest currentReq : requests) {
      // System.out.println(currentReq.generateInsertStatement());
      stm.execute(currentReq.generateInsertStatement());
    }
  }

  /**
   * @return ArrayList </labRequest>
   * @throws SQLException
   */
  public ArrayList<labRequest> getAllRequests() throws SQLException {
    updateDatabase();
    return requests;
  }

  /**
   * @param reqID request ID
   * @param nodeID id of location node
   * @param assignedEmpID employee name
   * @param requesterEmpID employee id
   * @param status status of request processing/done
   * @param reqType type of Request, for this always medical
   * @param medicalType type of medical, for this Lab
   * @param sampleType type of sample blood/urine
   * @throws SQLException
   */
  public void addRequest(
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType)
      throws SQLException {
    Statement stm = DatabaseManager.getConn().createStatement();
    requests.add(
        new labRequest(
            reqID, nodeID, assignedEmpID, requesterEmpID, status, "Medical", "Lab", sampleType));
    reqIDs.add(reqID);
    String add =
        "INSERT INTO labRequest values ("
            + "'"
            + reqID
            + "', '"
            + nodeID
            + "', '"
            + assignedEmpID
            + "' , '"
            + requesterEmpID
            + "' , '"
            + status
            + "' , '"
            + reqType
            + "' , '"
            + medicalType
            + "' , '"
            + sampleType
            + "')";
    System.out.println(add);
    stm.execute(add);
    // updateDatabase();
  }

  /**
   * @param deletedObject labRequest
   * @throws SQLException
   */
  public void deleteRequest(labRequest deletedObject) throws SQLException {
    for (labRequest currentReq : requests) {
      if (deletedObject.equals(currentReq)) {
        requests.remove(currentReq);
        System.out.println("found and removed :)");
        break;
      }
    }
    updateDatabase();
  }

  /**
   * @param updatingRequest labRequest to update
   * @param reqID new reqID
   * @param nodeID new nodeID
   * @param assignedEmpID new assignedEmpID
   * @param requesterEmpID new erquesterID
   * @param status new status
   * @param reqType new reqtype
   * @param medicalType new
   * @param sampleType new
   * @throws SQLException
   */
  public void updateRequest(
      labRequest updatingRequest,
      String reqID,
      String nodeID,
      String assignedEmpID,
      String requesterEmpID,
      String status,
      String reqType,
      String medicalType,
      String sampleType)
      throws SQLException {
    labRequest newReq =
        new labRequest(
            reqID, nodeID, assignedEmpID, requesterEmpID, status, "Medical", "Lab", sampleType);
    for (labRequest currentReq : requests) {
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
    String q = "SELECT * FROM labRequest";
    ResultSet rset = stm.executeQuery(q);
    while (rset.next()) {
      for (labRequest currentReq : requests) {
        // some sort of checker.....
      }
    }
    rset.close();
  }

  public ArrayList<MedDelReq> requestsFromRSET(ResultSet rset) throws SQLException {
    ArrayList<MedDelReq> reqs = new ArrayList<MedDelReq>();
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
      reqs.add(newR);
    }
    return reqs;
  }

  public void saveRequestToCSV() {

    String csvName = "src/main/resources/edu/wpi/cs3733/D22/teamF/csv/MedEquipReq.csv";
    // TODO JavaFX thingy

    Statement stm = null;
    try {
      stm = conn.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      ResultSet rset;
      rset = stm.executeQuery("SELECT * FROM medicalEquipmentDeliveryRequest");

      ArrayList<MedDelReq> allReqs = requestsFromRSET(rset);

      rset.close();
      File newCSV = new File(csvName);
      FileWriter fw = new FileWriter(csvName);
      fw.write("reqID, equipID, nodeID, assEmpID, reqEmpID, status\n");
      for (MedDelReq l : allReqs) {
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
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
