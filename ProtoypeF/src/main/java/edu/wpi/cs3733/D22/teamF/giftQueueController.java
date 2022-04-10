package edu.wpi.cs3733.D22.teamF;

import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * control for the queue for gifts
 *
 * @see Initializable
 */
public class giftQueueController implements Initializable {

  @FXML private ListView queue;
  ArrayList<String> list1 = new ArrayList();
  ArrayList<String> list2 = new ArrayList();
  ArrayList<String> list3 = new ArrayList();
  ArrayList<String> list4 = new ArrayList();
  ArrayList<String> list5 = new ArrayList();

  /**
   * Switch to the gift scene
   *
   * @param event ActionEvent
   */
  @FXML
  void switchToGift(ActionEvent event) {
    StageManager.getInstance().setDisplay("giftPage.fxml");
  }

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    list1.add("Jasmine");
    list1.add("Rose");
    list1.add("Teddy Bear");
    list1.add("Patient1");
    list1.add("101");
    list1.add("black");
    list1.add("doctor1");
    queue.getItems().add(list1.toString());
    list2.add("Gift Card");
    list2.add("Patient2");
    list2.add("102");
    list2.add("processing");
    list2.add("doctor2");
    queue.getItems().add(list2.toString());
    list3.add("Rose");
    list3.add("Jasmine");
    list3.add("Patient3");
    list3.add("103");
    list3.add("done");
    list3.add("doctor3");
    queue.getItems().add(list3.toString());
    list4.add("Chrysanthemum");
    list4.add("Jasmine");
    list4.add("Gift Card");
    list4.add("Teddy Bear");
    list4.add("Patient4");
    list4.add("104");
    list4.add("black");
    list4.add("doctor4");
    queue.getItems().add(list4.toString());
    list5.add("Chrysanthemum");
    list5.add("Rose");
    list5.add("Patient5");
    list5.add("105");
    list5.add("done");
    list5.add("doctor5");
    queue.getItems().add(list5.toString());
  }

  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("homePage.fxml");
  }
}
