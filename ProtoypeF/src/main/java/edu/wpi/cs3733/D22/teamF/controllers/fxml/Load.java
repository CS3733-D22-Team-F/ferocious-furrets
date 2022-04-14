package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import java.io.IOException;
import java.sql.SQLException;
import javafx.concurrent.Task;

public class Load extends Task<Void> {

  boolean useEmbedded;

  public Load(boolean embedded) {
    useEmbedded = embedded;
  }

  @Override
  protected Void call() throws IOException, SQLException {
    Cache.startDB(useEmbedded);
    Cache.updateDBCache();
    Cache.loadViews();
    Cache.loadIcons();
    return null;
  }
}
