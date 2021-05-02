package com.navigation.reversegeocodingapi.infrastructure.database;

import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface MongoAddressRepository extends MongoRepository<AddressEntity, Long> {

  Optional<AddressEntity> findTop1ByLocationNear(GeoJsonPoint point, Distance distance);
}
