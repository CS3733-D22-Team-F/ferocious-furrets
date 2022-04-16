package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class settingController implements Initializable {
  @FXML private Label themeSetLabel;
  @FXML private JFXComboBox themeSetCombo;
  @FXML private JFXButton logoutButton;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    themeSetLabel.setVisible(false);
    themeSetCombo.setVisible(false);
  }
}
