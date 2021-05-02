package com.navigation.geocodingapi.infrastructure.database;

import com.navigation.geocodingapi.domain.Address;
import com.navigation.geocodingapi.domain.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatabaseAddressRepository implements AddressRepository {
  private final ElasticSearchAddressEntityRepository addressRepository;
  private final DatabaseMapper mapper;

  public DatabaseAddressRepository(
          ElasticSearchAddressEntityRepository addressRepository, DatabaseMapper mapper) {
    this.addressRepository = addressRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Address> searchAllByAddress(String search) {
    return addressRepository.searchAllByAddress(search).stream()
        .map(mapper::toDomain)
        .collect(Collectors.toList());
  }
}
