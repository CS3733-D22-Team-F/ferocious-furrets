package edu.wpi.cs3733.D22.teamF.controllers.requests;

import edu.wpi.cs3733.D22.teamF.pageControllers.PageController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;

public class floralController extends PageController implements Initializable, IRequestController {

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location The location used to resolve relative paths for the root object, or {@code
   *     null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @Override
  public void submit() {}

  @Override
  public void reset() {}

  /**
   * Starts the table in the request page
   *
   * @throws SQLException
   * @throws IOException
   */
  public void startTable() throws SQLException, IOException {}

  /** clears the table in the request page */
  public void clearTable() {}

  /* helpers function */

  /**
   * generates a reqID based on fields in the request page
   *
   * @return returns String (a reqID)
   * @throws SQLException
   */
  public String generateReqID() throws SQLException, IOException {
    return null;
  }

  void switchToHome(ActionEvent event) throws IOException {
    // StageManager.getInstance().setHome();
  }

  @Override
  public ContextMenu makeContextMenu() {
    return null;
  }
}
