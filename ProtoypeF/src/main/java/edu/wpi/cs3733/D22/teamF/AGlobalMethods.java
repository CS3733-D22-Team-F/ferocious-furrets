package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;

public class AGlobalMethods {

  private static final String FX_TEXT_FILL_WHITE = "-fx-text-fill:WHITE";
  private static final String FX_BACKGROUND_BLUE = "-fx-background-color:#123090";

  public static void setCircleButton(Button button, int radius) {
    button.setStyle(
        "-fx-background-radius: "
            + radius
            + "em; "
            + "-fx-min-width: "
            + radius
            + "px; "
            + "-fx-min-height: "
            + radius
            + "px; "
            + "-fx-max-width: "
            + radius
            + "px; "
            + "-fx-max-height: "
            + radius
            + "px;"
            + FX_TEXT_FILL_WHITE
            + ";"
            + FX_BACKGROUND_BLUE);
  }

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
}
