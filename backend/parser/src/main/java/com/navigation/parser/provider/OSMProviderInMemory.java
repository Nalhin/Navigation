package com.navigation.parser.provider;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;

public class OSMProviderInMemory implements OSMProvider {

  private final String osmData;

  public OSMProviderInMemory(String osmData) {
    this.osmData = osmData;
  }

  @Override
  public XMLStreamReader loadOSMXml() throws XMLStreamException {
    var xmlInputFactory = XMLInputFactory.newInstance();
    return xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(osmData.getBytes()));
  }
}
