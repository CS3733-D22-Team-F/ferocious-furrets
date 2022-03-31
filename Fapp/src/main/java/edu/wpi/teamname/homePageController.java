package edu.wpi.teamname;

import fxml.controllers.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class homePageController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private Button signInButton;
  @FXML private AnchorPane reportAnchorPane;
  // Theme Color: #154487
  // Button Color: #062558

  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("labRequestPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToMap(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("mapPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToEquipment(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("equipmentPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToMedicine(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("medicinePage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToGift(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("giftPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToMeals(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("mealPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToLogin(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("logInPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void returnToEquipment(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("equipmentPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void returnToLab(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("labRequestPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void returnToMedicine(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("medicinePage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void returnToMeals(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("mealPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void returnToGifts(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("giftPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    Stage window = (Stage) reportAnchorPane.getScene().getWindow();
    window.setScene(ReportManagerScene);
    window.show();
  }

  @FXML
  public void exitProgram() {
    System.exit(0);
  }
}
