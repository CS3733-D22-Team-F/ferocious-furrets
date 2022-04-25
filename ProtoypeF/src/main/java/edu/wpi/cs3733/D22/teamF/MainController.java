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

/** Controller for the main menu sidebar */
public class MainController implements Initializable {

  @FXML StackPane pageHolder;
  @FXML StackPane stackHolder;
  @FXML StackPane mainPane;
  @FXML JFXDrawer menu;
  @FXML VBox homeMenu;
  @FXML VBox serviceMenu;
  @FXML VBox titleBox;
  @FXML JFXButton serviceButton;
  @FXML JFXButton linksButton;
  @FXML JFXButton settingsButton;
  @FXML JFXButton outButton;
  @FXML JFXButton mapViewButton;
  @FXML JFXButton dashboardButton;

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
  @FXML JFXButton patientButton;

  @FXML VBox v1;
  @FXML VBox v2;
  @FXML VBox v5;

  ChangeListener<Boolean> maxScreenCallback;
  ChangeListener<Number> widthResizeCallback;
  ChangeListener<Number> heightResizeCallback;
  ObservableList<Transform> baseTransformsTop;
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
    // SceneManager.getInstance().getStage().getScene().getStylesheets().clear();
    //    SceneManager.getInstance().getStage().getScene().getStylesheets().remove();
    //    SceneManager.getInstance().getStage().getScene().getStylesheets().add();

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
    titleBox.maxWidthProperty().bind(SceneManager.getInstance().getStage().widthProperty());
    serviceMenu.setVisible(false);
    baseTransformsTop = titleBox.getTransforms();
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
    widthResizeCallback =
        new ChangeListener<Number>() {
          @Override
          public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            applyResize((double) newValue, SceneManager.getInstance().getStage().getHeight());
          }
        };

    heightResizeCallback =
        new ChangeListener<Number>() {
          @Override
          public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            applyResize(SceneManager.getInstance().getStage().getWidth(), (double) newValue);
          }
        };

    SceneManager.getInstance().getStage().maximizedProperty().addListener(maxScreenCallback);
    SceneManager.getInstance().getStage().fullScreenProperty().addListener(maxScreenCallback);
    SceneManager.getInstance().getStage().widthProperty().addListener(widthResizeCallback);
    SceneManager.getInstance().getStage().heightProperty().addListener(heightResizeCallback);
  }

  /**
   * resizes the menu, takes in a boolean if true resizes
   *
   * @param newValue boolean
   */
  private void applyResize(boolean newValue) {
    //    pageHolder.getTransforms().setAll(baseTransforms);
    titleBox.getTransforms().setAll(baseTransformsTop);
    stackHolder.getTransforms().setAll(baseTransformsMenu);
    homeMenu.getTransforms().setAll(baseTransformsMenu);
    serviceMenu.getTransforms().setAll(baseTransformsMenu);
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    if (newValue) {
      double maxWidth = screenBounds.getWidth();
      double maxHeight = screenBounds.getHeight();
      double contentWidth = stackHolder.getPrefWidth();
      double contentHeight = stackHolder.getPrefHeight();
      double widthRatio = maxWidth / contentWidth;
      double heightRatio = maxHeight / contentHeight;
      double preservedAspectRatio = Math.min(widthRatio, heightRatio);
      //      menu.getTransforms().add(new Scale(1, (float) preservedAspectRatio));
      menu.minHeightProperty().bind(stackHolder.heightProperty());
      stackHolder.getTransforms().add(new Scale((float) widthRatio, (float) (heightRatio * 0.9)));
    } else {
      stackHolder.getTransforms().add(new Scale(1, 1));
    }
  }

  /**
   * Resizes taking in width and height doubles
   *
   * @param width double
   * @param height double
   */
  private void applyResize(double width, double height) {
    // TODO: Make this work
    stackHolder.getTransforms().setAll(baseTransformsMenu);
    titleBox.getTransforms().setAll(baseTransformsTop);
    homeMenu.getTransforms().setAll(baseTransformsMenu);
    serviceMenu.getTransforms().setAll(baseTransformsMenu);
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double maxWidth = width;
    double maxHeight = height;
    double contentWidth = stackHolder.getPrefWidth();
    double contentHeight = stackHolder.getPrefHeight();
    double widthRatio = maxWidth / contentWidth;
    double heightRatio = maxHeight / contentHeight;
    stackHolder.getTransforms().add(new Scale((float) widthRatio, (float) (heightRatio * 0.9)));
    titleBox.getTransforms().add(new Scale((float) widthRatio, 1));
  }

  public void menuClose() throws InterruptedException {
    menu.close();
    menu.setPrefWidth(50);
    homeMenu.setPrefWidth(50);
    serviceMenu.setPrefWidth(50);
    serviceButton.setGraphic(MapIconModifier.getIcon("serviceMenu"));
    serviceButton.setText("");
    settingsButton.setGraphic(MapIconModifier.getIcon("settingsMenu"));
    settingsButton.setText("");
    outButton.setGraphic(MapIconModifier.getIcon("outMenu"));
    outButton.setText("");
    mapViewButton.setGraphic(MapIconModifier.getIcon("mapMenu"));
    mapViewButton.setText("");
    dashboardButton.setGraphic(MapIconModifier.getIcon("dashboardMenu"));
    dashboardButton.setText("");
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
    patientButton.setGraphic(MapIconModifier.getIcon("patientMenu"));
    patientButton.setText("");
  }

  public void menuOpen() {
    menu.setMaxWidth(200);
    menu.open();
    serviceButton.setText("Service");
    settingsButton.setText("Settings");
    outButton.setText("Exit");
    mapViewButton.setText("Map View");
    dashboardButton.setText("Dashboard");
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
    linksButton.setText("Links");
    landingButton.setText("Landing");
    patientButton.setText("Pt. Transport");
  }

  public void changeToHomeMenu() throws IOException {
    menu.setSidePane(homeMenu);
    homeMenu.setVisible(true);
    serviceMenu.setVisible(false);
  }

  public void changeToMap() throws IOException {
    changeTo("views/mapPage.fxml");
  }

  public void changeToServiceMenu() throws IOException {
    menu.setSidePane(serviceMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(true);
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
    changeTo("views/maintenanceRequestPage.fxml");
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
    serviceMenu.setMaxWidth(200);
    homeMenu.setPrefWidth(200);
    serviceMenu.setPrefWidth(200);
  }

  public void onClose() {
    menu.setMaxWidth(50);
  }
}
