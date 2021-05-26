package com.navigation.geocoding.domain;

import java.util.List;

public interface AddressRepository {
  List<Address> findMatching(String search);
}
