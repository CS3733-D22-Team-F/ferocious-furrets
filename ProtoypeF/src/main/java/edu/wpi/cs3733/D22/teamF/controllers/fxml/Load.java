package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import javafx.concurrent.Task;

public class Load extends Task<Void> {

  @Override
  protected Void call() {
    Cache.loadViews();
    return null;
  }
}
