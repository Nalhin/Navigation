package com.navigation.osmdataprocessor.domain.address;

import com.navigation.osmdataprocessor.domain.DataProcessor;
import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.loader.ExportSummary;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

import io.vavr.control.Try;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class AddressDataProcessor implements DataProcessor {

  private final OSMProvider provider;
  private final OSMExporter exporter;

  public AddressDataProcessor(OSMProvider provider, OSMExporter osmExporter) {
    this.provider = provider;
    this.exporter = osmExporter;
  }

  @Override
  public Try<ExportSummary> processAndExport() {
    return Try.of(new OSMLoader(provider, exporter, new AddressDataSpecification())::export);
  }
}
