package com.navigation.osmdataprocessor.domain.address;

public interface ProcessedAddressExporter {

  void exportProcessedAddress(String id, Address address);
}
