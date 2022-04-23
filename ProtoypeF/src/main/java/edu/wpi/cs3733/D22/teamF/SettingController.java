package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.UserType;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingController implements Initializable {
  @FXML private Label themeSetLabel;
  @FXML private JFXComboBox themeSetCombo;
  @FXML private ImageView furretImage;
  @FXML private Label userFromLogin;
  @FXML private JFXButton logoutButton;

  @FXML VBox pickerBox;
  JFXColorPicker textPicker;
  JFXColorPicker backPicker;
  JFXColorPicker titlePicker;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    textPicker = new JFXColorPicker();
    backPicker = new JFXColorPicker();
    titlePicker = new JFXColorPicker();
    pickerBox.getChildren().add(textPicker);
    pickerBox.getChildren().add(backPicker);
    pickerBox.getChildren().add(titlePicker);
    userFromLogin.setText("Current User: " + UserType.getUserType());
  }
}
