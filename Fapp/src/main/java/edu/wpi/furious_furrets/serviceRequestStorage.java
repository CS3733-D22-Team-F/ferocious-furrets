package edu.wpi.furious_furrets;

import java.util.ArrayList;

public class serviceRequestStorage {
  private static ArrayList<ArrayList<Object>> storageArrayList = new ArrayList<>();

  public static void addToArrayList(ArrayList<Object> inputArrayList) {
    storageArrayList.add(inputArrayList);
  }

  public static ArrayList<ArrayList<Object>> getArrayList() {

    return storageArrayList;
  }
}
