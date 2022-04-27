package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.Fapp;
import java.io.*;
import java.util.List;

public class CSVWriter {

  private CSVWriter() {}

  private static void write(File file, String data) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    writer.write(data);
    writer.flush();
    writer.close();
  }

  private static void write(String dir, String data) throws IOException {
    System.out.println("Opening: " + dir);
    dir = Fapp.class.getResource(dir).getPath();

    FileWriter writer = new FileWriter(dir);
    writer.write(data);
    writer.flush();
    writer.close();
  }

  public static void writeAll(File file, List<String> data) throws FileNotFoundException {
    String dataAll = "";
    for (String str : data) {
      dataAll = dataAll + str + "\n";
    }
    write(file, dataAll);
  }

  public static void writeAllToDir(String dir, List<String> data) throws IOException {
    String dataAll = "";
    for (String str : data) {
      dataAll = dataAll + str + "\n";
    }
    write(dir, dataAll);
  }

  public static void empty(File file) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    writer.write("");
    writer.close();
  }
}
