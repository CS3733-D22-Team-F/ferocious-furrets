package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.cs3733.D22.teamF.controllers.general.AudioPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AGlobalMethods {

  private static final String FX_TEXT_FILL_WHITE = "-fx-text-fill:WHITE";
  private static final String FX_BACKGROUND_BLUE = "-fx-background-color:#123090";

  public static void setCircleButton(Button button, int radius) {
    button.setStyle(
        //        "-fx-background-radius: "
        //            + radius
        //            + "em; "
        //            + "-fx-min-width: "
        //            + radius
        //            + "px; "
        //            + "-fx-min-height: "
        //            + radius
        //            + "px; "
        //            + "-fx-max-width: "
        //            + radius
        //            + "px; "
        //            + "-fx-max-height: "
        //            + radius
        //            + "px;"
        FX_TEXT_FILL_WHITE + ";" + FX_BACKGROUND_BLUE);
  }

  /**
   * Shows an alert
   *
   * @param info String info to show on the alert
   * @param random Node node to get the scene and window from for the alert //TODO rename random to
   *     something that actually makes sense
   */
  public static void showAlert(String info, Node random) {
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setBody(new Label(info));
    JFXAlert<Void> alert = new JFXAlert<>(random.getScene().getWindow());
    alert.setOverlayClose(true);
    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    alert.setContent(layout);
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }

  public void helper(String file, String name) {
    Parent root = null;
    try {
      root = FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource(file)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage popupwindow = new Stage();
    popupwindow.setTitle(name);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }

  public void helperForAudio(String file, String name, String musicFile) {
    Parent root = null;
    try {
      root = FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource(file)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    AudioPlayer.getInstance().setAudioInputStream(musicFile);
    AudioPlayer.getInstance().playFrom(1000);

    Stage popupwindow = new Stage();
    popupwindow.setTitle(name);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
    AudioPlayer.getInstance().stop();
  }
}
