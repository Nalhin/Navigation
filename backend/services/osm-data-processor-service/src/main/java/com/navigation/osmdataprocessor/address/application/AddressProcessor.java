package com.navigation.osmdataprocessor.address.application;

import com.navigation.osmdataprocessor.shared.interfaces.OSMProcessor;
import com.navigation.osmdataprocessor.address.domain.AddressSpecification;
import com.navigation.parser.loader.ExportSummary;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

import io.vavr.control.Try;

public class AddressProcessor implements OSMProcessor {

  private final OSMProvider provider;
  private final AddressExporter exporter;

  public AddressProcessor(OSMProvider provider, AddressExporter osmExporter) {
    this.provider = provider;
    this.exporter = osmExporter;
  }

  @Override
  public Try<ExportSummary> processAndPublish() {
    return Try.of(new OSMLoader(provider, exporter, new AddressSpecification())::export);
  }
}
