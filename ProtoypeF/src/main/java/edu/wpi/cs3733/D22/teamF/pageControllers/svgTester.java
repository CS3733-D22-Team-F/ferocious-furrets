package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import edu.wpi.cs3733.D22.teamF.Map.mapPageController;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class svgTester implements Initializable {

  @FXML public AnchorPane root1 = new AnchorPane();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      SVGGlyphLoader.loadGlyphsFont(
          mapPageController.class.getResourceAsStream("Icons/MapMenuSVG/load.svg"), "load");
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      JFXButton test = createIconButton(SVGGlyphLoader.getIcoMoonGlyph("load.svg"));
      root1.getChildren().add(test);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private JFXButton createIconButton(SVGGlyph glyph) {
    JFXButton button = new JFXButton(null, glyph);
    button.setPrefSize(30, 30);
    button.setMinSize(30, 30);
    button.setMaxSize(30, 30);
    return button;
  }
}
