package edu.wpi.cs3733.D22.teamF.Map;

import edu.wpi.cs3733.D22.teamF.Map.MapComponents.coordTempHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Tracks the mouse on the map
 */
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
  Image F4 = new Image(getClass().getResourceAsStream("FloorMap/Floor4.jpg"));
  Image F5 = new Image(getClass().getResourceAsStream("FloorMap/Floor5.jpg"));
  Image L1 = new Image(getClass().getResourceAsStream("FloorMap/Lower1.jpg"));
  Image L2 = new Image(getClass().getResourceAsStream("FloorMap/Lower2.jpg"));

  /**
   * tracks the mouses x and y coordinates
   * @param event
   */
  @FXML
  public void track(MouseEvent event) {
    xValue.setText((event.getX() + 2) + "");
    yValue.setText((event.getY() - 2 + ""));
    floorValue.setText(floor);
  }

  /**
   * submits a location using the mouse
   */
  public void submit() {
    if (!xValue.getText().isEmpty() && !yValue.getText().isEmpty()) {
      coordTempHolder.setFloorValue(floorValue.getText());
      coordTempHolder.setxValue(((Double.parseDouble(xValue.getText()) / 814) * 4450) + "");
      coordTempHolder.setyValue(((Double.parseDouble(yValue.getText()) / 650) * 3550) + "");
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

  public void changeToF4() {
    mapHolder.setImage(F4);
    floor = "4";
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    floor = "5";
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
