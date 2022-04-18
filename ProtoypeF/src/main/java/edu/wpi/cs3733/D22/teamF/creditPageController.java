package edu.wpi.cs3733.D22.teamF;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class creditPageController implements Initializable {

  @FXML VBox creditVbox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    creditVbox.maxHeightProperty().bind(creditVbox.heightProperty());
    ((Stage) (creditVbox.getScene().getWindow())).setResizable(false);
  }
}
