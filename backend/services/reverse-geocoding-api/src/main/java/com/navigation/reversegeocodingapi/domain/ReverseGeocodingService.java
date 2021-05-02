package com.navigation.reversegeocodingapi.domain;

import java.util.Optional;

public interface ReverseGeocodingService {

  Optional<Address> findClosestAddress(Location location);
}
