package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** control for the home page */
public class homePageController extends PageController {

  @FXML private AnchorPane reportAnchorPane;
  // Theme Color: #154487
  // Button Color: #062558

  /**
   * switch to medical scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToMedical(ActionEvent event) throws IOException {
    // TODO fix
    StageManager.getInstance().setDisplay("medicalPage.fxml");
  }

  @FXML
  void switchToEmployee(ActionEvent event) throws IOException {
    //    if (!UserType.getUserType().equals("admin")) {
    //      Alert alert = new Alert(Alert.AlertType.ERROR);
    //      alert.setTitle("ERROR");
    //      alert.setHeaderText("You do not have access to this feature");
    //      alert.setContentText("Please consult an administrator");
    //      alert.showAndWait();
    //    } else {
    //      StageManager.getInstance().setDisplay("employee/employeePage.fxml");
    //    }
    StageManager.getInstance().setDisplay("employee/employeePage.fxml");
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
    StageManager.getInstance().setDisplay("giftPage.fxml");
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
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
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  /**
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToRequests(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("requestListPage.fxml");
  }

  @FXML
  public void switchToLanding(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("landingPage.fxml");
  }

  public void switchToLogin(ActionEvent event) {
    StageManager.getInstance().setDisplay("logInPage.fxml");
  }

  /** exits */
  @FXML
  public void exitProgram() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
