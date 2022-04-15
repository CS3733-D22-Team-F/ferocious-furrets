package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestDAOImpl;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sanitationDAOImpl implements IRequestDAO{


    @Override
    public void initTable(File file) throws SQLException, IOException {
        DatabaseManager.dropTableIfExist("sanitationRequest");
        DatabaseManager.runStatement("CREATE TABLE sanitationRequest (reqID varchar(16) PRIMARY KEY, accessObject varChar(64), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

        List<String> lines = CSVReader.readFile(file);
        for (String currentLine : lines) {
            //      System.out.println(currentLine);
            addInit(makeArrayListFromString(currentLine));
        }
    }

    @Override
    public void initTable(String filePath) throws SQLException, IOException {
        DatabaseManager.dropTableIfExist("sanitationRequest");
        DatabaseManager.runStatement("CREATE TABLE sanitationRequest (reqID varchar(16) PRIMARY KEY, accessObject varChar(64), Foreign Key (reqID) references SERVICEREQUEST(reqID))");

        List<String> lines = CSVReader.readResourceFilepath(filePath);
        for (String currentLine : lines) {
            //      System.out.println(currentLine);
            addInit(makeArrayListFromString(currentLine));
        }
    }

    public void addInit(ArrayList<String> fields) throws SQLException {
        ArrayList<String> serviceRequestFields = new ArrayList<>();
        ArrayList<String> sanitationRequest = new ArrayList<>();

        sanitationRequest.add(0, fields.get(0)); // request id
        sanitationRequest.add(1, fields.get(1)); // type of sanitation request
        DatabaseManager.runStatement(generateInsertStatement(sanitationRequest));
    }

    @Override
    public ResultSet get() throws SQLException, IOException {
        return DatabaseManager.runQuery("SELECT * FROM SANITATIONREQUEST");
    }

    @Override
    public void add(ArrayList<String> fields) throws SQLException {
        ArrayList<String> serviceRequestFields = new ArrayList<>();
        ArrayList<String> sanitationRequest = new ArrayList<>();

        sanitationRequest.add(0, fields.get(0)); // request id
        sanitationRequest.add(1, fields.get(5)); // sanitation type

        serviceRequestFields.add(0, fields.get(0)); // request ID
        serviceRequestFields.add(1, fields.get(1)); // node iD
        serviceRequestFields.add(2, fields.get(2)); // assigned emp id
        serviceRequestFields.add(3, fields.get(3)); // requester emp id
        serviceRequestFields.add(4, fields.get(4)); // status

        DatabaseManager.runStatement(
                RequestDAOImpl.generateInsertStatementForService(serviceRequestFields));
        DatabaseManager.runStatement(generateInsertStatement(sanitationRequest));
    }

    @Override
    public void delete(String reqID) throws SQLException {
        String cmd = "UPDATE SANITATIONREQUEST SET status = 'done' WHERE reqID = '" + reqID + "'";
        DatabaseManager.runStatement(cmd);
    }

    @Override
    public void update(ArrayList<String> fields) throws SQLException {

    }

    private ArrayList<String> makeArrayListFromString(String currentLine) {
        ArrayList<String> fields = new ArrayList<>();
        String[] currentLineSplit = currentLine.split(",");
        String reqID = currentLineSplit[0];
        String type = currentLineSplit[1];

        fields.add(reqID);
        fields.add(type);

        return fields;
    }

    @Override
    public String generateInsertStatement(ArrayList<String> fields) {
        return String.format(
                "INSERT INTO sanitationRequest VALUES ('%s', '%s')", fields.get(0), fields.get(1));
    }

    @Override
    public void backUpToCSV(String fileDir) throws SQLException, IOException {
        ArrayList<String> toAdd = new ArrayList<>();
        ResultSet currentRow = get();
        toAdd.add("reqID,accessObject");

        while (currentRow.next()) {
            toAdd.add(
                    String.format(
                            "%s,%s", currentRow.getString("reqID"), currentRow.getString("accessObject")));
        }

        CSVWriter.writeAllToDir(fileDir, toAdd);
    }

    @Override
    public void backUpToCSV(File file) throws SQLException, IOException {
        ArrayList<String> toAdd = new ArrayList<>();
        ResultSet currentRow = get();
        toAdd.add("reqID,accessObject");

        while (currentRow.next()) {
            toAdd.add(
                    String.format(
                            "%s,%s", currentRow.getString("reqID"), currentRow.getString("accessObject")));
        }

        CSVWriter.writeAll(file, toAdd);
    }

}
