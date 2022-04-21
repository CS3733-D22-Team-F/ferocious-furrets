package edu.wpi.cs3733.D22.teamF;

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
  @FXML ImageView connor;
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
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();

            //            FXMLLoader fxmlLoader =
            //                new
            // FXMLLoader(Fapp.class.getResource("views/teammembers/raffi.fxml"));
            //            Scene scene = null;
            //            try {
            //              scene = new Scene(fxmlLoader.load());
            //            } catch (IOException e) {
            //              e.printStackTrace();
            //            }
            //            Stage stage = SceneManager.getInstance().getStage();
            //            SceneManager.getInstance().setStage(stage);
            //            stage.setScene(scene);
            //            stage.show();
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
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(root);
            popupwindow.setScene(scene1);
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.showAndWait();
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
