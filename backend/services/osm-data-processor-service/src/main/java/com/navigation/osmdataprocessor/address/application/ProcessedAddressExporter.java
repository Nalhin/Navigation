package com.navigation.osmdataprocessor.address.application;

import com.navigation.osmdataprocessor.address.domain.Address;

public interface ProcessedAddressExporter {

  void exportProcessedAddress(String id, Address address);
}
