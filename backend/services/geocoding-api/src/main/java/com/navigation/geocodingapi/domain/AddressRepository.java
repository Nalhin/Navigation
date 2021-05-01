package com.navigation.geocodingapi.domain;

import java.util.List;

public interface AddressRepository {
  List<Address> searchAllByAddress(String search);
}
