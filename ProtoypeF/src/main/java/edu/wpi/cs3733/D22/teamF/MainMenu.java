package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/** abstract for the menu side bars */
public abstract class MainMenu {

  StackPane pageHolder;
  JFXDrawer menu;
  VBox homeMenu;
  VBox mapMenu;
  VBox serviceMenu;

  /**
   * Contructor
   *
   * @param pageHolder StackPane
   * @param menu JFXDrawer
   * @param homeMenu VBox
   * @param mapMenu VBox
   * @param serviceMenu VBox
   */
  public MainMenu(
      StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
    this.pageHolder = pageHolder;
    this.menu = menu;
    this.homeMenu = homeMenu;
    this.mapMenu = mapMenu;
    this.serviceMenu = serviceMenu;
  }

  abstract void changeTo();
}
