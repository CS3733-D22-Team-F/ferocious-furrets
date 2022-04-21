package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import edu.wpi.cs3733.D22.teamF.Exceptions.*;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapIconModifier;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.SceneManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import lombok.SneakyThrows;

public class MainController implements Initializable {

  @FXML StackPane pageHolder;
  @FXML StackPane stackHolder;
  @FXML StackPane mainPane;
  @FXML JFXDrawer menu;
  @FXML VBox menuBar;
  @FXML VBox homeMenu;
  @FXML VBox mapMenu;
  @FXML VBox serviceMenu;
  @FXML JFXButton mapButton;
  @FXML JFXButton serviceButton;
  @FXML JFXButton linksButton;
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
  @FXML JFXButton employeeButton;
  @FXML JFXButton queueButton;
  @FXML JFXButton landingButton;

  @FXML VBox v1;
  @FXML VBox v2;
  @FXML VBox v3;
  @FXML VBox v4;
  @FXML VBox v5;
  @FXML VBox v6;

  ChangeListener<Boolean> maxScreenCallback;
  ObservableList<Transform> baseTransformsScene;
  ObservableList<Transform> baseTransformsMenu;

  //  Timeline timeline =
  //      new Timeline(
  //          new KeyFrame(
  //              Duration.seconds(10),
  //              event -> {
  //                try {
  //                  DatabaseManager.getInstance().backUpDatabaseToCSV();
  //                } catch (SQLException | IOException e) {
  //                  e.printStackTrace();
  //                }
  //              }));

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Thread shutDownHook =
        new Thread(
            () -> {
              try {
                DatabaseManager.getInstance().backUpDatabaseToCSV();
              } catch (SQLException | IOException e) {
                e.printStackTrace();
              }
            });
    Runtime.getRuntime().addShutdownHook(shutDownHook);
    mapMenu.setVisible(false);
    serviceMenu.setVisible(false);
    baseTransformsScene = pageHolder.getTransforms();
    baseTransformsMenu = stackHolder.getTransforms();
    menu.setSidePane(homeMenu);
    onOpen();
    menuClose();
    SubScene scene = SceneManager.getInstance().setScene("views/dashboardPage.fxml");
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);

    maxScreenCallback =
        new ChangeListener<Boolean>() {
          @Override
          public void changed(
              ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            applyResize(newValue);
          }
        };
    SceneManager.getInstance().getStage().maximizedProperty().addListener(maxScreenCallback);
    SceneManager.getInstance().getStage().fullScreenProperty().addListener(maxScreenCallback);

    // TODO: make this work
    //    SceneManager.getInstance()
    //        .getStage()
    //        .widthProperty()
    //        .addListener(
    //            new ChangeListener<Number>() {
    //              @Override
    //              public void changed(
    //                  ObservableValue<? extends Number> observable, Number oldValue, Number
    // newValue) {
    //                applyResize((double) newValue,
    // SceneManager.getInstance().getStage().getHeight());
    //              }
    //            });
    //    SceneManager.getInstance()
    //        .getStage()
    //        .heightProperty()
    //        .addListener(
    //            new ChangeListener<Number>() {
    //              @Override
    //              public void changed(
    //                  ObservableValue<? extends Number> observable, Number oldValue, Number
    // newValue) {
    //                applyResize(SceneManager.getInstance().getStage().getWidth(), (double)
    // newValue);
    //              }
    //            });
    //    v2.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v4.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    v6.prefHeightProperty().bind(menu.heightProperty().divide(11));
    //    timeline.setCycleCount(Timeline.INDEFINITE);
    //    timeline.play();
  }

  private void applyResize(boolean newValue) {
    //    pageHolder.getTransforms().setAll(baseTransforms);
    //    menu.getTransforms().setAll(baseTransformsScene);
    stackHolder.getTransforms().setAll(baseTransformsMenu);
    homeMenu.getTransforms().setAll(baseTransformsMenu);
    mapMenu.getTransforms().setAll(baseTransformsMenu);
    serviceMenu.getTransforms().setAll(baseTransformsMenu);
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    if (newValue) {
      double maxWidth = screenBounds.getWidth() - 30;
      double maxHeight = screenBounds.getHeight();
      double contentWidth = stackHolder.getWidth();
      double contentHeight = stackHolder.getHeight();
      double widthRatio = maxWidth / contentWidth;
      double heightRatio = maxHeight / contentHeight;
      double preservedAspectRatio = Math.min(widthRatio, heightRatio);
      //      menu.getTransforms().add(new Scale(1, (float) preservedAspectRatio));
      stackHolder
          .getTransforms()
          .add(new Scale((float) preservedAspectRatio, (float) preservedAspectRatio * 1.15));

    } else {
      stackHolder.getTransforms().add(new Scale(1, 1));
    }
  }

  private void applyResize(double width, double height) {
    // TODO: Make this work
    pageHolder.getTransforms().setAll(baseTransformsScene);

    double maxWidth = width;
    double maxHeight = height;
    double contentWidth = SceneManager.getInstance().getStage().widthProperty().get();
    double contentHeight = SceneManager.getInstance().getStage().heightProperty().get();
    double widthRatio = maxWidth / contentWidth;
    double heightRatio = maxHeight / contentHeight;
    double preservedAspectRatio = Math.min(widthRatio, heightRatio);
    pageHolder
        .getTransforms()
        .add(new Scale((float) preservedAspectRatio, (float) preservedAspectRatio));
  }

  public void menuClose() throws InterruptedException {
    menu.close();
    menu.setPrefWidth(50);
    homeMenu.setPrefWidth(50);
    mapMenu.setPrefWidth(50);
    serviceMenu.setPrefWidth(50);
    mapButton.setGraphic(MapIconModifier.getIcon("infoMenu"));
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
    homeButton1.setGraphic(MapIconModifier.getIcon("backMenu"));
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
    homeButton2.setGraphic(MapIconModifier.getIcon("backMenu"));
    homeButton2.setText("");
    employeeButton.setGraphic(MapIconModifier.getIcon("employeeMenu"));
    employeeButton.setText("");
    queueButton.setGraphic(MapIconModifier.getIcon("queueMenu"));
    queueButton.setText("");
    linksButton.setGraphic(MapIconModifier.getIcon("linkMenu"));
    linksButton.setText("");
    landingButton.setGraphic(MapIconModifier.getIcon("apiMenu"));
    landingButton.setText("");
  }

  public void menuOpen() {
    menu.open();
    menu.setMaxWidth(200);
    mapButton.setText("Info");
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
    queueButton.setText("Queue");
    employeeButton.setText("Employee");
    linksButton.setText("Helpful Links");
    landingButton.setText("Landing");
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

  public void changeToLinks() throws IOException {
    changeTo("views/linksPage.fxml");
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

  public void changeToExtPatient() throws IOException {
    changeTo("views/extPatDeliveryPage.fxml");
  }

  public void changeToGift() throws IOException {
    changeTo("views/giftPageResized.fxml");
  }

  public void changeToMeal() throws IOException {
    changeTo("views/mealPage.fxml");
  }

  public void changeToMedicine() throws IOException, ServiceException {
    MedicineRequest.run(0, 0, 0, 0, "stylesheets/RequestPages.css", "FDEPT00301");
  }

  public void changeToDashboard() throws IOException {
    changeTo("views/dashboardPage.fxml");
  }

  public void changeToSetting() throws IOException {
    changeTo("views/settings.fxml");
  }

  public void changeToQueue() throws IOException {
    changeTo("views/requestListPage.fxml");
  }

  public void changeToEmployee() throws IOException {
    changeTo("views/employee/employeePage.fxml");
  }

  public void changeToSecurity() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/securityPage.fxml");
  }

  public void changeToPhysical() throws IOException {
    changeTo("views/physicalTherapyPage.fxml");
  }

  public void changeToLanding() throws IOException {
    changeTo("views/landingPage.fxml");
  }

  public void changeToFacilities() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/facilitiesPage.fxml");
  }

  public void changeToMaintenance() throws IOException {
    // TODO add your page name before ".fxml"
    changeTo("views/request/maintenanceRequestPage.fxml");
  }

  public void goToTransport() throws IOException {
    changeTo("views/extPatDeliveryPage.fxml");
  }

  public void changeTo(String path) throws IOException {
    SubScene scene = SceneManager.getInstance().setScene(path);
    pageHolder.getChildren().clear();
    pageHolder.getChildren().addAll(scene);
  }

  public void exit() throws SQLException, IOException {
    DatabaseManager.getInstance().backUpDatabaseToCSV();
    System.exit(0);
  }

  public void onOpen() {
    homeMenu.setMaxWidth(200);
    mapMenu.setMaxWidth(200);
    serviceMenu.setMaxWidth(200);
    homeMenu.setPrefWidth(200);
    mapMenu.setPrefWidth(200);
    serviceMenu.setPrefWidth(200);
  }

  public void onClose() {
    menu.setMaxWidth(50);
  }
}
