package com.navigation.geocoding.application;

import com.navigation.geocoding.domain.Address;
import java.util.List;

public interface FindMatchingAddressesUseCase {
  List<Address> findMatchingAddresses(String address);
}
