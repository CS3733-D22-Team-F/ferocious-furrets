package edu.wpi.furious_furrets.controllers.entities;

import edu.wpi.furious_furrets.database.DatabaseInitializer;
import edu.wpi.furious_furrets.database.LocationsDAOImpl;
import edu.wpi.furious_furrets.database.MedDelReqDAOImpl;
import edu.wpi.furious_furrets.database.labReqDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for managing instances of the various DAO implementations for the different tables To Use access a DAO call
 * DatabaseManager.getWantedDAO()
 *
 * @see LocationsDAOImpl
 * @see edu.wpi.furious_furrets.database.MedDelReq
 */
public class DatabaseManager {

    private static final Connection conn = DatabaseInitializer.getConnection().dbConnection;
    private static final LocationsDAOImpl ldao = new LocationsDAOImpl(conn);
    private static final MedDelReqDAOImpl mdao = new MedDelReqDAOImpl(conn);
    private static final labReqDAOImpl lrdao = new labReqDAOImpl(conn);

    private static DatabaseManager DatabaseManager;

    private DatabaseManager() {
    }

    /**
     * inits the dao objects
     *
     * @return DatabaseManager
     *
     * @throws SQLException
     * @throws IOException
     */
    public static DatabaseManager initalizeDatabaseManager() throws SQLException, IOException {
        ldao.initTable();
        mdao.initTable();
        return Helper.dbMan;
    }

    /**
     * gets the connection object
     *
     * @return Connection
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * gets the LocatationDAOImpl object Use to use the addLocation, update, delete, etc
     *
     * @return LocationsDAOImpl
     */
    public static LocationsDAOImpl getLdao() {
        return ldao;
    }

    /**
     * gets the MedDelReqDAOImpl object Use to use the addLocation, update, delete, etc
     *
     * @return MedDelReqDAOImpl
     */
    public static MedDelReqDAOImpl getMdao() {
        return mdao;
    }

    /**
     * gets the labReqDAOImpl object Use to use the addLocation, update, delete, etc
     *
     * @return labReqDAOImpl
     */
    public static labReqDAOImpl getlrdao() {
        return lrdao;
    }

    /**
     * helper
     */
    private static class Helper {
        private static final DatabaseManager dbMan = new DatabaseManager();
    }

    /**
     * helper but singleton
     */
    private static class SingletonHelper {
        private static final DatabaseManager DatabaseManager = new DatabaseManager();
    }
}
