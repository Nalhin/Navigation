package com.navigation.reversegeocodingapi.application;

import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.domain.Location;
import java.util.Optional;

public interface FindClosestAddressUseCase {

  Optional<Address> findClosestAddress(Location location);
}
