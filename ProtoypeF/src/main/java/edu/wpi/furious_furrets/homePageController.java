package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.fxml.SceneManager;
import edu.wpi.furious_furrets.controllers.general.DatabaseManager;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** control for the home page */
public class homePageController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private Button signInButton;
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
    scene = SceneManager.getInstance().setScene("medicalPage.fxml");

    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to map scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToMap(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("mapPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to equipment scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToEquipment(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("equipmentPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to medicine scene
   *
   * @param event ActionEvent
   * @throws IOException
   */
  @FXML
  void switchToMedicine(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("medicinePage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to gift scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void switchToGift(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("giftPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to meal scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToMeals(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("mealPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * switch to login scene
   *
   * @param event
   * @throws IOException
   */
  @FXML
  public void switchToLogin(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("logInPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
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
    scene = SceneManager.getInstance().setScene("requestListPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /** exits */
  @FXML
  public void exitProgram() throws SQLException {
    DatabaseManager.getLocationDAO().saveLocationToCSV();
    DatabaseManager.getMedEquipDelReqDAO().saveRequestToCSV();
    System.out.println("Locations table updated to csv :)");
    System.out.println("MedDelReq table updated to csv :)");
    System.exit(0);
  }
}
