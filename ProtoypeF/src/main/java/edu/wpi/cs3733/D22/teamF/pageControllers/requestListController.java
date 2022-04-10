package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * controller for request list
 *
 * @see Initializable
 */
public class requestListController implements Initializable {

  @FXML ListView requestList;

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /** adds items to the list to requestList ArrayList */
  public void populateList() {
    requestList.getItems().clear();
    for (ArrayList<Object> list : serviceRequestStorage.getArrayList()) {
      requestList.getItems().add(list);
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }
}
