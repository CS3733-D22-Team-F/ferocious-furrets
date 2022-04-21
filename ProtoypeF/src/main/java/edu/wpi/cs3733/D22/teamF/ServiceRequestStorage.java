package edu.wpi.cs3733.D22.teamF;

import java.util.ArrayList;

/** class for the storage of the service requests */
public class ServiceRequestStorage {
  private static final ArrayList<ArrayList<Object>> storageArrayList = new ArrayList<>();

  /**
   * adds input array list to the storageArrayList
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
