package com.navigation.osmdataprocessor.domain.street;

import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class StreetDataProcessor {

  private final OSMProvider provider;
  private final OSMExporter exporter;

  public StreetDataProcessor(OSMProvider provider, OSMExporter osmExporter) {
    this.provider = provider;
    this.exporter = osmExporter;
  }

  public void processAndExport() {
    try {
      new OSMLoader(provider, exporter, new StreetDataSpecification()).export();
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}
