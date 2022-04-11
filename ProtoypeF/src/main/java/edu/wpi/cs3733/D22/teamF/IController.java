package edu.wpi.cs3733.D22.teamF;

import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;

// Interface for all pages
public interface IController extends Initializable {

  /** clear user inputed fields of a page */
  public void clear();

  /**
   * Makes a context menu for a specific page
   *
   * @return a context menu
   */
  public ContextMenu makeContextMenu();

  /**
   * Makes a menu bar
   *
   * @return a menu bar specific to the page
   */
  public MenuBar makeMenuBar();
}
