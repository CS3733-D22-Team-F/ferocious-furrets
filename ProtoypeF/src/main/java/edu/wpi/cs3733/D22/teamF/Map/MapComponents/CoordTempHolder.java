package edu.wpi.cs3733.D22.teamF.Map.MapComponents;

/** holder for coordinates */
public class CoordTempHolder {
  private static String xValue;
  private static String yValue;
  private static String floorValue;

  public static String getxValue() {
    return xValue;
  }

  public static void setxValue(String xValue) {
    CoordTempHolder.xValue = xValue;
  }

  public static String getyValue() {
    return yValue;
  }

  public static void setyValue(String yValue) {
    CoordTempHolder.yValue = yValue;
  }

  public static String getFloorValue() {
    return floorValue;
  }

  public static void setFloorValue(String floorValue) {
    CoordTempHolder.floorValue = floorValue;
  }
}
