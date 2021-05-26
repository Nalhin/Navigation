package com.navigation.reversegeocodingapi.infrastructure.database;

import com.navigation.reversegeocodingapi.domain.Address;
import com.navigation.reversegeocodingapi.application.AddressRepository;
import com.navigation.reversegeocodingapi.domain.Location;
import java.util.Optional;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseAddressRepository implements AddressRepository {

  private final MongoAddressRepository mongoRepository;
  private final DatabaseMapper databaseMapper;

  public DatabaseAddressRepository(
      MongoAddressRepository mongoRepository, DatabaseMapper databaseMapper) {
    this.mongoRepository = mongoRepository;
    this.databaseMapper = databaseMapper;
  }

  @Override
  public Optional<Address> findClosestAddressWithinThreshold(
      Location location, double distanceThreshold) {
    return mongoRepository
        .findTop1ByLocationNear(
            databaseMapper.toEntity(location), new Distance(distanceThreshold, Metrics.KILOMETERS))
        .map(databaseMapper::toDomain);
  }
}
