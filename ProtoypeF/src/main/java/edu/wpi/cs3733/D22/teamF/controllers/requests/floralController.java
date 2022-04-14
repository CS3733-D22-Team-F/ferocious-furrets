package edu.wpi.cs3733.D22.teamF.controllers.requests;

import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

public class floralController extends PageController implements Initializable, IRequestController {
  @Override
  public void submit() {}

  @Override
  public void reset() {}

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setHome();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
