package edu.wpi.cs3733.D22.teamF;

import javafx.scene.control.Button;

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
}
