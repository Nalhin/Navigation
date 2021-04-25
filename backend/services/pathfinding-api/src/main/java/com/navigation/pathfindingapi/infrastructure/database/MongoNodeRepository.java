package com.navigation.pathfindingapi.infrastructure.database;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface MongoNodeRepository extends MongoRepository<StreetNodeEntity, Long> {

  Optional<StreetNodeEntity> findTop1ByLocationNear(GeoJsonPoint point);
}
