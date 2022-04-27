package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.ThemeManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingController implements Initializable {
  @FXML private Label userFromLogin;
  @FXML JFXComboBox<String> choiceBox;
  @FXML VBox pickerBox;
  @FXML TextField nameField;
  JFXColorPicker textPicker;
  JFXColorPicker backPicker;
  JFXColorPicker titlePicker;

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

  public void saveCSS() throws SQLException, IOException {
    String name = nameField.getText();
    String textColor = textPicker.getValue().toString().substring(2, 8);
    String mainColor = backPicker.getValue().toString().substring(2, 8);
    String subColor = titlePicker.getValue().toString().substring(2, 8);
    ThemeManager.getInstance().saveCSS(name, textColor, mainColor, subColor);
    loadCSS();
    nameField.clear();
  }

  public void deleteCSS() throws SQLException, IOException {
    try {
      ThemeManager.getInstance().deleteCSS(choiceBox.getValue());
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

  public void applyCSS() {
    ThemeManager.getInstance().applyCSS();
  }
}
