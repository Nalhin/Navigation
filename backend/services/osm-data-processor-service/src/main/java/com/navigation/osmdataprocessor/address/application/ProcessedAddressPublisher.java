package com.navigation.osmdataprocessor.address.application;

import com.navigation.osmdataprocessor.address.domain.Address;

public interface ProcessedAddressPublisher {

  void publishProcessedAddress(String id, Address address);
}
