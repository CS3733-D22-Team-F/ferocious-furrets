package edu.wpi.cs3733.D22.teamF;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CreditAPI extends RecursiveTreeObject<CreditAPI> {
  StringProperty apiIncorporated;
  StringProperty apiVersion;
  StringProperty apiCreator;

  public CreditAPI(String apiIncorporated, String apiVersion, String apiCreator) {
    this.apiIncorporated = new SimpleStringProperty(apiIncorporated);
    this.apiVersion = new SimpleStringProperty(apiVersion);
    this.apiCreator = new SimpleStringProperty(apiCreator);
  }
}
