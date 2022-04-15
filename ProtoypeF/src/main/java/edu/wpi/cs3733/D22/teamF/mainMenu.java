package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public abstract class mainMenu {

  StackPane pageHolder;
  JFXDrawer menu;
  VBox homeMenu;
  VBox mapMenu;
  VBox serviceMenu;

  public mainMenu(StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu){
    this.pageHolder = pageHolder;
    this.menu = menu;
    this.homeMenu = homeMenu;
    this.mapMenu = mapMenu;
    this.serviceMenu = serviceMenu;
  }
  abstract void changeTo();
}
