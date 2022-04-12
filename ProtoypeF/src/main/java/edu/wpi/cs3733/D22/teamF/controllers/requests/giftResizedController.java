package edu.wpi.cs3733.D22.teamF.controllers.requests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamF.controllers.fxml.StageManager;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.request.RequestSystem;
import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

// import javax.swing.text.html.ImageView;

/** Controller for gift scene */
public class giftResizedController extends PageController
    implements Initializable, IRequestController {
  @FXML Rectangle rectangle1;
  @FXML Rectangle rectangle2;
  @FXML BorderPane masterPane;
  @FXML Pane leftPane;
  @FXML ImageView logo;
  @FXML ImageView backgroundIMG;

  @FXML TextField employeeID;
  @FXML TextField nodeID;
  @FXML TextField patientName;
  @FXML TextField assigned;
  @FXML JFXComboBox statusChoice;
  @FXML JFXComboBox giftChoice;

  @FXML JFXButton submitButton;
  @FXML JFXButton homeButton;
  @FXML JFXButton queueButton;

  @FXML HBox leftHbox;

  /**
   * submit the Arraylist that contains the items and doctor Return formula: ['Service Type',
   * 'Service1', 'Service2',..., 'Patient Name', 'Room Number', 'Doctor Name']
   *
   * @return giftDeliveryRequest
   */
  public void submit() throws SQLException {
    RequestSystem req = new RequestSystem("Gift");
    ArrayList<String> fields = new ArrayList<String>();
    fields.add(generateReqID());
    fields.add(nodeID.getText());
    fields.add(assigned.getText());
    fields.add(employeeID.getText());
    fields.add(statusChoice.getValue().toString());
    fields.add(giftChoice.getValue().toString().substring(0, 15));
    req.placeRequest(fields);

    employeeID.setText("Empty");
    nodeID.setText("Empty");
    assigned.clear();
    employeeID.clear();
    nodeID.clear();
    statusChoice.valueProperty().set(null);
    giftChoice.valueProperty().set(null);
    patientName.clear();
  }

  @Override
  public void reset() {}

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.makeMenuBar(masterPane);

    masterPane.setMinHeight(500);
    masterPane.setMinWidth(500);
    rectangle1.heightProperty().bind(masterPane.heightProperty());
    rectangle1.widthProperty().bind(masterPane.widthProperty().divide(2));
    rectangle2.widthProperty().bind(masterPane.widthProperty().add(15).divide(2));
    logo.xProperty().bind(rectangle2.widthProperty().subtract(600));
    employeeID.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    employeeID.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    nodeID.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    nodeID.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    patientName.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    patientName.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    assigned.minWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    assigned.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    statusChoice.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    giftChoice.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    leftHbox.maxWidthProperty().bind(rectangle1.widthProperty().subtract(100));
    backgroundIMG.maxWidth(736);
    backgroundIMG.fitHeightProperty().bind(masterPane.heightProperty());
    backgroundIMG.fitWidthProperty().bind(masterPane.widthProperty().divide(2));

    ArrayList<Object> temp = new ArrayList<>();
    temp.add("");
    temp.add("Processing");
    temp.add("Done");
    statusChoice.getItems().addAll(temp);
    statusChoice.setValue("");
    ArrayList<Object> temp1 = new ArrayList<>();
    temp1.add("");
    temp1.add("TED - Teddy Bear");
    temp1.add("TEA - Tea");
    temp1.add("GWS - Get Well Soon Card");
    temp1.add("BLA - Blanket");
    temp1.add("TSH - Brigham and Womens T-Shirt");
    giftChoice.getItems().addAll(temp1);
    giftChoice.setValue("");
  }

  /**
   * shows the queue scene
   *
   * @param event
   * @throws IOException
   */
  public void showQueueScene(ActionEvent event) throws IOException {
    StageManager.getInstance().setDisplay("giftRequestQueue.fxml");
  }

  public String generateReqID() throws SQLException {
    String nNodeType = giftChoice.getValue().toString().substring(0, 3);
    int reqNum = 0;

    ResultSet rset = DatabaseManager.runQuery("SELECT * FROM SERVICEREQUEST");
    while (rset.next()) {
      reqNum++;
    }
    rset.close();

    if (reqNum == 0) {
      reqNum = 1;
    }

    String nID = "f" + nNodeType + reqNum;
    return nID;
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    StageManager.getInstance().setHomeScreen();
  }
}
