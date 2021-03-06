package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.arduino.ArduinoLogin;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.ThemeManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingController implements Initializable {
  @FXML private Label userFromLogin;
  @FXML JFXComboBox<String> choiceBox;
  @FXML VBox pickerBox;
  @FXML TextField nameField;
  @FXML JFXButton saveToCSV;
  @FXML JFXButton loadFromCSV;

  JFXColorPicker textPicker;
  JFXColorPicker backPicker;
  JFXColorPicker titlePicker;

  ArduinoLogin ardLogin = new ArduinoLogin();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    textPicker = new JFXColorPicker();
    backPicker = new JFXColorPicker();
    titlePicker = new JFXColorPicker();
    pickerBox.getChildren().add(textPicker);
    pickerBox.getChildren().add(backPicker);
    pickerBox.getChildren().add(titlePicker);
    userFromLogin.setText("Current User: " + UserType.getUserType());
    try {
      loadCSS();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void logoutSwitch() {
    FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource("views/logInPage.fxml"));
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
  }

  @FXML
  public void aboutButton(ActionEvent event) throws IOException {

    Parent root = null;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource("views/aboutPage.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage popupwindow = new Stage();
    popupwindow.setTitle("About Page");
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }

  @FXML
  public void creditButton(ActionEvent event) throws IOException {

    Parent root = null;
    try {
      root = FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource("views/credits.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage popupwindow = new Stage();
    popupwindow.setTitle("Credit Page");
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }

  @FXML
  public void rescanFinger(ActionEvent event) throws IOException, InterruptedException {

    if (ardLogin.reenrollFingerprint()) {
      System.out.println("Fingerprint Reenrolled");
    } else {
      System.out.println("Fingerprint Reenroll Unsuccessful");
    }
  }

  public void saveCSS() throws SQLException, IOException {
    String name = nameField.getText();
    String textColor = textPicker.getValue().toString().substring(2, 8);
    String mainColor = backPicker.getValue().toString().substring(2, 8);
    String subColor = titlePicker.getValue().toString().substring(2, 8);
    if (choiceBox.getItems().contains(name)) {
      AGlobalMethods.showAlert("This theme already exist", userFromLogin);
    } else {
      ThemeManager.getInstance().saveCSS(name, textColor, mainColor, subColor);
      loadCSS();
      nameField.clear();
    }
  }

  public void deleteCSS() throws SQLException, IOException {

    try {
      if (choiceBox.getValue().equals("default")
          || choiceBox.getValue().equals("Dark")
          || choiceBox.getValue().equals("Color Blind 1")
          || choiceBox.getValue().equals("Color Blind 2")
          || choiceBox.getValue().equals("Color Blind 3")) {
        AGlobalMethods.showAlert("Cannot delete default theme", userFromLogin);
      } else {
        ThemeManager.getInstance().deleteCSS(choiceBox.getValue());
      }

    } catch (Exception ignored) {
      AGlobalMethods.showAlert("Please select a theme", userFromLogin);
    }
    loadCSS();
  }

  public void loadCSS() throws SQLException, IOException {
    ArrayList<String> names = ThemeManager.getInstance().getCSS();
    choiceBox.getItems().clear();
    choiceBox.getItems().setAll(names);
  }

  public void changeCSS() throws SQLException, IOException {
    ThemeManager.getInstance().changeCSS(choiceBox.getValue());
  }

  public void applyCSS() throws SQLException, IOException {
    try {
      if (choiceBox.getValue() == "default") {
        AGlobalMethods.showAlert("Already default theme", userFromLogin);
      } else {
        changeCSS();
        ThemeManager.getInstance().applyCSS();
      }
    } catch (Exception ignored) {
      AGlobalMethods.showAlert("PLease select a theme", userFromLogin);
    }
    loadCSS();
  }

  public void backUpDatabase() throws SQLException, IOException {
    DirectoryChooser fChoose = new DirectoryChooser();
    fChoose.setTitle("Select Directory to Save Database:");
    Stage stage = (Stage) saveToCSV.getScene().getWindow();
    File file = fChoose.showDialog(stage);

    DatabaseManager.getInstance()
        .getLocationDAO()
        .backUpToCSV(file.getPath() + "/TowerLocations.csv");
    DatabaseManager.getInstance().getAudioVisDAO().backUpToCSV(file.getPath() + "/AudioVisual.csv");
    DatabaseManager.getInstance()
        .getMedEquipDelReqDAO()
        .backUpToCSV(file.getPath() + "/EquipmentDelivery.csv");
    DatabaseManager.getInstance().getMedEquipDAO().backUpToCSV(file.getPath() + "/Equipment.csv");
    DatabaseManager.getInstance()
        .getExtPatDAO()
        .backUpToCSV(file.getPath() + "/ExternalPatientTransport.csv");
    DatabaseManager.getInstance().getGiftDAO().backUpToCSV(file.getPath() + "/Gifts.csv");
    DatabaseManager.getInstance()
        .getLabRequestDAO()
        .backUpToCSV(file.getPath() + "/LabRequests.csv");
    DatabaseManager.getInstance()
        .getMaintenanceDAO()
        .backUpToCSV(file.getPath() + "/Maintenance.csv");
    DatabaseManager.getInstance().getMealDAO().backUpToCSV(file.getPath() + "/Meals.csv");
    DatabaseManager.getInstance().getPTDAO().backUpToCSV(file.getPath() + "/PhysicalTherapy.csv");
    DatabaseManager.getInstance()
        .getScanRequestDAO()
        .backUpToCSV(file.getPath() + "/ScanRequests.csv");
    DatabaseManager.getInstance().getSecurityDAO().backUpToCSV(file.getPath() + "/Security.csv");
    DatabaseManager.getInstance().getThemeDAO().backUpToCSV(file.getPath() + "/Themes.csv");
    DatabaseManager.getInstance().getRequestDAO().backUpToCSV(file.getPath() + "/Requests.csv");
    DatabaseManager.getInstance().getEmployeeDAO().backUpToCSV(file.getPath() + "/Employees.csv");
  }

  public void reloadDatabase() throws SQLException, IOException {
    DirectoryChooser fChoose = new DirectoryChooser();
    fChoose.setTitle("Select Directory to Load Database:");
    Stage stage = (Stage) saveToCSV.getScene().getWindow();
    File file = fChoose.showDialog(stage);
    System.out.println("cd: " + file.getPath());

    DatabaseManager.getInstance().dropAllTables();
    DatabaseManager.getInstance().getEmployeeDAO().initTable(file.getPath() + "/employees.csv");
    DatabaseManager.getInstance()
        .getLocationDAO()
        .initTable(file.getPath() + "/TowerLocations.csv");
    DatabaseManager.getInstance().getRequestDAO().initTable(file.getPath() + "/serviceRequest.csv");
    DatabaseManager.getInstance().getMedEquipDAO().initTable(file.getPath() + "/equipment.csv");
    DatabaseManager.getInstance()
        .getMedEquipDelReqDAO()
        .initTable(file.getPath() + "/MedEquipReq.csv");
    DatabaseManager.getInstance().getGiftDAO().initTable(file.getPath() + "/gifts.csv");
    DatabaseManager.getInstance().getLabRequestDAO().initTable(file.getPath() + "/labs.csv");
    DatabaseManager.getInstance().getScanRequestDAO().initTable(file.getPath() + "/scans.csv");
    DatabaseManager.getInstance().getAudioVisDAO().initTable(file.getPath() + "/audioVis.csv");
    DatabaseManager.getInstance()
        .getMaintenanceDAO()
        .initTable(file.getPath() + "/maintenanceSR.csv");
    DatabaseManager.getInstance().getPTDAO().initTable(file.getPath() + "/physicaltherapy.csv");
    DatabaseManager.getInstance().getSecurityDAO().initTable(file.getPath() + "/security.csv");
    DatabaseManager.getInstance().getExtPatDAO().initTable(file.getPath() + "/extPatDelivery.csv");
    DatabaseManager.getInstance().getThemeDAO().initTable(file.getPath() + "/themes.csv");
    DatabaseManager.getInstance().getMealDAO().initTable(file.getPath() + "/meals.csv");
  }
}
