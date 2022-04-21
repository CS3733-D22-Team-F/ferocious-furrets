package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeMenu extends MainMenu {

  public HomeMenu(
      StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
    super(pageHolder, menu, homeMenu, mapMenu, serviceMenu);
  }

  @Override
  void changeTo() {
    menu.setSidePane(homeMenu);
    homeMenu.setVisible(true);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(false);
    menu.setSidePane(homeMenu);
  }
}
