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
  @FXML JFXButton settingsButton1;
  @FXML JFXButton outButton1;
  @FXML JFXButton settingsButton2;
  @FXML JFXButton outButton2;

  @FXML JFXButton mapViewButton;
  @FXML JFXButton dashboardButton;
  @FXML JFXButton homeButton1;

  @FXML JFXButton labButton;
  @FXML JFXButton scanButton;
  @FXML JFXButton equipmentButton;
  @FXML JFXButton mealButton;
  @FXML JFXButton audioButton;
  @FXML JFXButton medicineButton;
  @FXML JFXButton giftButton;
  @FXML JFXButton physicalTherapyButton;
  @FXML JFXButton homeButton2;

  @FXML VBox v1;
  @FXML VBox v2;
  @FXML VBox v3;
  @FXML VBox v4;
  @FXML VBox v5;
  @FXML VBox v6;

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
    // settingsButton.setMa
    v1.prefHeightProperty().bind(menu.heightProperty().divide(10).multiply(8));
    v3.prefHeightProperty().bind(menu.heightProperty().divide(10).multiply(8));
    v5.prefHeightProperty().bind(menu.heightProperty().divide(10).multiply(8));
    //    v2.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v4.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v6.prefHeightProperty().bind(menu.heightProperty().divide(11));
  }

  public void menuClose() {
    menu.close();
    menu.setMaxWidth(50);
    homeMenu.setPrefWidth(50);
    serviceMenu.setPrefWidth(50);
    mapMenu.setPrefWidth(50);
    mapButton.setGraphic(MapIconModifier.getIcon("mapMenu"));
    mapButton.setText("");
    serviceButton.setGraphic(MapIconModifier.getIcon("serviceMenu"));
    serviceButton.setText("");
    settingsButton.setGraphic(MapIconModifier.getIcon("settingsMenu"));
    settingsButton.setText("");
    outButton.setGraphic(MapIconModifier.getIcon("outMenu"));
    outButton.setText("");
    settingsButton1.setGraphic(MapIconModifier.getIcon("settingsMenu"));
    settingsButton1.setText("");
    outButton1.setGraphic(MapIconModifier.getIcon("outMenu"));
    outButton1.setText("");
    settingsButton2.setGraphic(MapIconModifier.getIcon("settingsMenu"));
    settingsButton2.setText("");
    outButton2.setGraphic(MapIconModifier.getIcon("outMenu"));
    outButton2.setText("");
    mapViewButton.setGraphic(MapIconModifier.getIcon("mapMenu"));
    mapViewButton.setText("");
    dashboardButton.setGraphic(MapIconModifier.getIcon("dashboardMenu"));
    dashboardButton.setText("");
    homeButton1.setGraphic(MapIconModifier.getIcon("home"));
    homeButton1.setText("");
    labButton.setGraphic(MapIconModifier.getIcon("labMenu"));
    labButton.setText("");
    scanButton.setGraphic(MapIconModifier.getIcon("scanMenu"));
    scanButton.setText("");
    equipmentButton.setGraphic(MapIconModifier.getIcon("equipmentMenu"));
    equipmentButton.setText("");
    medicineButton.setGraphic(MapIconModifier.getIcon("medicineMenu"));
    medicineButton.setText("");
    mealButton.setGraphic(MapIconModifier.getIcon("mealMenu"));
    mealButton.setText("");
    audioButton.setGraphic(MapIconModifier.getIcon("audioMenu"));
    audioButton.setText("");
    giftButton.setGraphic(MapIconModifier.getIcon("giftMenu"));
    giftButton.setText("");
    homeButton2.setGraphic(MapIconModifier.getIcon("home"));
    homeButton2.setText("");
  }

  public void menuOpen() {
    menu.open();
    menu.setMaxWidth(200);
    homeMenu.setMaxWidth(200);
    homeMenu.setPrefWidth(200);
    mapMenu.setMaxWidth(200);
    mapMenu.setPrefWidth(200);
    serviceMenu.setMaxWidth(200);
    serviceMenu.setPrefWidth(200);
    mapButton.setText("Map");
    serviceButton.setText("Service");
    settingsButton.setText("Settings");
    outButton.setText("Exit");
    settingsButton1.setText("Settings");
    outButton1.setText("Exit");
    settingsButton2.setText("Settings");
    outButton2.setText("Exit");
    mapViewButton.setText("Map View");
    dashboardButton.setText("Dashboard");
    homeButton1.setText("Home");
    labButton.setText("Lab");
    scanButton.setText("Scan");
    equipmentButton.setText("Equipment");
    medicineButton.setText("Medicine");
    mealButton.setText("Meals");
    audioButton.setText("Audio/Visual");
    giftButton.setText("Gift");
    homeButton2.setText("Home");
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

  public void changeToPhysicalTherapy() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/physicalTherapyPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void changeTOFacilities() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/facilitiesPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }


  public void changeToMedicine() throws IOException {
    SubScene scene = SceneManager.getInstance().setScene("views/facilitiesPage.fxml");
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
