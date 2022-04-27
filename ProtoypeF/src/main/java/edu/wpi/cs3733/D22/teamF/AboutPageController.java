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
public class AboutPageController implements Initializable {

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

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/raffi.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Raffi Alexander");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void carterCredit() {
    carter.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/carter.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Carter Bullock");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void nikolaCredit() {
    nikola.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/nikola.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }


            Stage popupwindow = new Stage();
            popupwindow.setTitle("Nikola Grozdani");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
            AudioPlayer.getInstance().stop();
          }
        });
  }

  @FXML
  public void jackCredit() {
    jack.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/jack.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Jack Hanlon");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void willCredit() {
    will.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/will.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Will Huang");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void connorCredit() {
    conner.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/connor.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Conner McKevitt");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void evansCredit() {
    evans.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/evans.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Evans Owusu");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void coleCredit() {
    cole.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/cole.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Cole Parks");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void johnCredit() {
    john.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/john.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("John Petrarca");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void owenCredit() {
    owen.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(
                          Fapp.class.getResource("views/teammembers/owen.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Owen Radcliffe");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @FXML
  public void azuCredit() {
    azu.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {

            Parent root = null;
            try {
              root =
                  FXMLLoader.load(
                      Objects.requireNonNull(Fapp.class.getResource("views/teammembers/azu.fxml")));
            } catch (IOException e) {
              e.printStackTrace();
            }
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Shiming De (Azu)");
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
          }
        });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    raffi.setPickOnBounds(true);
    carter.setPickOnBounds(true);
    nikola.setPickOnBounds(true);

    //    bannerAnchor.maxWidthProperty().bind(creditPane.maxHeightProperty());
    //    creditVbox.layoutXProperty().bind(creditPane.maxWidthProperty());
    //    creditVbox.layoutYProperty().bind(creditPane.maxHeightProperty());
    //    creditVbox.maxHeightProperty().bind(creditVbox.heightProperty());
    //    ((Stage) (creditVbox.getScene().getWindow())).setResizable(false);
  }
}
