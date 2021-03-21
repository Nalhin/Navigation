package com.navigation.parser.provider;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

public class OSMProviderFile implements OSMProvider {
  private final String filePath;

  public OSMProviderFile(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public XMLStreamReader loadOSMXml() throws IOException, XMLStreamException {
    var xmlInputFactory = XMLInputFactory.newInstance();
    return xmlInputFactory.createXMLStreamReader(new FileInputStream(filePath));
  }
}
