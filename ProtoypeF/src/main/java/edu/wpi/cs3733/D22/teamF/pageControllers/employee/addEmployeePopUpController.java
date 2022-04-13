package edu.wpi.cs3733.D22.teamF.pageControllers.employee;

import edu.wpi.cs3733.D22.teamF.controllers.requests.IRequestController;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

public class addEmployeePopUpController extends PageController
    implements Initializable, IRequestController {
  public void submit() throws SQLException {}

  public void reset() {}

  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public ContextMenu makeContextMenu() {
    return null;
  }

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  public void initialize(URL location, ResourceBundle resources) {}
}
