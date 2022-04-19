package edu.wpi.cs3733.D22.teamF.boundary.pagecontrollers.request;

import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class modifyRequestPageController extends PageController
        implements Initializable{

    @FXML TextField requestTypeField;
    @FXML TextField requestIDField;
    @FXML TextField nodeIDField;
    @FXML TextField AssignedEmpIDField;
    @FXML TextField requestedEmpIDField;
    @FXML TextField statusField;
    @FXML TextField ExtraField1;
    @FXML TextField ExtraField2;
    @FXML TextField ExtraField3;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    public void initialize(URL location, ResourceBundle resources) {

        // TODO Get all the requestFields including requestType from previous scene......
        // TODO show, hide, edit ExtraField1-3 based on the requestTypeField

    }

    /**
     * submit function for button in request controllers
     *
     * @throws SQLException
     * @throws IOException
     */
    public void submit() {

        // TODO put fields into arrayList and run modifyRequestInDatabase with arrayList

    }

    /**
     * clears the fields in the request page
     */
    public void reset() {}

    /**
     * Method to create a class specifics context menu
     *
     * @return A specific's class context menu
     */
    public ContextMenu makeContextMenu() {
        return null;
    }

    public void modifyRequestInDatabase(ArrayList<String> fields) {
        RequestSystem edit = new RequestSystem(requestTypeField.getText());
        edit.modifyRequest(fields); // todo Make modify functionality for all the request
    }


}
