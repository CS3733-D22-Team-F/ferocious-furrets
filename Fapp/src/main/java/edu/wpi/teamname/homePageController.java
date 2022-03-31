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
  // @FXML private MenuItem equipmentNavigation;

  // Theme Color: #154487
  // Button Color: #062558

  @FXML
  void switchToLab(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("labRequestPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToMap(ActionEvent event) throws IOException {
    scene = SceneManager.getInstance().setScene("mapPage.fxml");
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToEquipment(ActionEvent event) throws IOException {
    try {
      root = FXMLLoader.load((getClass().getResource("equipmentPage.fxml")));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root, 1280, 720);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void switchToMedicine(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("medicinePage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  void switchToGift(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("giftPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToMeals(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("mealPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToLogin(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("logInPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void gotoEquipment(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("equipmentPage.fxml"));
    Parent ReportManager = loader.load();
    Scene ReportManagerScene = new Scene(ReportManager);
    stage = (Stage) reportAnchorPane.getScene().getWindow();
    stage.setScene(ReportManagerScene);
    stage.show();
  }

  @FXML
  public void exitProgram() {
    System.exit(0);
  }
}
