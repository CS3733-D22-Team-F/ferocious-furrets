package edu.wpi.cs3733.D22.teamF.pageControllers.employee;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

public class deleteEmployeePopUpController extends PageController implements Initializable {


  public void submit() throws SQLException {
    String employeeID = null;
    DatabaseManager.getEmployeeDAO().delete(employeeID);
  }

  public void reset() {

  }

  public void back() {
    StageManager.getInstance().setDisplay("employee/employeePage.fxml");
  }

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
