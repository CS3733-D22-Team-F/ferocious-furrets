package edu.wpi.cs3733.D22.teamF.entities.database;

import edu.wpi.cs3733.D22.teamF.controllers.general.CSVReader;
import edu.wpi.cs3733.D22.teamF.controllers.general.CSVWriter;
import edu.wpi.cs3733.D22.teamF.controllers.general.DatabaseManager;
import edu.wpi.cs3733.D22.teamF.entities.theme.Theme;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemeDAOImpl {

  public void initTable(File file) throws SQLException, IOException {
    DatabaseManager.getInstance().dropTableIfExist("Theme");
    DatabaseManager.getInstance()
        .runStatement(
            "CREATE TABLE Theme (name varchar(64) PRIMARY KEY, mainColor varChar(64), subColor varchar(64), textColor varchar(64))");

    List<String> lines = CSVReader.readFile(file);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeObjectFromString(currentLine));
    }
  }

  public void initTable(String filePath) throws SQLException, IOException {
    try {
      DatabaseManager.getInstance()
          .runStatement(
              "CREATE TABLE Theme (name varchar(64) PRIMARY KEY, mainColor varChar(64), subColor varchar(64), textColor varchar(64))");
    } catch (SQLException e) {
      if (e.getSQLState().equals("X0Y32")) {
        return;
      }
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
    List<String> lines = CSVReader.readResourceFilepath(filePath);
    for (String currentLine : lines) {
      //      System.out.println(currentLine);
      add(makeObjectFromString(currentLine));
    }
  }

  private Theme makeObjectFromString(String currentLine) {
    String[] currentLineSplit = currentLine.split(",");

    return new Theme(
        currentLineSplit[0], currentLineSplit[1], currentLineSplit[2], currentLineSplit[3]);
  }

  public ResultSet get() throws SQLException, IOException {
    return DatabaseManager.getInstance().runQuery("SELECT * FROM Theme");
  }

  public ArrayList<Theme> getArrayList() throws SQLException, IOException {

    ArrayList<Theme> currentThemesList = new ArrayList<>();
    ResultSet currentThemes = get();

    while (currentThemes.next()) {
      currentThemesList.add(
          new Theme(
              currentThemes.getString("name"),
              currentThemes.getString("mainColor"),
              currentThemes.getString("subColor"),
              currentThemes.getString("textColor")));
    }

    currentThemes.close();

    return currentThemesList;
  }

  public void add(Theme theme) throws SQLException {
    DatabaseManager.getInstance().runStatement(generateInsertStatement(theme));
  }

  public void delete(String name) throws SQLException {
    DatabaseManager.getInstance()
        .runStatement(String.format("" + "DELETE FROM Theme WHERE name = '%s'", name));
  }

  public String generateInsertStatement(Theme theme) {
    return String.format(
        "INSERT INTO Theme VALUES('%s', '%s', '%s', '%s')",
        theme.getName(), theme.getMainColor(), theme.getSubColor(), theme.getTextColor());
  }

  public void backUpToCSV(String fileDir) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("name,mainColor,subColor,textColor");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("name"),
              currentRow.getString("mainColor"),
              currentRow.getString("subColor"),
              currentRow.getString("textColor")));
    }

    currentRow.close();

    CSVWriter.writeAllToDir(fileDir, toAdd);
  }

  public void backUpToCSV(File file) throws SQLException, IOException {
    ArrayList<String> toAdd = new ArrayList<>();
    ResultSet currentRow = get();
    toAdd.add("name,mainColor,subColor,textColor");

    while (currentRow.next()) {
      toAdd.add(
          String.format(
              "%s,%s,%s,%s",
              currentRow.getString("name"),
              currentRow.getString("mainColor"),
              currentRow.getString("subColor"),
              currentRow.getString("textColor")));
    }

    currentRow.close();

    CSVWriter.writeAll(file, toAdd);
  }
}
