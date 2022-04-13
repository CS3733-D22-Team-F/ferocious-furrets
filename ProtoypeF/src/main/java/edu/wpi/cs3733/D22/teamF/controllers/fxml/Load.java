package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import java.io.IOException;
import javafx.concurrent.Task;

public class Load extends Task<Void> {

  @Override
  protected Void call() throws IOException {
    Cache.loadViews();
    Cache.loadIcons();
    return null;
  }
}
