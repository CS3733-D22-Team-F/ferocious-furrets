package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.general.AudioPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Controller for the about page */
public class AboutPageController extends AGlobalMethods implements Initializable {

  @FXML VBox creditVbox;
  @FXML BorderPane creditPane;
  @FXML Label nickLabel;
  @FXML Label getNickLabel2;
  @FXML AnchorPane bannerAnchor;

  @FXML ImageView raffi;
  @FXML ImageView carter;
  @FXML ImageView nikola;
  @FXML ImageView jack;
  @FXML ImageView will;
  @FXML ImageView conner;
  @FXML ImageView evans;
  @FXML ImageView cole;
  @FXML ImageView john;
  @FXML ImageView owen;
  @FXML ImageView azu;

  public void changeTo(String path) throws IOException {
    Stage stage = new Stage();
    Scene root = FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource(path)));
    stage.setScene(root);
    stage.show();
  }

  @FXML
  public void raffiCredit() {
    raffi.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/raffi.fxml", "Raffi Alexander");
          }
        });
  }

  @FXML
  public void carterCredit() {
    carter.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helperForAudio("views/teammembers/carter.fxml", "Carter Bullock", "Music/grandpa.wav");
          }
        });
  }

  @FXML
  public void nikolaCredit() {
    nikola.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/nikola.fxml", "Nikola Grozdani");
          }
        });
  }

  @FXML
  public void jackCredit() {
    jack.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/jack.fxml", "Jack Hanlon");
          }
        });
  }

  @FXML
  public void willCredit() {
    will.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/will.fxml", "Will Huang");
          }
        });
  }

  @FXML
  public void connorCredit() {
    conner.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/connor.fxml", "Conner McKevitt");
          }
        });
  }

  @FXML
  public void evansCredit() {
    evans.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/evans.fxml", "Evans Owusu");
          }
        });
  }

  @FXML
  public void coleCredit() {
    cole.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/cole.fxml", "Cole Parks");
          }
        });
  }

  @FXML
  public void johnCredit() {
    john.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/john.fxml", "John Petrarca");
          }
        });
  }

  @FXML
  public void owenCredit() {
    owen.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/owen.fxml", "Owen Radcliffe");
          }
        });
  }

  @FXML
  public void azuCredit() {
    azu.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            helper("views/teammembers/azu.fxml", "Shiming De (Azu)");
          }
        });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    raffi.setPickOnBounds(true);
    carter.setPickOnBounds(true);
    nikola.setPickOnBounds(true);
    jack.setPickOnBounds(true);
    will.setPickOnBounds(true);
    conner.setPickOnBounds(true);
    evans.setPickOnBounds(true);
    cole.setPickOnBounds(true);
    john.setPickOnBounds(true);
    owen.setPickOnBounds(true);
    azu.setPickOnBounds(true);
  }
}
