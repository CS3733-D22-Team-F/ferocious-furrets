package edu.wpi.cs3733.D22.teamF.boundary.employee;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.boundary.PageController;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.employees.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class employeePageController extends PageController implements Initializable {

  @FXML Label employeeTitle;
  @FXML Rectangle upperRect;
  @FXML Rectangle lowerRect;
  @FXML ImageView logo;
  @FXML VBox buttons;
  @FXML JFXButton modifyButton;
  @FXML JFXButton addButton;
  @FXML JFXButton deleteButton;
  @FXML JFXButton homeButton;
  @FXML TableView<Employee> employeeTable;
  @FXML TableColumn<Employee, String> employeeID;
  @FXML TableColumn<Employee, String> firstName;
  @FXML TableColumn<Employee, String> lastName;
  @FXML TableColumn<Employee, String> salary;

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  public void initialize(URL location, ResourceBundle resources) {
    employeeID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeID"));
    firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
    salary.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));

    ObservableList<Employee> eList = FXCollections.observableList(updateTableFromDatabase());
    employeeTable.setItems(eList);
  }

  private ArrayList<Employee> updateTableFromDatabase() {
    ArrayList<Employee> employees = new ArrayList<>();
    try {
      ResultSet rset = DatabaseManager.getEmployeeDAO().get();
      while (rset.next()) {
        String empID = rset.getString("EMPLOYEEID");
        String first = rset.getString("FIRSTNAME");
        String last = rset.getString("LASTNAME");
        String salary = rset.getString("SALARY");
        System.out.println(empID + first + last + salary);
        Employee emp = new Employee(empID, first, last, salary);
        employees.add(emp);
      }
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
    return employees;
  }

  @FXML
  private void addEmployee(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplayAndWait("employee/addEmployee.fxml");
  }

  @FXML
  private void modifyEmployee(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplayAndWait("employee/modifyEmployee.fxml");
  }

  @FXML
  private void deleteEmployee(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplayAndWait("employee/deleteEmployee.fxml");
  }

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
}
