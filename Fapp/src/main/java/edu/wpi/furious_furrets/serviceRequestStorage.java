package edu.wpi.furious_furrets;

import java.util.ArrayList;

/** class for the storage of the service requests */
public class serviceRequestStorage {
  private static ArrayList<ArrayList<Object>> storageArrayList = new ArrayList<>();

  /**
   * addes input array list to the storageArrayList
   *
   * @param inputArrayList ArrayList
   */
  public static void addToArrayList(ArrayList<Object> inputArrayList) {
    storageArrayList.add(inputArrayList);
  }

  /**
   * gets the storageArrayList
   *
   * @return ArrayList of ArrayLists
   */
  public static ArrayList<ArrayList<Object>> getArrayList() {

    return storageArrayList;
  }
}