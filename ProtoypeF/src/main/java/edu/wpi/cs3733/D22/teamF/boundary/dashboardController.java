package edu.wpi.cs3733.D22.teamF.boundary;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.location.Location;
import edu.wpi.cs3733.D22.teamF.entities.medicalEquipment.equipment;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class dashboardController implements Initializable {

  @FXML TextArea floor5PodTextArea;
  @FXML TextArea floor4PodTextArea;
  @FXML TextArea floor3PodTextArea;
  @FXML TextArea floor2PodTextArea;
  @FXML TextArea floor1PodTextArea;
  @FXML TextArea lowerLevel1PodTextArea;
  @FXML TextArea lowerLevel2PodTextArea;

  @FXML TextArea floor5CleanTextArea;
  @FXML TextArea floor4CleanTextArea;
  @FXML TextArea floor3CleanTextArea;
  @FXML TextArea floor2CleanTextArea;
  @FXML TextArea floor1CleanTextArea;
  @FXML TextArea lowerLevel1CleanTextArea;
  @FXML TextArea lowerLevel2CleanTextArea;

  @FXML TextArea floor5DirtyTextArea;
  @FXML TextArea floor4DirtyTextArea;
  @FXML TextArea floor3DirtyTextArea;
  @FXML TextArea floor2DirtyTextArea;
  @FXML TextArea floor1DirtyTextArea;
  @FXML TextArea lowerLevel1DirtyTextArea;
  @FXML TextArea lowerLevel2DirtyTextArea;

  @FXML JFXButton homeButton;
  @FXML JFXButton switchToMap;

  equipTextBox[] floorTextBoxes = new equipTextBox[21];

  private final Connection connection = DatabaseManager.getConn();

  private static class equipTextBox {
    TextArea textArea;
    String floor;
    String locType;

    equipTextBox(TextArea ta, String floor, String locType) {
      this.textArea = ta;
      this.floor = floor;
      this.locType = locType;
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    equipTextBox L2Pod = new equipTextBox(lowerLevel2PodTextArea, "L2", "Pod");
    equipTextBox L2Clean = new equipTextBox(lowerLevel2CleanTextArea, "L2", "Clean");
    equipTextBox L2Dirty = new equipTextBox(lowerLevel2DirtyTextArea, "L2", "Dirty");
    equipTextBox L1Pod = new equipTextBox(lowerLevel1PodTextArea, "L1", "Pod");
    equipTextBox L1Clean = new equipTextBox(lowerLevel1CleanTextArea, "L1", "Clean");
    equipTextBox L1Dirty = new equipTextBox(lowerLevel1DirtyTextArea, "L1", "Dirty");
    equipTextBox F1Pod = new equipTextBox(floor1PodTextArea, "01", "Pod");
    equipTextBox F1Clean = new equipTextBox(floor1CleanTextArea, "01", "Clean");
    equipTextBox F1Dirty = new equipTextBox(floor1DirtyTextArea, "01", "Dirty");
    equipTextBox F2Pod = new equipTextBox(floor2PodTextArea, "02", "Pod");
    equipTextBox F2Clean = new equipTextBox(floor2CleanTextArea, "02", "Clean");
    equipTextBox F2Dirty = new equipTextBox(floor2DirtyTextArea, "02", "Dirty");
    equipTextBox F3Pod = new equipTextBox(floor3PodTextArea, "03", "Pod");
    equipTextBox F3Clean = new equipTextBox(floor3CleanTextArea, "03", "Clean");
    equipTextBox F3Dirty = new equipTextBox(floor3DirtyTextArea, "03", "Dirty");
    equipTextBox F4Pod = new equipTextBox(floor4PodTextArea, "04", "Pod");
    equipTextBox F4Clean = new equipTextBox(floor4CleanTextArea, "04", "Clean");
    equipTextBox F4Dirty = new equipTextBox(floor4DirtyTextArea, "04", "Dirty");
    equipTextBox F5Pod = new equipTextBox(floor5PodTextArea, "05", "Pod");
    equipTextBox F5Clean = new equipTextBox(floor5CleanTextArea, "05", "Clean");
    equipTextBox F5Dirty = new equipTextBox(floor5DirtyTextArea, "05", "Dirty");
    floorTextBoxes[0] = L2Pod;
    floorTextBoxes[1] = L2Clean;
    floorTextBoxes[2] = L2Dirty;
    floorTextBoxes[3] = L1Pod;
    floorTextBoxes[4] = L1Clean;
    floorTextBoxes[5] = L1Dirty;
    floorTextBoxes[6] = F1Pod;
    floorTextBoxes[7] = F1Clean;
    floorTextBoxes[8] = F1Dirty;
    floorTextBoxes[9] = F2Pod;
    floorTextBoxes[10] = F2Clean;
    floorTextBoxes[11] = F2Dirty;
    floorTextBoxes[12] = F3Pod;
    floorTextBoxes[13] = F3Clean;
    floorTextBoxes[14] = F3Dirty;
    floorTextBoxes[15] = F4Pod;
    floorTextBoxes[16] = F4Clean;
    floorTextBoxes[17] = F4Dirty;
    floorTextBoxes[18] = F5Pod;
    floorTextBoxes[19] = F5Clean;
    floorTextBoxes[20] = F5Dirty;

    try {
      setAllFloorDashboardText();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets all the dashboard text for the floor buttons
   *
   * @throws SQLException
   */
  public void setAllFloorDashboardText() throws SQLException {
    ArrayList<equipment> allEquip = DatabaseManager.getMedEquipDAO().getAllEquipment();

    //    String outText = "";
    //    for (MedEquip me : allEquip) outText += me.getEquipID() + '\n';
    //    System.out.println(outText);

    for (equipTextBox tb : floorTextBoxes) {
      String textToSet = getTextBoxDashboardText(tb.floor, tb.locType, allEquip);
      //      System.out.println(textToSet);
      tb.textArea.setText(textToSet);
    }
  }

  /**
   * Given a floor button and the equipment on that floor, prints out MedEquip summary
   *
   * @param whichFloor
   * @param floorEquip
   * @return Formatted String to display on the floor dashboard
   */
  private String getTextBoxDashboardText(
      String whichFloor, String whichType, ArrayList<equipment> floorEquip) throws SQLException {
    ArrayList<equipment> typeEquip = new ArrayList<>();
    String equipText = "";
    for (equipment m : floorEquip) {
      if (m.getNodeID().substring(8).equals(whichFloor)
          && getLocationTypeFromNodeID(m.getNodeID()).equals(whichType)) {
        typeEquip.add(m);
      }
    }
    System.out.println(typeEquip.size());
    for (equipment m : typeEquip) {
      equipText += m.getEquipType() + " " + m.getEquipID() + ", ";
    }
    System.out.println(equipText);
    return equipText;
  }

  /**
   * Takes the floor button and all equipment and returns a list of the equipment only on that floor
   *
   * @param thisFloor String representing the floor chosen
   * @param allEquip All equipment in the databsase of MedEquip
   * @return ArrayList<MedEquip>
   */
  private ArrayList<equipment> getEquipOnThisFloor(
      String thisFloor, ArrayList<equipment> allEquip) {
    ArrayList<equipment> equipOnThisFloor = new ArrayList<>();
    for (equipment individualEquipment : allEquip) {
      String equipFloor = individualEquipment.getNodeID().substring(8);
      if (equipFloor.equals(thisFloor)) {
        equipOnThisFloor.add(individualEquipment);
      }
    }
    return equipOnThisFloor;
  }

  /**
   * Gets the location type from a node id (only pod, clean storage, or dirty storage)
   *
   * @param id NodeID
   * @return String location type
   * @throws SQLException
   */
  private String getLocationTypeFromNodeID(String id) throws SQLException {
    String retVal = "";

    ResultSet rset =
        DatabaseManager.runQuery("Select * from LOCATIONS WHERE NODEID = '" + id + "'");

    ArrayList<Location> locArray = DatabaseManager.getLocationDAO().locationsFromRSET(rset);
    Location loc = locArray.get(0);
    if (loc.getNodeType().equals("PATI")) {
      retVal = "Pod";
    }
    // if clean storage
    else if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Clean")) {
      //      System.out.println(loc.getLongName().substring(0, 5) + loc.getNodeType());

      retVal = "Clean";
    }
    // if dirty storage
    else if (loc.getNodeType().equals("STOR") && loc.getLongName().startsWith("Dirty")) {
      //      System.out.println(loc.getLongName().substring(0, 5) + loc.getNodeType());

      retVal = "Dirty";
    }

    return retVal;
  }

  @FXML
  void switchToMap(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("mapPage.fxml");
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHome();
  }
}
