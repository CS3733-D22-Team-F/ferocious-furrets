package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class CreditPageController implements Initializable {

  @FXML private JFXTreeTableView table;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TreeTableColumn apis = new TreeTableColumn<>("API Incorporated");
    apis.setPrefWidth(150);
    TreeTableColumn versions = new TreeTableColumn<>("API Version");
    versions.setPrefWidth(150);
    TreeTableColumn creators = new TreeTableColumn<>("API Creator");
    creators.setPrefWidth(150);
    table.getColumns().addAll(apis, versions, creators);


//    apis.setCellValueFactory(cellData -> {
//      TreeItem<?> item = cellData.getValue();
//      Object data = item.getValue();

//      if (data instanceof String) {
//        String api = (String)data ;
//        return new SimpleStringProperty("Nick");
//      } else {
//        return new SimpleStringProperty("");
//      }
//    });

  }
}
