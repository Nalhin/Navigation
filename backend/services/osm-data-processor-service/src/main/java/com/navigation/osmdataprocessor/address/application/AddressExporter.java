package com.navigation.osmdataprocessor.address.application;

import com.navigation.osmdataprocessor.shared.exceptions.ExportNotSupportedException;
import com.navigation.osmdataprocessor.address.domain.AddressExtractor;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;

public class AddressExporter implements OSMExporter {

  private final ProcessedAddressPublisher processedAddressPublisher;
  private final AddressExtractor addressExtractor;

  public AddressExporter(
          ProcessedAddressPublisher processedAddressPublisher, AddressExtractor addressExtractor) {
    this.processedAddressPublisher = processedAddressPublisher;
    this.addressExtractor = addressExtractor;
  }

  @Override
  public boolean accept(Node node) {
    var address = addressExtractor.extractFromNode(node);
    processedAddressPublisher.publishProcessedAddress(String.valueOf(address.getId()), address);
    return true;
  }

  @Override
  public boolean accept(Way way) {
    throw new ExportNotSupportedException();
  }

  @Override
  public boolean accept(Bounds bounds) {
    throw new ExportNotSupportedException();
  }

  @Override
  public boolean accept(Metadata metadata) {
    throw new ExportNotSupportedException();
  }

  @Override
  public boolean accept(Relation relation) {
    throw new ExportNotSupportedException();
  }
}
