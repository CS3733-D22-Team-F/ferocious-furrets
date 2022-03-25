package edu.wpi.teamname;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homePageController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  public void switchToLab(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("labRequestPage.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
