package com.navigation.geocoding.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public List<Address> findAllByAddress(String address) {
    return addressRepository.searchAllByAddress(address);
  }
}
