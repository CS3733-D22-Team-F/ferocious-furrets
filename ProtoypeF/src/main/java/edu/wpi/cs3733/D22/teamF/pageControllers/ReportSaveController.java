package edu.wpi.cs3733.D22.teamF.pageControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ReportSaveController extends PageController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private JFXButton saveButton;
  @FXML private Label popUpLabel;
  @FXML private JFXComboBox saveTypeChooser;
  @FXML private VBox loginVBox;
  @FXML private ImageView backgroundImage;
  @FXML private BorderPane masterPane;
  @FXML private Pane imagePane;
  @FXML private AnchorPane imageAnchor;
  /**
   * Method to create a class specifics context menu
   *
   * @return A specific's class context menu
   */
  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<String> saveChoices = new ArrayList<>();
    saveChoices.add("Word Document (*.docx)");
    saveChoices.add("Portable Document Format (*.pdf)");
    saveTypeChooser.getItems().addAll(saveChoices);
  }

  public void save() {
    FileChooser fChoose = new FileChooser();
    fChoose.setTitle("Save to:");
    Stage stage = (Stage) masterPane.getScene().getWindow();
    File file = fChoose.showSaveDialog(stage);
    if (saveTypeChooser.getValue().toString().equals("Word Document (*.docx)")) {
      String filepath = file.getPath() + ".docx";
    }
  }
}
