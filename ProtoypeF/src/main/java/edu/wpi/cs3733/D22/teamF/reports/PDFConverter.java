package edu.wpi.cs3733.D22.teamF.reports;

import com.documents4j.api.DocumentType;
import java.io.*;
import org.docx4j.documents4j.local.Documents4jLocalServices;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public class PDFConverter {

  String inputFile;
  String outputFile;

  public PDFConverter(String inputFile, String outputFile) {
    this.inputFile = inputFile;
    this.outputFile = outputFile;
  }

  public void convertToPDF() throws IOException, Docx4JException {
    File output = new File(outputFile);
    FileOutputStream os = new FileOutputStream(output);

    Documents4jLocalServices exporter = new Documents4jLocalServices();
    exporter.export(new File(inputFile), os, DocumentType.MS_WORD);

    os.close();
  }
}
