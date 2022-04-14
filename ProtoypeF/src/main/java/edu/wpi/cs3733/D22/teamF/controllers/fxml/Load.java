package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import java.io.IOException;
import java.sql.SQLException;
import javafx.concurrent.Task;

public class Load extends Task<Void> {

  @Override
  protected Void call() throws IOException, SQLException {
    Cache.updateDBCache();
    Cache.loadViews();
    Cache.loadIcons();
    return null;
  }
}
