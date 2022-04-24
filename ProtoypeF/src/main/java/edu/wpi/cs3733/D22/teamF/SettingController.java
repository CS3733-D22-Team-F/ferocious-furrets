package edu.wpi.cs3733.D22.teamF;

import static org.reflections.scanners.Scanners.Resources;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

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
    loadCSS();
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

  public void saveCSS() throws IOException, URISyntaxException {
    String name = nameField.getText();
    String textColor = textPicker.getValue().toString().substring(2, 8);
    String backColor = backPicker.getValue().toString().substring(2, 8);
    String titleColor = titlePicker.getValue().toString().substring(2, 8);

    File myObj =
        new File("src/main/resources/edu/wpi/cs3733/D22/teamF/stylesheets/" + name + ".css");
    myObj.createNewFile();
    FileWriter writer = new FileWriter(myObj);
    writer.flush();
    writer.write(
        String.format(
            "/*** variable declarations***/\n"
                + "\n"
                + "/*This is the normal CSS with the hospital colors*/\n"
                + "\n"
                + "@import url(vars.css);\n"
                + "\n"
                + "{\n"
                + "    f-maincolor: #%s;\n"
                + "    f-subcolor: #%s;\n"
                + "    f-ripple: #3E8CD0;\n"
                + "    f-white: #%s;\n"
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
            titleColor, backColor, textColor));
    writer.flush();
    writer.close();
    choiceBox.getItems().add("stylesheets/" + name + ".css");
  }

  public void loadCSS() {
    ArrayList<String> exceptions =
        new ArrayList<>(
            List.of(
                "stylesheets/combobox.css",
                "stylesheets/DashBoard.css",
                "stylesheets/vars.css",
                "stylesheets/RequestPages.css"));

    Reflections reflections = new Reflections("edu.wpi.cs3733.D22.teamF", Scanners.values());
    Set<String> cssPaths = reflections.get(Resources.with(".*\\.css"));
    ArrayList<String> s = new ArrayList<>();
    for (String path : cssPaths) {
      path = path.substring(25); // Strip "edu/wpi/cs3733/D22/teamF/"
      if (exceptions.contains(path)) continue; // Skip pages that can't be cached

      try {
        s.add(path);
      } catch (Exception e) {
        System.out.println("Loading Error: " + path);
        e.printStackTrace();
      }
    }
    choiceBox.getItems().clear();
    choiceBox.getItems().addAll(s);
  }

  public void applyCSS() {
    String name = choiceBox.getValue();
    SceneManager.getInstance().getStage().getScene().getStylesheets().clear();
    SceneManager.getInstance()
        .getStage()
        .getScene()
        .getStylesheets()
        .add(Fapp.class.getResource(name).toExternalForm());
  }
}
