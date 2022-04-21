package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;

public class CreditPageController implements Initializable {

  @FXML private JFXTreeTableView table;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TreeTableColumn apis = new TreeTableColumn<>("API Incorporated");
    TreeTableColumn versions = new TreeTableColumn<>("API Version");
    table.getColumns().addAll(apis, versions);
  }
}
