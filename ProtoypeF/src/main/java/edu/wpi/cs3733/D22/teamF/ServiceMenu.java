package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ServiceMenu extends MainMenu {

  public ServiceMenu(
      StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
    super(pageHolder, menu, homeMenu, mapMenu, serviceMenu);
  }

  @Override
  void changeTo() {
    menu.setSidePane(serviceMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(true);
    mapMenu.setVisible(false);
    menu.setSidePane(serviceMenu);
  }
}
