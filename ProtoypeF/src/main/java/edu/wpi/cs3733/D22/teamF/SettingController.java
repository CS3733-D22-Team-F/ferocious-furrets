package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.theme.Theme;
import java.io.File;
import java.io.FileWriter;
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
  File theme;
  String main = "";
  String sub = "";
  String text = "";

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

  public void saveCSS() throws IOException, SQLException {
    String name = nameField.getText();
    String textColor = textPicker.getValue().toString().substring(2, 8);
    String mainColor = backPicker.getValue().toString().substring(2, 8);
    String subColor = titlePicker.getValue().toString().substring(2, 8);

    Theme newTheme = new Theme(name, mainColor, subColor, textColor);
    DatabaseManager.getInstance().getThemeDAO().add(newTheme);
    DatabaseManager.getInstance().backUpDatabaseToCSV();
    loadCSS();
  }

  public void loadCSS() throws SQLException, IOException {
    ArrayList<Theme> themeList = DatabaseManager.getInstance().getThemeDAO().getArrayList();
    ArrayList<String> names = new ArrayList<>();
    for (Theme t : themeList) {
      names.add(t.getName());
    }
    choiceBox.getItems().clear();
    choiceBox.getItems().addAll(names);
  }

  public void changeCSS() throws SQLException, IOException {
    ArrayList<Theme> themes = DatabaseManager.getInstance().getThemeDAO().getArrayList();
    for (Theme t : themes) {
      if (t.getName().equals(choiceBox.getValue())) {
        System.out.println(choiceBox.getValue());
        System.out.println("find exist theme");
        main = t.getMainColor();
        sub = t.getSubColor();
        text = t.getTextColor();
      }
    }
    theme = generateCSS(main, sub, text);
    //    SceneManager.getInstance().getStage().getScene().getStylesheets().clear();
    //    SceneManager.getInstance()
    //        .getStage()
    //        .getScene()
    //        .getStylesheets()
    //        .addAll(generateCSS(main, sub, text).getAbsolutePath());
  }

  public void applyCSS() throws IOException {
    SceneManager.getInstance().getStage().getScene().getStylesheets().clear();
    SceneManager.getInstance().getStage().getScene().getStylesheets().add(theme.toURI().toString());
  }

  public File generateCSS(String main, String sub, String text) throws IOException {
    theme = new File("theme.css");
    FileWriter writer = new FileWriter(theme);
    writer.flush();
    writer.write(
        String.format(
            "{\n"
                + "    f-maincolor: #%s;\n"
                + "    f-subcolor: #%s;\n"
                + "    f-ripple: #3E8CD0;\n"
                + "    f-white: #%s;\n"
                + "    f-blue: #123090;\n"
                + "    f-gold: #f3ba48;\n"
                + "}\n"
                + "\n"
                + "#TextTitleWhite {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + "#TextTitle {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#TextTitle2 {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#TextTitle3 {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".text-field {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-prompt-text-fill: rgba(128, 128, 128, 0.64);\n"
                + "    -fx-text-fill: #000000;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + ".menu-bar .label {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 18px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#submitButton {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-pref-height: 60;\n"
                + "    -fx-pref-width: 200;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + "#resetButton {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-pref-height: 60;\n"
                + "    -fx-pref-width: 200;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + "#rectangle1 {\n"
                + "    -fx-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#rectangle2 {\n"
                + "    -fx-fill: f-subcolor;\n"
                + "}\n"
                + "\n"
                + ".jfx-rippler.jfx-button {\n"
                + "    -jfx-rippler-fill: f-ripple;\n"
                + "}\n"
                + "\n"
                + ".combo-box {\n"
                + "    -fx-background-color: white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "    -fx-font-size: 24px;\n"
                + "}",
            main, sub, text));
    writer.flush();
    writer.close();
    return theme;
  }
}
