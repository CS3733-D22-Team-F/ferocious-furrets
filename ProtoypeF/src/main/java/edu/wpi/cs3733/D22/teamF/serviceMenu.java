package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class serviceMenu extends mainMenu{

    public serviceMenu(
            StackPane pageHolder, JFXDrawer menu, VBox homeMenu, VBox mapMenu, VBox serviceMenu) {
        this.pageHolder = pageHolder;
        this.menu = menu;
        this.homeMenu = homeMenu;
        this.mapMenu = mapMenu;
        this.serviceMenu = serviceMenu;
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
