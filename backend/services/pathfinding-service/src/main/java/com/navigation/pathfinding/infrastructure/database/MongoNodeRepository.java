package com.navigation.pathfinding.infrastructure.database;

import io.vavr.control.Option;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MongoNodeRepository extends MongoRepository<StreetNodeEntity, Long> {

  Option<StreetNodeEntity> findTop1ByLocationNear(GeoJsonPoint point, Distance limit);

  List<StreetNodeEntity> findByLocationWithin(Box points);
}
