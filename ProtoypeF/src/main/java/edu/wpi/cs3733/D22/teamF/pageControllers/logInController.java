package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamF.returnHomePage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controller for log in scene
 *
 * @see returnHomePage
 */
public class logInController extends returnHomePage implements Initializable {
  @FXML private TextField usernameField;
  @FXML private TextField passwordField;
  @FXML private Label popUpLabel;
  @FXML private JFXComboBox databaseChooser;

  private Stage stage = new Stage();
  private Parent root;
  private Scene scene;

  private DatabaseInitializer.ConnType dbType;

  public void loginSuccess() throws IOException {
    StageManager.getInstance().setDisplay("homePage.fxml");
  }

  /**
   * backs up the db to csvs then quits the application
   *
   * @throws SQLException
   * @throws IOException
   */
  @FXML
  private void helpQuit() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }
  /** logs in, or states message the username or password are wrong */
  @FXML
  private void logIn() throws SQLException, IOException {
    boolean success = false;
    UserType userType = new UserType();
    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      userType.setUserType("admin");
      success = true;
    } else if (usernameField.getText().equals("nurse") && passwordField.getText().equals("nurse")) {
      userType.setUserType("nurse");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      success = true;
    } else if (usernameField.getText().equals("doctor")
        && passwordField.getText().equals("doctor")) {
      userType.setUserType("doctor");
      success = true;
    } else if (usernameField.getText().equals("staff") && passwordField.getText().equals("staff")) {
      userType.setUserType("staff");
      success = true;
    } else if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("ERROR");
      alert.setHeaderText("One or more field is empty");
      alert.setContentText("Please fill out any empty fields.");
      alert.showAndWait();
    } else {
      userType.setUserType("");
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
    if (success) {
      usernameField.clear();
      passwordField.clear();
      StageManager.getInstance().setDisplayNoViews("homePage.fxml");
      popUpLabel.setVisible(false);
    } else {
      popUpLabel.setVisible(true);
    }
    if (databaseChooser.getValue().toString().equals("Embedded")) {
      dbType = DatabaseInitializer.ConnType.EMBEDDED;
    } else {
      dbType = DatabaseInitializer.ConnType.CLIENTSERVER;
    }

    // DatabaseManager.switchConnection(dbType);
    DatabaseInitializer.switchConnection(dbType);
    DatabaseManager.initalizeDatabaseManager();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ArrayList<Object> databaseDrop = new ArrayList<>();
    databaseDrop.add("");
    databaseDrop.add("Embedded");
    databaseDrop.add("Client-Server");
    databaseChooser.getItems().addAll(databaseDrop);
    databaseChooser.setValue("Embedded");
  }
}
