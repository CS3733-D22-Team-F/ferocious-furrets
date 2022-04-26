package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.Exceptions.ServiceException;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
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

  @FXML StackPane pagePane;

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

  public void changeToMap() throws IOException {
    changeTo("views/mapPage.fxml");
  }

  public void changeToLab() throws IOException {
    changeTo("views/labRequestPage.fxml");
  }

  public void changeToScan() throws IOException {
    changeTo("views/scanPage.fxml");
  }

  public void changeToEquipment() throws IOException {
    changeTo("views/equipmentPage.fxml");
  }

  public void changeToAudio() throws IOException {
    changeTo("views/audioVisualPage.fxml");
  }

  public void changeToExtPatient() throws IOException {
    changeTo("views/extPatDeliveryPage.fxml");
  }

  public void changeToGift() throws IOException {
    changeTo("views/giftPageResized.fxml");
  }

  public void changeToMeal() throws IOException {
    changeTo("views/mealPage.fxml");
  }

  public void changeToMedicine() throws IOException, ServiceException {
    MedicineRequest.run(0, 0, 0, 0, "stylesheets/RequestPages.css", "FDEPT00301");
  }

  public void changeToSecurity() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/securityPage.fxml");
  }

  public void changeToPhysical() throws IOException {
    changeTo("views/physicalTherapyPage.fxml");
  }

  public void changeToLanding() throws IOException {
    changeTo("views/landingPage.fxml");
  }

  public void changeToFacilities() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/facilitiesPage.fxml");
  }

  public void changeToMaintenance() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/maintenanceRequestPage.fxml");
  }

  public void goToTransport() throws IOException {
    changeTo("views/extPatDeliveryPage.fxml");
  }

  public void changeTo(String path) throws IOException {
    SubScene scene = SceneManager.getInstance().setScene(path);
    pagePane.getChildren().clear();
    pagePane.getChildren().addAll(scene);
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
