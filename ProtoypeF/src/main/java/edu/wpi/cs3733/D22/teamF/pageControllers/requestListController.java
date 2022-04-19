package edu.wpi.cs3733.D22.teamF.pageControllers;

import edu.wpi.cs3733.D22.teamF.Fapp;
import edu.wpi.cs3733.D22.teamF.Map.MapComponents.locTempHolder;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.Request;
import edu.wpi.cs3733.D22.teamF.entities.request.deliveryRequest.equipmentDeliveryRequest;
import edu.wpi.cs3733.D22.teamF.serviceRequestStorage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * controller for request list
 *
 * @see Initializable
 */
public class requestListController extends PageController implements Initializable {


  public static String selectedID = "";
  public static String selectedType = "";
  @FXML ListView requestList;
  @FXML private AnchorPane masterPane;

  /**
   * inits
   *
   * @param location URL
   * @param resources ResourceBundle
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /** adds items to the list to requestList ArrayList */
  public void populateList() {
    requestList.getItems().clear();
    ArrayList<ArrayList<Object>> reqs = new ArrayList<ArrayList<Object>>();

    for (ArrayList<Object> list : serviceRequestStorage.getArrayList()) {
      requestList.getItems().add(list);
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setHome();
  }

  void onChangePress() throws IOException {
    //TODO replace "new" items with defined items
    TreeTableView<Request> table = new TreeTableView<>();
    TreeItem<Request> selectedItem = table.getSelectionModel().getSelectedItem();
    selectedID = selectedItem.getValue().getReqID();
    selectedType = selectedItem.getValue().getReqType();
    popUpModifyReq();
    //requestListController.selectedID;
    //requestListController.selectedType;
  }



  void popUpModifyReq() throws IOException {
    Parent root =
            FXMLLoader.load(
                    Objects.requireNonNull(Fapp.class.getResource("Map/mapEquipModifyPage.fxml")));
    Stage popupwindow = new Stage();
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    Scene scene1 = new Scene(root);
    popupwindow.setScene(scene1);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.showAndWait();
  }
}
