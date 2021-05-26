package com.navigation.geocoding.infrastructure.database;

import com.navigation.geocoding.domain.Address;
import com.navigation.geocoding.domain.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
class DatabaseAddressRepository implements AddressRepository {
  private final ElasticSearchAddressEntityRepository addressRepository;
  private final DatabaseMapper mapper;

  public DatabaseAddressRepository(
          ElasticSearchAddressEntityRepository addressRepository, DatabaseMapper mapper) {
    this.addressRepository = addressRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Address> findMatching(String search) {
    return addressRepository.searchAllByAddress(search).stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
  }
}
