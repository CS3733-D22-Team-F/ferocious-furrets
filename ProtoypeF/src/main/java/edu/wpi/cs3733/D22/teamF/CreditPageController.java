package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class CreditPageController implements Initializable {

  @FXML private JFXTreeTableView<CreditAPI> table;

  private String apiIncorp;
  private String apiVersion;
  private String apiCreator;
  @FXML private TextField input;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //    CreditAPI docAPI = new CreditAPI("Derby", "1.0", "Nelson");
    //
    //    TreeTableColumn apis = new TreeTableColumn<>("API Incorporated");
    //    apis.setPrefWidth(150);
    //    TreeTableColumn versions = new TreeTableColumn<>("API Version");
    //    versions.setPrefWidth(150);
    //    TreeTableColumn creators = new TreeTableColumn<>("API Creator");
    //    creators.setPrefWidth(150);
    //    table.getColumns().addAll(apis, versions, creators);

    JFXTreeTableColumn<CreditAPI, String> APIIncorporated = new JFXTreeTableColumn<>("API Incorporated");
      APIIncorporated.setPrefWidth(150);
      APIIncorporated.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<CreditAPI, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<CreditAPI, String> param) {
            return param.getValue().getValue().apiIncorporated;
          }
        });

    JFXTreeTableColumn<CreditAPI, String> APIVersion = new JFXTreeTableColumn<>("API Version");
      APIVersion.setPrefWidth(150);
      APIVersion.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<CreditAPI, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<CreditAPI, String> param) {
            return param.getValue().getValue().apiVersion;
          }
        });

    JFXTreeTableColumn<CreditAPI, String> APICreator = new JFXTreeTableColumn<>("API Creator");
      APICreator.setPrefWidth(150);
      APICreator.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<CreditAPI, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<CreditAPI, String> param) {
            return param.getValue().getValue().apiCreator;
          }
        });

    ObservableList<CreditAPI> credits = FXCollections.observableArrayList();
    credits.add(new CreditAPI("Computer Department", "23", "CD 1"));
    credits.add(new CreditAPI("Sales Department", "22", "Employee 1"));
    credits.add(new CreditAPI("Sales Department", "22", "Employee 2"));
    credits.add(new CreditAPI("Sales Department", "25", "Employee 4"));
    credits.add(new CreditAPI("Sales Department", "25", "Employee 5"));
    credits.add(new CreditAPI("IT Department", "42", "ID 2"));
    credits.add(new CreditAPI("HR Department", "22", "HR 1"));
    credits.add(new CreditAPI("HR Department", "22", "HR 2"));

    final TreeItem<CreditAPI> root =
        new RecursiveTreeItem<CreditAPI>(credits, RecursiveTreeObject::getChildren);
    table.getColumns().setAll(APIIncorporated, APIVersion, APICreator);
    table.setRoot(root);
    table.setShowRoot(false);

    input
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                table.setPredicate(
                    new Predicate<TreeItem<CreditAPI>>() {
                      @Override
                      public boolean test(TreeItem<CreditAPI> creditAPITreeItem) {
                        Boolean flag =
                            creditAPITreeItem
                                    .getValue()
                                    .apiIncorporated
                                    .getValue()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(newValue.toLowerCase(Locale.ROOT))
                                || creditAPITreeItem
                                    .getValue()
                                    .apiIncorporated
                                    .getValue()
                                    .contains(newValue)
                                || creditAPITreeItem
                                    .getValue()
                                    .apiVersion
                                    .getValue()
                                    .contains(newValue)
                                || creditAPITreeItem
                                    .getValue()
                                    .apiVersion
                                    .getValue()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(newValue.toLowerCase(Locale.ROOT))
                                || creditAPITreeItem
                                    .getValue()
                                    .apiCreator
                                    .getValue()
                                    .contains(newValue)
                                || creditAPITreeItem
                                    .getValue()
                                    .apiCreator
                                    .getValue()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(newValue.toLowerCase(Locale.ROOT));
                        return flag;
                      }
                    });
              }
            });
  }
}
