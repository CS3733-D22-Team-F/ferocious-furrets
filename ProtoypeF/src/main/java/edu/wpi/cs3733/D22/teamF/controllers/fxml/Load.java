package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.database.DatabaseInitializer;
import java.io.IOException;
import java.sql.SQLException;
import javafx.concurrent.Task;

public class Load extends Task<Void> {

  @Override
  protected Void call() throws IOException, SQLException {
    DatabaseManager.switchConnection(DatabaseInitializer.getConnType());
    Cache.loadViews();
    Cache.loadIcons();
    return null;
  }
}
