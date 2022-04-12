package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/** abstract class for all pages in application*/
public abstract class PageController {
  protected ContextMenu contextMenu;
  protected MenuBar menuBar;

  public PageController() {}
  /**
   * Creates a PageController
   *
   * @param c_menu the pages context menu
   * @param m_menu the pages menu bar
   */
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
  // All methods for switching between all pages specifically for scene builder
  public void menu_Home() {
    switchScene("landingPage.fxml");
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

  public void menu_Medicine() {
    switchScene("medicinePage.fxml");
  }

  public void menu_Medical() {
    switchScene("medicalPage.fxml");
  }
  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public abstract ContextMenu makeContextMenu();

  /**
   * implemented make a menu bar from a Pane
   * @param masterPane Pane
   * @return MenuBar
   */
  public MenuBar makeMenuBar(Pane masterPane) {
    MenuBar pageMenu = new MenuBar();
    Menu navigator = new Menu("Navigate");
    Menu serviceRequests = new Menu("Service Requests");
    Menu map_Navigate = new Menu("Go to Map");
    // pageMenu.prefWidthProperty().bindBidirectional(masterPane.widthProperty(),NumberFormat.getNumberInstance());
    pageMenu.setMinWidth(1500);
    // TODO switch to home page at end
    // Navigate Menus
    MenuItem home = this.addMenuItem("landingPage.fxml");
    home.setText("Home Page");
    // Map
    MenuItem map = this.addMenuItem("mapPage.fxml");
    // TODO add an if statement for admin here using John's code for line 68- 69
    map.setText("Map Request");

    // Serivce Requst Menus
    MenuItem audioVisual = this.addMenuItem("audioVisualPage.fxml");
    audioVisual.setText("Audio/Visual Request");
    MenuItem equip = this.addMenuItem("equipmentPage.fxml");
    equip.setText("Equipment Request");
    MenuItem gift = this.addMenuItem("giftPage.fxml");
    gift.setText("Gift Request");
    MenuItem labs = this.addMenuItem("labRequestPage.fxml");
    labs.setText("Labs Request");
    MenuItem meals = this.addMenuItem("mealPage.fxml");
    meals.setText("Meals Request");
    MenuItem medical = this.addMenuItem("medicalPage.fxml");
    medical.setText("Medical Request");
    MenuItem medicine = this.addMenuItem("medicinePage.fxml");
    medicine.setText("Medicine Request");
    MenuItem scans = this.addMenuItem("scanPage.fxml");
    scans.setText("Scans Request");
    MenuItem allRequests = this.addMenuItem("requestListPage.fxml");
    allRequests.setText("All Requests List");

    // adding options to menus in the menubar
    navigator.getItems().addAll(home);
    map_Navigate.getItems().addAll(map);
    serviceRequests
        .getItems()
        .addAll(audioVisual, equip, gift, labs, meals, medicine, medical, allRequests);

    // adding menus into the menu bar
    pageMenu.getMenus().addAll(navigator, serviceRequests, map_Navigate);

    // adding menu bar to the main pane of a page
    masterPane.getChildren().add(pageMenu);

    return pageMenu;
  }

  /**
   * abstraction of Stage manager display to switch scenes for
   * @param fileName name of page to switch to
   * switch to scene passed in
   * @param fileName String scene to switch to
   */
  public void switchScene(String fileName) {
    StageManager.getInstance().setDisplay(fileName);
  }

  /** abstaction of StageManager to getback to home */
  /**
   * switch to home scene
   */
  public void switchToHome() {
    StageManager.getInstance().setHomeScreen();
  }
}
