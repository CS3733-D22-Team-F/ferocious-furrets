package edu.wpi.furious_furrets;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * controller for request list
 *
 * @see returnHomePage
 * @see Initializable
 */
public class requestListController extends returnHomePage implements Initializable {

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
}
