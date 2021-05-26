package com.navigation.reversegeocodingapi.application;

import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.domain.Location;
import java.util.Optional;

public interface AddressRepository {
  Optional<Address> findClosestAddressWithinThreshold(Location location, double distanceThreshold);
}
