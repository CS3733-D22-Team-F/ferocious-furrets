package edu.wpi.cs3733.D22.teamF.pageControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javax.swing.text.html.ImageView;

public class linksPageController implements Initializable {

  @FXML ImageView homeWebsiteQR;
  @FXML ImageView employeePortalQR;
  @FXML ImageView staffDirectoryQR;
  @FXML ImageView hrQR;

  @FXML VBox websiteVBox;
  @FXML VBox loginVBox;
  @FXML VBox covidVBox;
  @FXML VBox signUpVBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //    websiteVBox.maxHeightProperty().bind(websiteVBox.heightProperty());
    //    loginVBox.maxHeightProperty().bind(loginVBox.heightProperty());
    //    covidVBox.maxHeightProperty().bind(covidVBox.heightProperty());
    //    signUpVBox.maxHeightProperty().bind(signUpVBox.heightProperty());

    //    creditVbox.maxHeightProperty().bind(creditVbox.heightProperty());

  }

  public void disappear(ImageView im) {}
}
