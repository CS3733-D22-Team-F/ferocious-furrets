package edu.wpi.cs3733.D22.teamF.controllers.general;

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
    br =
        new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(CSVReader.class.getResourceAsStream(filepath)),
                StandardCharsets.UTF_8));
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
