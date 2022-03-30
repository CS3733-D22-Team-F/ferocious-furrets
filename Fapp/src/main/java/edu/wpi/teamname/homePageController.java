package edu.wpi.teamname;

import fxml.controllers.SceneManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class homePageController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private Button signInButton;
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
  public void exitProgram() {
    System.exit(0);
  }
}
