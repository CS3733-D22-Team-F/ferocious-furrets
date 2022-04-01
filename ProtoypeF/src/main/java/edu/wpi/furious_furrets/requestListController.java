package edu.wpi.furious_furrets;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/** controll for request list */
public class requestListController extends returnHomePage implements Initializable {

  @FXML ListView requestList;

  /**
   * inits
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /** adds items to the list */
  public void populateList() {
    requestList.getItems().clear();
    for (ArrayList<Object> list : serviceRequestStorage.getArrayList()) {
      requestList.getItems().add(list);
    }
  }
}
