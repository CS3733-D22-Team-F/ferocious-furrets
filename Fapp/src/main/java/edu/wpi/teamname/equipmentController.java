package edu.wpi.teamname;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class equipmentController implements Initializable {

  @FXML private Button backButton;

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML private ListView<String> myListView;
  @FXML private ListView<String> myCleanListView;
  @FXML private ListView<String> myDirtyListView;

  @FXML
  void returnHome(ActionEvent event) throws IOException {
    root = FXMLLoader.load((getClass().getResource("homePage.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1280, 720);
    stage.setScene(scene);
    stage.show();
  }

  String[] medEquipment = {
    "<medical equipment slot>",
    "<medical equipment slot>",
    "<medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
    " <medical equipment slot>",
  };
  String[] cleanEquipment = {
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
    "<clean equipment slot>",
  };
  String[] dirtyEquipment = {
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
    "<dirty equipment slot",
  };

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    myListView.getItems().addAll(medEquipment);
    myCleanListView.getItems().addAll(cleanEquipment);
    myDirtyListView.getItems().addAll(dirtyEquipment);
  }
}
