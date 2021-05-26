package com.navigation.osmdataprocessor.street.application;

import com.navigation.osmdataprocessor.shared.interfaces.OSMProcessor;
import com.navigation.osmdataprocessor.street.domain.StreetDataSpecification;
import com.navigation.parser.loader.ExportSummary;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

import io.vavr.control.Try;

public class StreetProcessor implements OSMProcessor {

  private final OSMProvider provider;
  private final StreetExporter exporter;

  public StreetProcessor(OSMProvider provider, StreetExporter exporter) {
    this.provider = provider;
    this.exporter = exporter;
  }

  @Override
  public Try<ExportSummary> processAndExport() {
    return Try.of(new OSMLoader(provider, exporter, new StreetDataSpecification())::export);
  }
}
