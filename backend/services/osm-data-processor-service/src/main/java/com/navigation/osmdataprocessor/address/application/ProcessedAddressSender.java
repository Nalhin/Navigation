package com.navigation.osmdataprocessor.address.application;

import com.navigation.osmdataprocessor.address.domain.Address;

public interface ProcessedAddressSender {

  void sendProcessedAddress(String id, Address address);
}
