package com.navigation.reversegeocodingapi.domain;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReverseGeocodingServiceImpl implements ReverseGeocodingService {

  private final AddressRepository repository;

  public ReverseGeocodingServiceImpl(AddressRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Address> findClosestAddress(Location location) {
    return repository.findClosestAddressWithinThreshold(location, 0.2);
  }
}
