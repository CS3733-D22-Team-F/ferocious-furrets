package edu.wpi.cs3733.D22.teamF;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CreditPageController implements Initializable {

  @FXML VBox creditVbox;
  @FXML BorderPane creditPane;
  @FXML Label nickLabel;
  @FXML Label getNickLabel2;
  @FXML AnchorPane bannerAnchor;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    bannerAnchor.maxWidthProperty().bind(creditPane.maxHeightProperty());
    //    creditVbox.layoutXProperty().bind(creditPane.maxWidthProperty());
    //    creditVbox.layoutYProperty().bind(creditPane.maxHeightProperty());
    //    creditVbox.maxHeightProperty().bind(creditVbox.heightProperty());
    //    ((Stage) (creditVbox.getScene().getWindow())).setResizable(false);
  }
}
