package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/** MapManu controllers */
public class MapMenu extends MainMenu {

  public MapMenu(
      StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
    super(pageHolder, menu, homeMenu, mapMenu, serviceMenu);
  }

  /** changes to the map menu on the sidebar */
  @Override
  void changeTo() {
    menu.setSidePane(mapMenu);
    homeMenu.setVisible(false);
    serviceMenu.setVisible(false);
    mapMenu.setVisible(true);
    menu.setSidePane(mapMenu);
  }
}
