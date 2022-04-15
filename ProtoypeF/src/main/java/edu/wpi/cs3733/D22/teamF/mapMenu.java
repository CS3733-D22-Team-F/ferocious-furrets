package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class mapMenu extends mainMenu {

  public mapMenu(StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
    super(pageHolder, menu, homeMenu, mapMenu, serviceMenu);
  }

  @Override
  void changeTo() {
    menu.setSidePane(mapMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(true);
    menu.setSidePane(mapMenu);
  }
}
