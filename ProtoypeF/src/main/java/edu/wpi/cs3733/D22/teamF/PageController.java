package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;

// abstract class for all pages in application
public abstract class PageController implements IController {

  private ContextMenu contextMenu;
  private MenuBar menuBar;

  public PageController(ContextMenu c_menu, MenuBar m_menu) {
    this.contextMenu = c_menu;
    this.menuBar = m_menu;
  }

  /** clear user inputed fields of a page */
  @Override
  public void clear() {}

  /**
   * Makes a context menu for a specific page
   *
   * @return a context menu
   */
  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /**
   * Makes a menu bar
   *
   * @return a menu bar specific to the page
   */
  @Override
  public MenuBar makeMenuBar() {
    return null;
  }

  /**
   * Switches scenes
   *
   * @param fileName
   */
  public void switchScene(String fileName) {
    StageManager.getInstance().setDisplay(fileName);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
