package com.navigation.osmdataprocessor.domain.address;

import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class AddressDataProcessor {

  private final OSMProvider provider;
  private final OSMExporter exporter;

  public AddressDataProcessor(OSMProvider provider, OSMExporter osmExporter) {
    this.provider = provider;
    this.exporter = osmExporter;
  }

  public void export() {
    try {
      new OSMLoader(provider, exporter, new AddressDataSpecification()).export();
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}
