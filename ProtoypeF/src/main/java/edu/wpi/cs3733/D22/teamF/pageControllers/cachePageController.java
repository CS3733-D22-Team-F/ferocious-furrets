package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.Load;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class cachePageController implements Initializable {

  private final BooleanProperty useEmbedded;

  public cachePageController(BooleanProperty embedded) {
    this.useEmbedded = embedded;
  }

  @FXML Label statusLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    statusLabel.setText(randomFact());
    statusLabel.setTextAlignment(TextAlignment.CENTER);
    // Task<Void> loader = new Load();
    Task<Void> loader = new Load(useEmbedded.get());
    loader.setOnSucceeded(
        e -> {
          try {
            Stage stage = SceneManager.getInstance().getStage();
            Scene scene =
                new Scene(FXMLLoader.load(Fapp.class.getResource("views/pageHolder.fxml")));
            stage.setScene(scene);
            stage.show();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        });
    Thread thread = new Thread(loader, "Starting App...");
    thread.start();
  }

  private String randomFact() {
    String fact = "";
    double rand = Math.random();
    double whichFact = rand * 10;
    int mod = (int) whichFact;
    switch (mod) {
      case 0:
        fact =
            "Thank YOU for making Brigham & Women's Hospital \na leader in the healthcare industry!";
        break;
      case 1:
        fact =
            "Thank YOU for helping us provide leading healthcare \nto thousands of patients annually!";
        break;
      case 2:
        fact = "Thank YOU, we wouldn't be the same without you!";
        break;
      case 3:
        fact = "Thank YOU for your tireless work to improve our patient's lives!";
        break;
      case 4:
        fact = "Improving our patient's lives since 1980!";
        break;
      case 5:
        fact =
            "Thank YOU for making Brigham & Women's Hospital a \nleader in the healthcare industry!";
        break;
      case 6:
        fact =
            "Thank YOU for helping us provide leading healthcare \nto thousands of patients annually!";
        break;
      case 7:
        fact = "Thank YOU, we wouldn't be the same without you!";
        break;
      case 8:
        fact = "Thank YOU for your tireless work to improve our patient's lives!";
        break;
      case 9:
        fact =
            "Thank YOU for your tireless work! Every one of you is an \nessential part of this hospital!";
        break;
      default:
        break;
    }
    return fact;
  }
}
