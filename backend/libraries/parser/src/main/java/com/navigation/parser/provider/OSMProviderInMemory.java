package com.navigation.parser.provider;

import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;

public class OSMProviderInMemory implements OSMProvider {

  private final String osmData;

  public OSMProviderInMemory(String osmData) {
    this.osmData = osmData;
  }

  @Override
  public OSMStreamReader loadOSMXml() throws XMLStreamException {
    return new OSMStreamReader(new ByteArrayInputStream(osmData.getBytes()));
  }
}
