package edu.wpi.furious_furrets;

import edu.wpi.furious_furrets.controllers.SceneManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class labQueueController implements Initializable {

  @FXML private ListView queue;
  ArrayList<String> list1 = new ArrayList();
  ArrayList<String> list2 = new ArrayList();
  ArrayList<String> list3 = new ArrayList();
  ArrayList<String> list4 = new ArrayList();
  ArrayList<String> list5 = new ArrayList();

  @FXML
  void switchToLab(ActionEvent event) {
    Scene scene = SceneManager.getInstance().setScene("labRequestPage.fxml");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    list1.add("blood");
    list1.add("urine");
    list1.add("MRI");
    list1.add("Patient1");
    list1.add("101");
    list1.add("black");
    list1.add("doctor1");
    queue.getItems().add(list1.toString());
    list2.add("blood");
    list2.add("Patient2");
    list2.add("102");
    list2.add("processing");
    list2.add("doctor2");
    queue.getItems().add(list2.toString());
    list3.add("MRI");
    list3.add("CAT");
    list3.add("Patient3");
    list3.add("103");
    list3.add("done");
    list3.add("doctor3");
    queue.getItems().add(list3.toString());
    list4.add("blood");
    list4.add("urine");
    list4.add("CAT");
    list4.add("xray");
    list4.add("Patient4");
    list4.add("104");
    list4.add("black");
    list4.add("doctor4");
    queue.getItems().add(list4.toString());
    list5.add("xray");
    list5.add("MRI");
    list5.add("Patient5");
    list5.add("105");
    list5.add("done");
    list5.add("doctor5");
    queue.getItems().add(list5.toString());
  }
}
