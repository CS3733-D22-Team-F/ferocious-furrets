package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

/** abstract class for all pages in application */
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
    switchScene("request/giftPageResized.fxml");
  }

  public void menu_Map() {
    switchScene("mapPage.fxml");
  }

  public void menu_Medicine() {
    switchScene("request/medicinePage.fxml");
  }

  public void menu_Medical() {
    switchScene("request/medicalPage.fxml");
  }
  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  public abstract ContextMenu makeContextMenu();

  /**
   * implemented make a menu bar from a Pane
   *
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
    MenuItem gift = this.addMenuItem("request/giftPageResized.fxml");
    gift.setText("Gift Request");
    MenuItem labs = this.addMenuItem("request/labRequestPage.fxml");
    labs.setText("Labs Request");
    MenuItem meals = this.addMenuItem("request/mealPage.fxml");
    meals.setText("Meals Request");
    MenuItem medical = this.addMenuItem("request/medicalPage.fxml");
    medical.setText("Medical Request");
    MenuItem medicine = this.addMenuItem("request/medicinePage.fxml");
    medicine.setText("Medicine Request");
    MenuItem scans = this.addMenuItem("request/scanPage.fxml");
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
   * implemented make a menu bar from a Pane
   *
   * @param masterPane BorderPane
   * @return MenuBar
   */
  public MenuBar makeMenuBar(BorderPane masterPane) {
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
    MenuItem audioVisual = this.addMenuItem("request/audioVisualPage.fxml");
    audioVisual.setText("Audio/Visual Request");
    MenuItem equip = this.addMenuItem("request/equipmentPage.fxml");
    equip.setText("Equipment Request");
    MenuItem gift = this.addMenuItem("request/giftPageResized.fxml");
    gift.setText("Gift Request");
    MenuItem labs = this.addMenuItem("request/labRequestPage.fxml");
    labs.setText("Labs Request");
    MenuItem meals = this.addMenuItem("request/mealPage.fxml");
    meals.setText("Meals Request");
    MenuItem medical = this.addMenuItem("request/medicalPage.fxml");
    medical.setText("Medical Request");
    MenuItem medicine = this.addMenuItem("request/medicinePage.fxml");
    medicine.setText("Medicine Request");
    MenuItem scans = this.addMenuItem("request/scanPage.fxml");
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
    masterPane.setTop(pageMenu);

    return pageMenu;
  }

  /**
   * abstraction of Stage manager display to switch scenes for
   *
   * @param fileName name of page to switch to switch to scene passed in
   * @param fileName String scene to switch to
   */
  public void switchScene(String fileName) {
    // StageManager.getInstance().setDisplay(fileName);
  }

  /** abstaction of StageManager to getback to home */
  /** switch to home scene */
  public void switchToHome() {
    // StageManager.getInstance().setHome();
  }

  public ArrayList<Object> locationNames() {
    ArrayList<Object> locations = new ArrayList<>();
    ResultSet r = null;
    try {
      r = DatabaseManager.getInstance().runQuery("SELECT LONGNAME FROM LOCATIONS");
      while (r.next()) {
        String name = r.getString("LONGNAME");
        locations.add(name);
      }
      r.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return locations;
  }

  public ArrayList<Object> employeeNames() {
    ArrayList<Object> employees = new ArrayList<>();
    ResultSet rset = null;
    try {
      rset = DatabaseManager.getInstance().runQuery("SELECT FIRSTNAME, LASTNAME FROM EMPLOYEE");
      while (rset.next()) {
        String first = rset.getString("FIRSTNAME");
        String last = rset.getString("LASTNAME");
        String name = last + ", " + first;
        employees.add(name);
      }
      rset.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }

  public String nodeIDFinder(String name) throws SQLException {
    String nodeID = "";
    String cmd = String.format("SELECT NODEID FROM LOCATIONS WHERE LONGNAME = '%s'", name);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      nodeID = rset.getString("NODEID");
    }
    rset.close();
    return nodeID;
  }

  public String employeeIDFinder(String name) throws SQLException, SQLException {
    String empID = "";
    String[] employeeName = name.split(",");
    String last = employeeName[0];
    String first = employeeName[1];
    last = last.strip();
    first = first.strip();
    String cmd =
        String.format(
            "SELECT EMPLOYEEID FROM EMPLOYEE WHERE FIRSTNAME = '%s' AND LASTNAME = '%s'",
            first, last);
    ResultSet rset = DatabaseManager.getInstance().runQuery(cmd);
    if (rset.next()) {
      empID = rset.getString("EMPLOYEEID");
    }
    rset.close();
    return empID;
  }

  public void showAlert(String info, Node random) {
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setBody(new Label(info));
    JFXAlert<Void> alert = new JFXAlert<>(random.getScene().getWindow());
    alert.setOverlayClose(true);
    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    alert.setContent(layout);
    alert.initModality(Modality.NONE);
    alert.showAndWait();
  }
}
