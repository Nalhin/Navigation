package com.navigation.osmdataprocessor.domain.address;

import com.navigation.osmdataprocessor.domain.ExportNotSupportedException;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;
import org.springframework.stereotype.Component;

@Component
public class AddressExporter implements OSMExporter {

  private final ProcessedAddressExporter processedAddressExporter;

  public AddressExporter(ProcessedAddressExporter processedAddressExporter) {
    this.processedAddressExporter = processedAddressExporter;
  }

  @Override
  public void export(Node node) {
    var address =
        new AddressBuilder()
            .setCity(node.getTag("addr:city"))
            .setCountry("Poland")
            .setId(node.getId())
            .setHouseNumber(node.getTag("addr:housenumber"))
            .setStreet(node.getTag("addr:street"))
            .setPostCode(node.getTag("addr:postcode"))
            .setLatitude(node.getLatitude())
            .setLongitude(node.getLongitude())
            .createAddressDto();
    processedAddressExporter.exportProcessedAddress(String.valueOf(address.getId()), address);
  }

  @Override
  public void export(Way way) {
    throw new ExportNotSupportedException();
  }

  @Override
  public void export(Bounds bounds) {
    throw new ExportNotSupportedException();
  }

  @Override
  public void export(Metadata metadata) {
    throw new ExportNotSupportedException();
  }

  @Override
  public void export(Relation relation) {
    throw new ExportNotSupportedException();
  }
}
