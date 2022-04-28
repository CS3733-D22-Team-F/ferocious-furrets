package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.Fapp;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVReader {

  protected static BufferedReader br;
  private List<String> fileList;

  public CSVReader() {}

  private static List<String> bufferedReaderToString() throws IOException {
    List<String> fileString = new ArrayList<>();
    String line = br.readLine(); // Header ignored

    while ((line = br.readLine()) != null && !line.isEmpty()) {
      fileString.add(line);
    }

    return fileString;
  }

  public static List<String> readResourceFilepath(String filepath) throws IOException {
    List<String> fileList;
    File file = new File(filepath);
    System.out.println("Filepath reading from: " + file.getPath());
    try {
      br =
          new BufferedReader(
              new InputStreamReader(
                  Objects.requireNonNull(Fapp.class.getResourceAsStream(filepath)),
                  StandardCharsets.UTF_8));
    } catch (NullPointerException e) {
      br =
          new BufferedReader(
              new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }
    fileList = bufferedReaderToString();
    br.close();
    return fileList;
  }

  public static List<String> readResourceFilepathFromDirectory(String filepath) throws IOException {
    List<String> fileList;
    File file = new File(filepath);
    br =
        new BufferedReader(
            new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    fileList = bufferedReaderToString();
    br.close();
    return fileList;
  }

  public static List<String> readFile(File file) throws IOException {
    List<String> fileList;
    br = new BufferedReader(new FileReader(file));
    fileList = bufferedReaderToString();
    br.close();

    return fileList;
  }
}
