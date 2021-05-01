package com.navigation.geocodingapi.domain;

import java.util.List;

public interface AddressService {
  List<Address> findAllByAddress(String address);
}
