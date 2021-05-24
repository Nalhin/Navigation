package com.navigation.geocoding.domain;

import java.util.List;

public interface AddressService {
  List<Address> findAllByAddress(String address);
}
