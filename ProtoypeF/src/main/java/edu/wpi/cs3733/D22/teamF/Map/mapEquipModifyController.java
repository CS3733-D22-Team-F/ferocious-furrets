package edu.wpi.cs3733.D22.teamF.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.MapEquipmentModifier;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.locTempHolder;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mapEquipModifyController implements Initializable {

  String floor = "1";
  String Status = "available";

  @FXML JFXComboBox<String> nodeBox;

  @FXML private AnchorPane iconPane;

  ArrayList<String> node = new ArrayList<>();
  ArrayList<Integer> x = new ArrayList<>();
  ArrayList<Integer> y = new ArrayList<>();
  ArrayList<String> floors = new ArrayList<>();
  ArrayList<String> room = new ArrayList<>();

  @FXML private Button cancel;

  @FXML private JFXButton selectedIcon;
  @FXML private JFXButton cleanButton;
  @FXML private JFXButton dirtyButton;

  @FXML private Label currentNode;
  @FXML private Label statusLabel;
  @FXML private int xValue;
  @FXML private int yValue;
  @FXML private String floorValue;

  @FXML private ImageView mapHolder;

  Image F1 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor1.jpg")));
  Image F2 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor2.jpg")));
  Image F3 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor3.jpg")));
  Image F4 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor4.jpg")));
  Image F5 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Floor5.jpg")));
  Image L1 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Lower1.jpg")));
  Image L2 =
      new Image(Objects.requireNonNull(Fapp.class.getResourceAsStream("Map/FloorMap/Lower2.jpg")));

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    currentNode.setText(
        locTempHolder.getLocation().getNodeType()
            + " - "
            + locTempHolder.getLocation().getShortName());
    changeToF1();
    ArrayList<Location> locations = new ArrayList<>();
    try {
      locations = DatabaseManager.getLocationDAO().getAllLocationsFromDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (locTempHolder.getLocation().getBuilding().equals("available")) {
      dirtyButton.setDisable(false);
      cleanButton.setDisable(true);
      Status = "available";
      statusLabel.setText("available");
    } else {
      dirtyButton.setDisable(true);
      cleanButton.setDisable(false);
      Status = "unavailable";
      statusLabel.setText("unavailable");
    }
    for (Location lo : locations) {
      x.add(lo.getXcoord());
      y.add(lo.getYcoord());
      floors.add(lo.getFloor());
      node.add(lo.getNodeID());
      room.add(lo.getLongName());
    }
    nodeBox.getItems().addAll(room);
    nodeBox.setValue(room.get(0));
    ArrayList<String> status = new ArrayList<>();
    status.add("available");
    status.add("unavailable");
  }

  /** Cancel add, close window */
  public void cancel() {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  /** reset fields in add window */
  public void reset() {
    nodeBox.setValue(room.get(0));
    floorValue = floors.get(room.indexOf(room.get(0)));
    xValue = x.get(room.indexOf(room.get(0)));
    yValue = x.get(room.indexOf(room.get(0)));
    iconPane.getChildren().remove(selectedIcon);
  }

  /**
   * @throws SQLException
   * @throws IOException
   *     <p>Submit turns the fields of the Add Location window into a new Location object then
   *     passes that object to the LocationDAOImpl class to add to the database
   */
  public void submit() throws SQLException, IOException {
    try {
      MapEquipmentModifier.modifyEquipment(
          node.get(room.indexOf(nodeBox.getValue())),
          Status,
          locTempHolder.getLocation().getShortName());
      Stage stage = (Stage) cancel.getScene().getWindow();
      stage.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onRoomChange() {
    String selectFloor = floors.get(room.indexOf(nodeBox.getValue()));
    System.out.println(selectFloor);
    if (selectFloor.equals("1")) {
      changeToF1();
    } else if (selectFloor.equals("2")) {
      changeToF2();
    } else if (selectFloor.equals("3")) {
      changeToF3();
    } else if (selectFloor.equals("4")) {
      changeToF4();
    } else if (selectFloor.equals("5")) {
      changeToF5();
    } else if (selectFloor.equals("L1")) {
      changeToL1();
    } else if (selectFloor.equals("L2")) {
      changeToL2();
    }
    selectedIcon = new JFXButton("", getIcon());
    selectedIcon.setPrefSize(15, 15);
    int xValue = x.get(room.indexOf(nodeBox.getValue()));
    int yValue = y.get(room.indexOf(nodeBox.getValue()));
    selectedIcon.setLayoutX((xValue / 4450.0) * 620);
    selectedIcon.setLayoutY((yValue / 3550.0) * 470);
    iconPane.getChildren().add(selectedIcon);
  }

  public void delete() throws SQLException, IOException {
    MapEquipmentModifier.deleteEquipment();
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
  }

  public void changeToF1() {
    mapHolder.setImage(F1);
    floor = "1";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF2() {
    mapHolder.setImage(F2);
    floor = "2";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF3() {
    mapHolder.setImage(F3);
    floor = "3";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF4() {
    mapHolder.setImage(F4);
    floor = "4";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToF5() {
    mapHolder.setImage(F5);
    floor = "5";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToL1() {
    mapHolder.setImage(L1);
    floor = "L1";
    iconPane.getChildren().remove(selectedIcon);
  }

  public void changeToL2() {
    mapHolder.setImage(L2);
    floor = "L2";
    iconPane.getChildren().remove(selectedIcon);
  }

  public ImageView getIcon() {
    Image image =
        new Image(
            Objects.requireNonNull(
                getClass().getResourceAsStream("views/Map/Icons/MapIcons/INFO Icon.png")));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(10);
    imageView.setFitWidth(10);
    return imageView;
  }

  public void toClean() {
    Status = "available";
    statusLabel.setText("available");
  }

  public void toDirty() {
    Status = "unavailable";
    statusLabel.setText("unavailable");
  }
}
