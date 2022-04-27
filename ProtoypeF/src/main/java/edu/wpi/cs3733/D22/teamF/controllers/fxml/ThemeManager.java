package edu.wpi.cs3733.D22.teamF.controllers.fxml;

import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.theme.Theme;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.SubScene;

/** Scene manager class, singleton so there can only be one instance */
public class ThemeManager {
  // <> generic doesn't care accepts all key,object type pair
  // smart compiler <> alt + enter
  private HashMap<String, SubScene> h_map = new HashMap<>();
  private static ThemeManager themeManager = null;
  private String currentTheme;
  private File theme;
  private String main = "123090";
  private String sub = "4169E1";
  private String text = "FFFFFF";

  private ThemeManager() {
    theme = new File("theme.css");
  }

  /**
   * if instance is null creates
   *
   * @return the sole instance
   */
  public static ThemeManager getInstance() {
    // separation for ease of control i.e initialization method
    if (themeManager == null) themeManager = new ThemeManager();
    return themeManager;
  }

  public void toDefaultTheme() throws IOException {
    theme = generateCSS("123090", "4169E1", "FFFFFF");
    applyCSS();
  }

  public void saveCSS(String name, String mainColor, String subColor, String textColor)
      throws IOException, SQLException {
    Theme newTheme = new Theme(name, mainColor, subColor, textColor);
    DatabaseManager.getInstance().getThemeDAO().add(newTheme);
    DatabaseManager.getInstance().backUpDatabaseToCSV();
  }

  public void deleteCSS(String name) throws SQLException, IOException {
    DatabaseManager.getInstance().getThemeDAO().delete(name);
    DatabaseManager.getInstance().backUpDatabaseToCSV();
    toDefaultTheme();
  }

  public ArrayList<String> getCSS() throws SQLException, IOException {
    ArrayList<Theme> themeList = DatabaseManager.getInstance().getThemeDAO().getArrayList();
    ArrayList<String> names = new ArrayList<>();
    for (Theme t : themeList) {
      names.add(t.getName());
    }
    return names;
  }

  public void changeCSS(String name) throws SQLException, IOException {
    ArrayList<Theme> themes = DatabaseManager.getInstance().getThemeDAO().getArrayList();
    for (Theme t : themes) {
      if (t.getName().equals(name)) {
        main = t.getMainColor();
        sub = t.getSubColor();
        text = t.getTextColor();
      }
    }
    theme = generateCSS(main, sub, text);
  }

  public void applyCSS() {
    SceneManager.getInstance().getStage().getScene().getStylesheets().clear();
    SceneManager.getInstance().getStage().getScene().getStylesheets().add(theme.toURI().toString());
  }

  public File generateCSS(String main, String sub, String text) throws IOException {
    theme = new File("theme.css");
    FileWriter writer = new FileWriter(theme);
    writer.flush();
    writer.write(
        String.format(
            "{\n"
                + "    f-maincolor: #%s;\n"
                + "    f-subcolor: #%s;\n"
                + "    f-ripple: #3E8CD0;\n"
                + "    f-white: #%s;\n"
                + "    f-blue: #123090;\n"
                + "    f-gold: #f3ba48;\n"
                + "}\n"
                + "\n"
                + "#TextTitleWhite {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + "#TextTitle {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#TextTitle2 {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "#TextTitle3 {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + "\n"
                + "#submit {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-pref-height: 60;\n"
                + "    -fx-pref-width: 200;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + "#reset {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-pref-height: 60;\n"
                + "    -fx-pref-width: 200;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + ".basePane {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".titleBox {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "}\n"
                + ".titleLabel {\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".homeMenu {\n"
                + "    -fx-background-color: f-subcolor;\n"
                + "}\n"
                + ".regularButton{\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + ".jfx-rippler.jfx-button {\n"
                + "    -jfx-rippler-fill: f-ripple;\n"
                + "}\n"
                + "\n"
                + ".combo-box {\n"
                + "    -fx-background-color: white;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + ".text-field {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-prompt-text-fill: rgba(128, 128, 128, 0.64);\n"
                + "    -fx-text-fill: #000000;\n"
                + "    -fx-font-size: 24px;\n"
                + "}\n"
                + "\n"
                + ".menu-bar .label {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 18px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".emergencyButton {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-text-fill: f-white;\n"
                + "    -fx-font-size: 14px;\n"
                + "}\n"
                + "\n"
                + ".rectangle1 {\n"
                + "    -fx-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".rectangle2 {\n"
                + "    -fx-fill: f-subcolor;\n"
                + "}\n"
                + "\n"
                + ".serviceLabel {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".serviceTitle {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".serviceButton {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".serviceTextField {\n"
                + "    -fx-border-color: f-subcolor;\n"
                + "}\n"
                + "\n"
                + ".serviceCombo {\n"
                + "    -fx-background-color: f-white;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "    -fx-border-color: f-subcolor;\n"
                + "}\n"
                + ".linkBox {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".linkBox2 {\n"
                + "    -fx-background-color: f-subcolor;\n"
                + "}\n"
                + "\n"
                + ".linkLabel {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 36px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".queueTitle{\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 64px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".queueButton{\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".mapMenu{\n"
                + "    -fx-background-color: f-subcolor;\n"
                + "}\n"
                + "\n"
                + ".mapButton{\n"
                + "    -fx-background-color: #123090;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 12px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".mapNode{\n"
                + "    -fx-fill: #123090;\n"
                + "}\n"
                + "\n"
                + ".apiButton {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 24px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".dashboardArrow {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 14px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".dashboardLine {\n"
                + "    -fx-stroke: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".dashboardButton {\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}\n"
                + "\n"
                + ".dashboardLabel {\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 36px;\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".dashboardTitle{\n"
                + "    -fx-text-fill: f-maincolor;\n"
                + "}"
                + "\n"
                + "\n"
                + ".alertButton{\n"
                + "    -fx-background-color: f-maincolor;\n"
                + "    -fx-font-family: Serif;\n"
                + "    -fx-font-size: 14px;\n"
                + "    -fx-text-fill: f-white;\n"
                + "}"
                + "\n"
                + ".dashChart{\n"
                + "    -fx-fill: f-maincolor;\n"
                + "}"
                + "\n"
                + ".default-color0.chart-bar{\n"
                + "    -fx-bar-fill: f-maincolor;\n"
                + "}\n"
                + "\n"
                + ".default-color1.chart-bar{\n"
                + "    -fx-bar-fill: f-subcolor;\n"
                + "}",
            main, sub, text));
    writer.flush();
    writer.close();
    return theme;
  }
}
