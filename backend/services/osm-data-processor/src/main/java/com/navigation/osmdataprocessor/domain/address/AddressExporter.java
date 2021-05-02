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
  public boolean accept(Node node) {
    var address =
        new Address.AddressBuilder()
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
