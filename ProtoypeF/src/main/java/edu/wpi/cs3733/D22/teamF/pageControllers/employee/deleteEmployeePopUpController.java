package edu.wpi.cs3733.D22.teamF.pageControllers.employee;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

public class deleteEmployeePopUpController extends PageController implements Initializable {

  @FXML TextField employeeID;

  public void submit(ActionEvent actionEvent) throws SQLException {
    String empID = employeeID.getText();
    if (empID.equals("")) {
      System.out.println("Cannot leave text fields blank");
    }
    else {
      DatabaseManager.getEmployeeDAO().delete(empID);
    }
  }

  public void reset(ActionEvent actionEvent) {
    employeeID.clear();
  }

  public void back(ActionEvent actionEvent) {
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
