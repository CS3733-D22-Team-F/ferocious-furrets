package edu.wpi.cs3733.D22.teamF.pageControllers.employee;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.requests.IRequestController;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

public class addEmployeePopUpController extends PageController
    implements Initializable, IRequestController {

  @FXML
  JFXButton backButton;
  public void submit() throws SQLException {}

  public void reset() {}

  public void back(ActionEvent event){
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

  public void back(javafx.event.ActionEvent actionEvent) {
    StageManager.getInstance().setDisplay("employee/employeePage.fxml");
  }
}
