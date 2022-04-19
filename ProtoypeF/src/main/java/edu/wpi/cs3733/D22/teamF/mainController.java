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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class mainController implements Initializable {

  @FXML StackPane pageHolder;
  @FXML StackPane mainPane;
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
  @FXML JFXButton physicalButton;
  @FXML JFXButton securityButton;
  @FXML JFXButton sanitationButton;
  @FXML JFXButton maintenanceButton;
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
    serviceMenu.maxHeightProperty().bind(mainPane.heightProperty());
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    SceneManager.getInstance()
        .getStage()
        .fullScreenProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observable,
                  Boolean oldValue,
                  Boolean newValue) {
                double newHeight = screenBounds.getHeight() / pageHolder.getHeight();
                double newWidth = screenBounds.getWidth() / pageHolder.getWidth();
                if (newValue) {
                  System.out.println("W: " + newWidth + " H: " + newHeight);
                  pageHolder.getTransforms().add(new Scale(newWidth, newHeight));
                } else {

                  pageHolder.getTransforms().add(new Scale(1 / newWidth, 1 / newHeight));
                }
              }
            });
    //    v2.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v4.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v6.prefHeightProperty().bind(menu.heightProperty().divide(11));
  }

  public void menuClose() {
    menu.close();
    menu.setPrefWidth(50);
    homeMenu.setPrefWidth(50);
    mapMenu.setPrefWidth(50);
    serviceMenu.setPrefWidth(50);
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
    physicalButton.setGraphic(MapIconModifier.getIcon("physicalMenu"));
    physicalButton.setText("");
    securityButton.setGraphic(MapIconModifier.getIcon("securityMenu"));
    securityButton.setText("");
    sanitationButton.setGraphic(MapIconModifier.getIcon("cleanMenu"));
    sanitationButton.setText("");
    maintenanceButton.setGraphic(MapIconModifier.getIcon("toolMenu"));
    maintenanceButton.setText("");
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
    physicalButton.setText("Phy. Therapy");
    securityButton.setText("Security");
    sanitationButton.setText("Sanitation");
    maintenanceButton.setText("Maintenance");
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
    changeTo("views/mapPage.fxml");
  }

  public void changeToServiceMenu() throws IOException {
    menu.setSidePane(serviceMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(true);
    mapMenu.setVisible(false);
  }

  public void changeToLab() throws IOException {
    changeTo("views/labRequestPage.fxml");
  }

  public void changeToScan() throws IOException {
    changeTo("views/scanPage.fxml");
  }

  public void changeToEquipment() throws IOException {
    changeTo("views/equipmentPage.fxml");
  }

  public void changeToAudio() throws IOException {
    changeTo("views/audioVisualPage.fxml");
  }

  public void changeToGift() throws IOException {
    changeTo("views/giftPageResized.fxml");
  }

  public void changeToMeal() throws IOException {
    changeTo("views/mealPage.fxml");
  }

  public void changeToMedicine() throws IOException {
    changeTo("views/medicinePage.fxml");
  }

  public void changeToDashboard() throws IOException {
    changeTo("views/dashboardPage.fxml");
  }

  public void changeToSetting() throws IOException {
    changeTo("views/settings.fxml");
  }

  public void changeToSecurity() throws IOException {
    // TODO add your page name before ".fxml"
    // changeTo("views/.fxml");
  }

  public void changeToPhysical() throws IOException {
    changeTo("views/physicalTherapyPage.fxml");
  }

  public void changeToSanitation() throws IOException {
    // TODO add your page name before ".fxml"
    // changeTo("views/.fxml");
  }

  public void changeToMaintenance() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/request/maintenanceRequestPage.fxml");
  }

  public void changeTo(String path) throws IOException {
    SubScene scene = SceneManager.getInstance().setScene(path);
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void exit() throws SQLException, IOException {
    DatabaseManager.backUpDatabaseToCSV();
    System.exit(0);
  }
}
