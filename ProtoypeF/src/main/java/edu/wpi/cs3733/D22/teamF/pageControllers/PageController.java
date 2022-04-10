package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/// abstract class for all pages in application
public abstract class PageController {
  protected ContextMenu contextMenu;
  protected MenuBar menuBar;

  public PageController() {}

  public PageController(ContextMenu c_menu, MenuBar m_menu) {
    this.contextMenu = c_menu;
    this.menuBar = m_menu;
  }

  private MenuItem addMenuItem(String navigateTo) {
    MenuItem newMItem = new MenuItem();
    newMItem.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            switchScene(navigateTo);
          }
        });
    return newMItem;
  }
  // All methods for the menu bar to switch between pages
  public void menu_Home() {
    switchScene("homePage.fxml");
  }

  public void menu_Equipment() {
    switchScene("equipmentPage.fxml");
  }

  public void menu_Gift() {
    switchScene("giftPage.fxml");
  }

  public void menu_Map() {
    switchScene("mapPage.fxml");
  }

  public void menu_Medecine() {
    switchScene("medicinePage.fxml");
  }

  public void menu_Medical() {
    switchScene("medicalPage.fxml");
  }

  public abstract ContextMenu makeContextMenu();

  public MenuBar makeMenuBar(Pane masterPane) {
    MenuBar pageMenu = new MenuBar();
    Menu navigator = new Menu("Navigation");

    // pageMenu.prefWidthProperty().bindBidirectional(masterPane.widthProperty(),NumberFormat.getNumberInstance());
    pageMenu.setMinWidth(1000);

    MenuItem home = this.addMenuItem("homePage.fxml");
    home.setText("home");
    MenuItem equip = this.addMenuItem("equipmentPage.fxml");
    equip.setText("Equipment Request");
    MenuItem gift = this.addMenuItem("giftPage.fxml");
    gift.setText("Gift Request");
    MenuItem map = this.addMenuItem("mapPage.fxml");
    map.setText("Map Request");
    MenuItem medicine = this.addMenuItem("medicinePage.fxml");
    medicine.setText("Medicine Request");
    MenuItem medical = this.addMenuItem("medicalPage.fxml");
    medical.setText("Medical Request");

    navigator.getItems().addAll(home, equip, gift, map, medicine, medical);

    // add menus to menu bar
    pageMenu.getMenus().add(navigator);

    masterPane.getChildren().add(pageMenu);
    pageMenu.setPrefWidth(masterPane.getWidth());
    // pageMenu
    return pageMenu;
  }

  public void switchScene(String fileName) {
    StageManager.getInstance().setDisplay(fileName);
  }

  public void switchToHome() {
    StageManager.getInstance().setHomeScreen();
  }
}
