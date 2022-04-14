package edu.wpi.cs3733.D22.teamF.boundary.employee;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.boundary.PageController;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class modifyEmployeePopUpController extends PageController implements Initializable {

  @FXML JFXButton submitButton;
  @FXML JFXButton backButton;
  @FXML TextField employeeID;
  @FXML TextField firstName;
  @FXML TextField lastName;
  @FXML TextField salaryText;

  public void modify(ActionEvent event) throws SQLException {
    String empID = employeeID.getText();
    String fName = firstName.getText();
    String lName = lastName.getText();
    String salary = salaryText.getText();
    if (!(empID.equals("") || fName.equals("") || lName.equals("") || salary.equals(""))) {
      ArrayList<String> newEmployee = new ArrayList<>();
      newEmployee.add(0, empID);
      newEmployee.add(1, fName);
      newEmployee.add(2, lName);
      newEmployee.add(3, salary);
      String cmd =
          String.format(
              "UPDATE EMPLOYEE SET FIRSTNAME = '%s', LASTNAME = '%s', SALARY = '%s' WHERE EMPLOYEEID = '%s'",
              newEmployee.get(1), newEmployee.get(2), newEmployee.get(3), newEmployee.get(0));
      DatabaseManager.runStatement(cmd);
      // DatabaseManager.getEmployeeDAO().add(newEmployee);

    } else {
      System.out.println("One of more of the fields are empty!");
    }
  }

  public void reset(ActionEvent event) {
    employeeID.clear();
    firstName.clear();
    lastName.clear();
    salaryText.clear();
  }

  public void back(ActionEvent event) {
    //    StageManager.getInstance().setDisplay("employee/employeePage.fxml");
    Stage stage = (Stage) backButton.getScene().getWindow();
    stage.close();
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