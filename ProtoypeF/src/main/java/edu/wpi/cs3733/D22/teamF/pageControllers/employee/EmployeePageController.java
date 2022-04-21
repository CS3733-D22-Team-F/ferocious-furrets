package edu.wpi.cs3733.D22.teamF.pageControllers.employee;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.employees.*;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeePageController extends PageController implements Initializable {

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
    ArrayList<Employee> employees = new ArrayList<>();
    try {
      ResultSet rset = DatabaseManager.getInstance().getEmployeeDAO().get();
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
    // employees is a list of all employees in the table

    ObservableList<Employee> eList = FXCollections.observableList(employees);
    employeeTable.setItems(eList);
  }

  @FXML
  private void addEmployee(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(Fapp.class.getResource("views/employee/addEmployee.fxml")));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  @FXML
  private void modifyEmployee(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(Fapp.class.getResource("views/employee/modifyEmployee.fxml")));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
  }

  @FXML
  private void deleteEmployee(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(Fapp.class.getResource("views/employee/deleteEmployee.fxml")));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
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
