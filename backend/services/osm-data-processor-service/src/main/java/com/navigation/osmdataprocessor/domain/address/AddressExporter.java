package com.navigation.osmdataprocessor.domain.address;

import com.navigation.osmdataprocessor.domain.ExportNotSupportedException;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;

public class AddressExporter implements OSMExporter {

  private final ProcessedAddressExporter processedAddressExporter;
  private final AddressMapper addressMapper = new AddressMapper();

  public AddressExporter(ProcessedAddressExporter processedAddressExporter) {
    this.processedAddressExporter = processedAddressExporter;
  }

  @Override
  public boolean accept(Node node) {
    var address = addressMapper.fromNode(node);
    processedAddressExporter.exportProcessedAddress(String.valueOf(address.getId()), address);
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
