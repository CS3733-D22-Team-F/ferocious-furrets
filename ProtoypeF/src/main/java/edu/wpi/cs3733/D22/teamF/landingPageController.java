package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Controller for the landing page */
public class landingPageController implements Initializable {

  private Stage stage;
  private Scene scene;
  private Parent root;
  boolean toggleOff = false;

  @FXML private BorderPane reportBorderPane;
  @FXML private Label nikolaLabel;
  @FXML private Label azuLabel;
  @FXML private Label johnnikolaLabel;
  @FXML private Label evansLabel;
  @FXML private Label carterLabel;
  @FXML private Label johnScan;
  @FXML private Label nikolaLabLabel;
  @FXML private JFXToggleButton toggleButton;
  @FXML private Label raffiLabel;
  @FXML private Label nikLanding;

  /**
   * switch to the medical scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToMedical(ActionEvent event) throws IOException {
    // TODO fix
    StageManager.getInstance().setDisplay("medicalPage.fxml");
  }

  /**
   * switch to map scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToMap(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("mapPage.fxml");
  }

  /**
   * switch to equipment scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToEquipment(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("equipmentPage.fxml");
  }

  /**
   * switch to medicine scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToMedicine(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("medicinePage.fxml");
  }

  /**
   * switch to gift scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToGift(ActionEvent event) throws IOException {
    // TODO: Switch back to og page
    StageManager.getInstance().setDisplay("giftPageResized.fxml");
  }

  /**
   * switch to meal scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToMeals(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("mealPage.fxml");
  }

  /**
   * switch to audio/Visual scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToAudioVis(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("audioVisualPage.fxml");
  }

  /**
   * return to the map scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToMap(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("mapPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * return to the equipment scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToEquipment(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("equipmentPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * return to the medical scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToMedical(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("medicalPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * return to medicine scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToMedicine(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("medicinePage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * return to meal scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToMeals(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("mealPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * return to gifts scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void returnToGifts(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("giftPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportBorderPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void returnToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplayNoViews("homePage.fxml");
  }

  @FXML
  public void returnToLab(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("labRequestPage.fxml");
  }

  @FXML
  public void returnToScan(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("scanPage.fxml");
  }

  /**
   * switch to the request scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToRequests(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("requestListPage.fxml");
  }

  /**
   * switch to the login scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToLogin(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("logInPage.fxml");
  }

  /**
   * toggle credits, in MLA format
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void toggleCredits(ActionEvent event) throws IOException {
    if (!toggleOff) {
      azuLabel.setVisible(false);
      nikolaLabel.setVisible(false);
      johnScan.setVisible(false);
      johnnikolaLabel.setVisible(false);
      evansLabel.setVisible(false);
      carterLabel.setVisible(false);
      toggleOff = true;
    } else if (toggleOff) {
      azuLabel.setVisible(true);
      nikolaLabel.setVisible(true);
      johnScan.setVisible(true);
      johnnikolaLabel.setVisible(true);
      evansLabel.setVisible(true);
      carterLabel.setVisible(true);
      toggleOff = false;
    }
  }

  /** exits */
  @FXML
  public void exitProgram() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nikLanding.setVisible(false);
    nikolaLabel.setVisible(false);
    johnnikolaLabel.setVisible(false);
    evansLabel.setVisible(false);
    carterLabel.setVisible(false);
    johnScan.setVisible(false);
    nikolaLabLabel.setVisible(false);
    raffiLabel.setVisible(false);

    toggleButton
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (toggleButton.isSelected() == true) {
                toggleButton.setText("Show Credits: ON");
                nikLanding.setVisible(true);
                nikolaLabel.setVisible(true);
                johnnikolaLabel.setVisible(true);
                evansLabel.setVisible(true);
                carterLabel.setVisible(true);
                johnScan.setVisible(true);
                nikolaLabLabel.setVisible(true);
                raffiLabel.setVisible(true);
              } else {
                toggleButton.setText("Show Credits: OFF");
                nikLanding.setVisible(false);
                nikolaLabel.setVisible(false);
                johnnikolaLabel.setVisible(false);
                evansLabel.setVisible(false);
                carterLabel.setVisible(false);
                johnScan.setVisible(false);
                nikolaLabLabel.setVisible(false);
                raffiLabel.setVisible(false);
              }
            });
  }
}
