package edu.wpi.cs3733.D22.teamF.entities.theme;

public class Theme {

  private String name;
  private String mainColor;
  private String subColor;
  private String textColor;

  public Theme(String name, String mainColor, String subColor, String textColor) {
    this.name = name;
    this.mainColor = mainColor;
    this.subColor = subColor;
    this.textColor = textColor;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMainColor() {
    return mainColor;
  }

  public void setMainColor(String mainColor) {
    this.mainColor = mainColor;
  }

  public String getSubColor() {
    return subColor;
  }

  public void setSubColor(String subColor) {
    this.subColor = subColor;
  }

  public String getTextColor() {
    return textColor;
  }

  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }
}
