package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingController implements Initializable {
  @FXML private Label themeSetLabel;
  @FXML private JFXComboBox themeSetCombo;
  @FXML private ImageView furretImage;
  @FXML private Label userFromLogin;
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

  @FXML
  public void creditButton(ActionEvent event) throws IOException {

    Parent root = null;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource("views/aboutPage.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();

    //    SceneManager.getInstance().setScene("views/aboutPage.fxml");
    //    FXMLLoader fxmlLoader = new FXMLLoader(Fapp.class.getResource("views/aboutPage.fxml"));
    //    Scene scene = null;
    //    try {
    //      scene = new Scene(fxmlLoader.load());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //    Stage stage = SceneManager.getInstance().getStage();
    //    SceneManager.getInstance().setStage(stage);
    //    stage.setScene(scene);
    //    stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userFromLogin.setText(UserType.getUserType());
    //    themeSetLabel.setVisible(false);
    //    themeSetCombo.setVisible(false);
    //    furretImage.setPickOnBounds(true);
    //    furretImage.setOnMouseClicked(
    //        new EventHandler<MouseEvent>() {
    //          @Override
    //          public void handle(MouseEvent event) {
    //            Parent root = null;
    //            try {
    //              root =
    //                  FXMLLoader.load(
    //                      Objects.requireNonNull(Fapp.class.getResource("views/aboutPage.fxml")));
    //            } catch (IOException e) {
    //              e.printStackTrace();
    //            }
    //            Stage popupwindow = new Stage();
    //            popupwindow.initModality(Modality.APPLICATION_MODAL);
    //            Scene scene1 = new Scene(root);
    //            popupwindow.setScene(scene1);
    //            popupwindow.initModality(Modality.APPLICATION_MODAL);
    //            // popupwindow.showAndWait();
    //          }
    //        });
  }

  //  public void myFunction(String text) {
  //    userFromLogin.setText(text);
  //  }
}
