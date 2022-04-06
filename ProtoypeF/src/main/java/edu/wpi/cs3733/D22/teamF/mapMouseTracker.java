package edu.wpi.cs3733.D22.teamF;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class mapMouseTracker {

  @FXML Label xValue;
  @FXML Label yValue;
  @FXML Label floorValue;
  @FXML Button cancel;
  @FXML ImageView mapHolder;
  private String floor = "1";

  Image F1 = new Image(getClass().getResourceAsStream("FloorMap/Floor1.jpg"));
  Image F2 = new Image(getClass().getResourceAsStream("FloorMap/Floor2.jpg"));
  Image F3 = new Image(getClass().getResourceAsStream("FloorMap/Floor3.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

  @FXML
  public void track(MouseEvent event) {
    xValue.setText((event.getX() + 2) + "");
    yValue.setText((event.getY() - 2 + ""));
    floorValue.setText(floor);
  }

  public void submit() {
    if (!xValue.getText().isEmpty() && !yValue.getText().isEmpty()) {
      coordTempHolder.setFloorValue(floorValue.getText());
      coordTempHolder.setxValue(((Double.parseDouble(xValue.getText()) / 790) * 1070) + "");
      coordTempHolder.setyValue(((Double.parseDouble(yValue.getText()) / 630) * 856) + "");
      //      (x/790)*1070
      //      coordTempHolder.setxValue(xValue.getText());
      //      coordTempHolder.setyValue(yValue.getText());
      Stage stage = (Stage) cancel.getScene().getWindow();
      stage.close();
    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Alert");
      alert.setHeaderText("Blank Field");
      String s = "Please select a point on map or use cancel to exit";
      alert.setContentText(s);
      alert.show();
    }
  }

  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void changeToF1() {
    mapHolder.setImage(F1);
    floor = "1";
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    floor = "2";
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    floor = "3";
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    floor = "L1";
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    floor = "L2";
  }
}
