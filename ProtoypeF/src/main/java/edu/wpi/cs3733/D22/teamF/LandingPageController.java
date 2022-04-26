package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Controller for the landing page */
public class LandingPageController implements Initializable {

  @FXML JFXButton security;
  @FXML JFXButton meal;
  @FXML JFXButton gift;
  @FXML JFXButton therapy;
  @FXML JFXButton lab;
  @FXML JFXButton scan;
  @FXML JFXButton facility;
  @FXML JFXButton equipment;
  @FXML JFXButton audio;
  @FXML JFXButton patient;
  @FXML JFXButton medicine;
  @FXML JFXButton maintenance;
  @FXML JFXButton map;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    maintenance.setGraphic(MapIconModifier.getIcon("toolMenu"));
    security.setGraphic(MapIconModifier.getIcon("securityMenu"));
    meal.setGraphic(MapIconModifier.getIcon("mealMenu"));
    gift.setGraphic(MapIconModifier.getIcon("giftMenu"));
    therapy.setGraphic(MapIconModifier.getIcon("physicalMenu"));
    lab.setGraphic(MapIconModifier.getIcon("labMenu"));
    scan.setGraphic(MapIconModifier.getIcon("scanMenu"));
    facility.setGraphic(MapIconModifier.getIcon("cleanMenu"));
    equipment.setGraphic(MapIconModifier.getIcon("equipmentMenu"));
    audio.setGraphic(MapIconModifier.getIcon("audioMenu"));
    patient.setGraphic(MapIconModifier.getIcon("patientMenu"));
    medicine.setGraphic(MapIconModifier.getIcon("medicineMenu"));
    map.setGraphic(MapIconModifier.getIcon("mapMenu"));
  }

  /** opens the credit page */
  public void openCredit() {
    Parent root = null;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(Fapp.class.getResource("views/aboutPage.fxml")));
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
}
