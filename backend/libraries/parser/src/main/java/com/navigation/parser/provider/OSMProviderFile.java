package com.navigation.parser.provider;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;

public class OSMProviderFile implements OSMProvider {
  private final String filePath;

  public OSMProviderFile(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public OSMStreamReader loadOSMXml() throws XMLStreamException, FileNotFoundException {
    return new OSMStreamReader(new FileInputStream(filePath));
  }
}
