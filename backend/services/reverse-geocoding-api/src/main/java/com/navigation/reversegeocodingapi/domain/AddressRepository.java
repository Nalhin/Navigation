package com.navigation.reversegeocodingapi.domain;

import java.util.Optional;

public interface AddressRepository {
  Optional<Address> findClosestAddressWithinThreshold(Location location, double distanceThreshold);
}
