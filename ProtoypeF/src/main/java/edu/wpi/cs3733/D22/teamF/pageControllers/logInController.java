package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.AGlobalMethods;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamF.returnHomePage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controller for log in scene
 *
 * @see returnHomePage
 */
public class logInController extends returnHomePage implements Initializable {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Label popUpLabel;
  @FXML private JFXComboBox databaseChooser;

  private Stage stage = new Stage();
  private Parent root;
  private Scene scene;

  private DatabaseInitializer.ConnType dbType;

  /*
   * method to send user to the homepage after a successful authentication of username and password
   */
  public void loginSuccess() throws IOException {
    // StageManager.getInstance().setDisplay("homePage.fxml");
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
      AGlobalMethods.showAlert("At least one required field is empty", popUpLabel);
    } else {
      userType.setUserType("");
      popUpLabel.setStyle("-fx-text-fill: red;");
      popUpLabel.setText("Wrong username or password, try again");
    }
    if (success) {
      usernameField.clear();
      passwordField.clear();
      final BooleanProperty embedded =
          new SimpleBooleanProperty(databaseChooser.getValue().toString().equals("Embedded"));

      FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource("views/cachePage.fxml"));
      fxmlLoader.setControllerFactory(c -> new cachePageController(embedded));
      Scene scene = null;
      try {
        scene = new Scene(fxmlLoader.load());
      } catch (IOException e) {
        e.printStackTrace();
      }
      Stage stage = SceneManager.getInstance().getStage();
      SceneManager.getInstance().setStage(stage);
      stage.setScene(scene);
      stage.show();
      popUpLabel.setVisible(false);
    } else {
      popUpLabel.setVisible(true);
    }
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
