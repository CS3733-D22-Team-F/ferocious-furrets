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

  ChangeListener<Boolean> maxScreenCallback;
  ObservableList<Transform> baseTransforms;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Thread shutDownHook =
        new Thread(
            () -> {
              try {
                DatabaseManager.backUpDatabaseToCSV();
              } catch (SQLException | IOException e) {
                e.printStackTrace();
              }
            });
    Runtime.getRuntime().addShutdownHook(shutDownHook);
    mapMenu.setVisible(false);
    serviceMenu.setVisible(false);
    baseTransforms = pageHolder.getTransforms();
    menu.setSidePane(homeMenu);
    menuClose();
    SubScene scene = SceneManager.getInstance().setScene("views/mapPage.fxml");
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
  }

  private void applyResize(boolean newValue) {
    pageHolder.getTransforms().setAll(baseTransforms);
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    if (newValue) {
      double maxWidth = screenBounds.getWidth();
      double maxHeight = screenBounds.getHeight();
      double contentWidth = SceneManager.getInstance().getStage().widthProperty().get();
      double contentHeight = SceneManager.getInstance().getStage().heightProperty().get();
      double widthRatio = maxWidth / contentWidth;
      double heightRatio = maxHeight / contentHeight;
      double preservedAspectRatio = Math.min(widthRatio, heightRatio);
      pageHolder
          .getTransforms()
          .add(new Scale((float) preservedAspectRatio, (float) preservedAspectRatio));

    } else {
      pageHolder.getTransforms().add(new Scale(1, 1));
    }
  }

  private void applyResize(double width, double height) {
    // TODO: Make this work
    pageHolder.getTransforms().setAll(baseTransforms);

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

  public void changeToMedicine() throws IOException, ServiceException {
    //    SubScene scene = SceneManager.getInstance().setScene("views/medicinePage.fxml");
    //    pageHolder.getChildren().clear();
    //    pageHolder.getChildren().addAll(scene);
    //    try {
    //      //user needs to create csv files first
    //      MedicineRequest.initializeDatabase(
    //          "/edu/wpi/cs3733/D22/teamF/apiCSV/medicine.csv",
    //          "/edu/wpi/cs3733/D22/teamF/apiCSV/employees.csv");
    //    } catch (SQLException e) {
    //      e.printStackTrace();
    //    }
    MedicineRequest.run(0, 0, 0, 0, "stylesheets/RequestPages.css", "FDEPT00301");
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
    // changeTo("views/.fxml");
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
