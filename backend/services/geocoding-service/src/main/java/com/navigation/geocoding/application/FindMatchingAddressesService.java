package com.navigation.geocoding.application;

import com.navigation.geocoding.domain.Address;
import com.navigation.geocoding.domain.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMatchingAddressesService implements FindMatchingAddressesUseCase {

  private final AddressRepository addressRepository;

  public FindMatchingAddressesService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public List<Address> findMatchingAddresses(String address) {
    return addressRepository.findMatching(address);
  }
}
