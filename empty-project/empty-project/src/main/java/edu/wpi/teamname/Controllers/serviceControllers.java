package edu.wpi.teamname.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class serviceControllers implements Initializable {
  @FXML private Label currentService;
  @FXML private ListView<String> serviceList;
  private String currentChoice;
  private String[] services = {"1", "2", "3", "4", "5", "6"};

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    serviceList.getItems().addAll(services);

    serviceList
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentChoice = serviceList.getSelectionModel().getSelectedItem();
                changeServiceList();
              }
            });
  }

  @FXML
  private void callService() {
    currentService.setText("Service Called");
  }

  private void changeServiceList() {
    if (currentChoice == "1") {
      currentService.setText("Current Service: 1");
    } else if (currentChoice == "2") {
      currentService.setText("Current Service: 2");
    } else if (currentChoice == "3") {
      currentService.setText("Current Service: 3");
    } else if (currentChoice == "4") {
      currentService.setText("Current Service: 4");
    } else if (currentChoice == "5") {
      currentService.setText("Current Service: 5");
    } else {
      currentService.setText("Current Service: 6");
    }
  }
}
