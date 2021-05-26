package com.navigation.reversegeocodingapi.application;

import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.domain.Location;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FindClosestAddressService implements FindClosestAddressUseCase {

  private static final double DISTANCE_THRESHOLD = 0.3;

  private final AddressRepository repository;

  public FindClosestAddressService(AddressRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Address> findClosestAddress(Location location) {
    return repository.findClosestAddressWithinThreshold(location, DISTANCE_THRESHOLD);
  }
}
