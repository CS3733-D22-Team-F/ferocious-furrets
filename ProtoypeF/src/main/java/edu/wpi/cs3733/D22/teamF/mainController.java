package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
  @FXML JFXButton mapButton;
  @FXML JFXButton serviceButton;
  @FXML JFXButton settingsButton;
  @FXML JFXButton outButton;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mapMenu.setVisible(false);
    serviceMenu.setVisible(false);
    menu.setSidePane(homeMenu);
    menuClose();
    SubScene scene = SceneManager.getInstance().setScene("views/mapPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void menuClose() {
    menu.close();
    menu.setMaxWidth(50);
    homeMenu.setMaxWidth(50);
    mapButton.setGraphic(MapIconModifier.getIcon("Bed"));
    mapButton.setText("");
    serviceButton.setGraphic(MapIconModifier.getIcon("Bed"));
    serviceButton.setText("");
    settingsButton.setGraphic(MapIconModifier.getIcon("Bed"));
    settingsButton.setText("");
    outButton.setGraphic(MapIconModifier.getIcon("Bed"));
    outButton.setText("");
  }

  public void menuOpen() {
    menu.open();
    menu.setMaxWidth(200);
    homeMenu.setMaxWidth(200);
    mapButton.setGraphic(null);
    mapButton.setText("Map");
    serviceButton.setGraphic(null);
    serviceButton.setText("Service");
    settingsButton.setGraphic(null);
    settingsButton.setText("Settings");
    outButton.setGraphic(null);
    outButton.setText("Exit");
  }

  public void changeToHomeMenu() throws IOException {
    menu.setSidePane(homeMenu);
    homeMenu.setVisible(true);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(false);
  }

  public void changeToMapMenu() throws IOException {
    menu.setSidePane(mapMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(true);
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

  public void changeToDashboard() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/dashboardPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeToSetting() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/settings.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void exit() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }
}
