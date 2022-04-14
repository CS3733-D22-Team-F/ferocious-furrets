package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

public class mainController implements Initializable {

  @FXML StackPane pageHolder;
  @FXML JFXDrawer menu;
  @FXML VBox homeMenu;
  @FXML VBox mapMenu;
  @FXML VBox serviceMenu;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mapMenu.setVisible(false);
    serviceMenu.setVisible(false);
    menu.setSidePane(homeMenu);
    menu.close();
    SubScene scene = SceneManager.getInstance().setScene("views/mapPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void menuClose() {
    menu.close();
  }

  public void menuOpen() {
    menu.open();
  }

  public void changeToHomeMenu() throws IOException {
    menu.setSidePane(homeMenu);
    homeMenu.setVisible(true);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(false);
    menu.setSidePane(homeMenu);
  }

  public void changeToMapMenu() throws IOException {
    menu.setSidePane(mapMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(true);
    menu.setSidePane(mapMenu);
  }

  public void changeToMap() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/mapPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToServiceMenu() throws IOException {
    menu.setSidePane(serviceMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(true);
    mapMenu.setVisible(false);
    menu.setSidePane(serviceMenu);
  }

  public void changeToLab() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/labRequestPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToScan() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/scanPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToEquipment() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/equipmentPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToAudio() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/audioVisualPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToGift() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/giftPageResized.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToMeal() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/mealPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToMedicine() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/medicinePage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }
}