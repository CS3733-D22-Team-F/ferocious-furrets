package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.api.API;
import edu.wpi.cs3733.D22.teamD.API.*;
import edu.wpi.cs3733.D22.teamF.Exceptions.ServiceException;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.MedicineRequest;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

/** Controller for the landing page */
public class APILandingPageController implements Initializable {

  @FXML JFXButton facilitiesTeamD;
  @FXML JFXButton internalPatientTransportTeamB;
  @FXML JFXButton medicineTeamF;

  @FXML StackPane pagePane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    facilitiesTeamD.setGraphic(MapIconModifier.getIcon("cleanMenu"));
    internalPatientTransportTeamB.setGraphic(MapIconModifier.getIcon("patientMenu"));
    medicineTeamF.setGraphic(MapIconModifier.getIcon("medicineMenu"));
  }

  public void changeToIntPatient()
      throws IOException, edu.wpi.cs3733.D22.teamB.api.ServiceException {
    // TODO add Team B run() method
    // AGlobalMethods.showAlert("Team B API Page", pagePane);
    API internalPatientPage = new API();
    internalPatientPage.run(
        100,
        100,
        1220,
        660,
        "edu/wpi/cs3733/D22/teamF/stylesheets/RequestPages.css",
        "FDEPT00201",
        "FDEPT00301");
  }

  public void changeToMedicine() throws IOException, ServiceException {
    MedicineRequest.run(0, 0, 0, 0, "stylesheets/RequestPages.css", "FDEPT00301");
  }

  public void changeToFacilities() throws IOException {
    // TODO add Team D run() method
    // AGlobalMethods.showAlert("Team D API Page", pagePane);
    StartAPI.appLaunch(
        0, 0, 0, 0, "edu/wpi/cs3733/D22/teamF/stylesheets/RequestPages.css", "FDEPT00301");
  }

  public void changeTo(String path) throws IOException {
    SubScene scene = SceneManager.getInstance().setScene(path);
    pagePane.getChildren().clear();
    pagePane.getChildren().addAll(scene);
  }
}
