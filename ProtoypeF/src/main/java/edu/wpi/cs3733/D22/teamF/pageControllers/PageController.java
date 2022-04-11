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

  public void menu_Medecine() {
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
   * Creates the main menu bar for the entire application except the login screen
   *
   * @param masterPane the main pane of a page
   * @return
   */
  public MenuBar makeMenuBar(Pane masterPane) {
    MenuBar pageMenu = new MenuBar();
    Menu navigator = new Menu("Navigation");

    // pageMenu.prefWidthProperty().bindBidirectional(masterPane.widthProperty(),NumberFormat.getNumberInstance());
    pageMenu.setMinWidth(2000);

    MenuItem home = this.addMenuItem("homePage.fxml");
    home.setText("home");
    MenuItem equip = this.addMenuItem("equipmentPage.fxml");
    equip.setText("Equipment Request");
    MenuItem gift = this.addMenuItem("giftPage.fxml");
    gift.setText("Gift Request");
    // TODO add an if statement for admin here using John's code for line 68- 69
    MenuItem map = this.addMenuItem("mapPage.fxml");
    map.setText("Map Request");
    MenuItem medicine = this.addMenuItem("medicinePage.fxml");
    medicine.setText("Medicine Request");
    MenuItem medical = this.addMenuItem("medicalPage.fxml");
    medical.setText("Medical Request");

    // a menu in the menubar for navigating between pages
    navigator.getItems().addAll(home, equip, gift, map, medicine, medical);

    // adding menus into the menu bar
    pageMenu.getMenus().add(navigator);

    // adding menu bar to the main pane of a page
    masterPane.getChildren().add(pageMenu);

    return pageMenu;
  }

  /**
   * abstraction of Stage manger display to switch scenes for
   *
   * @param fileName name of page to switch to
   */
  public void switchScene(String fileName) {
    StageManager.getInstance().setDisplay(fileName);
  }

  /** abstaction of StageManager to getback to home */
  public void switchToHome() {
    StageManager.getInstance().setHomeScreen();
  }
}
