package com.navigation.pathfindingapi.infrastructure.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
interface MongoConnectionRepository extends MongoRepository<StreetConnectionEntity, String> {
  List<StreetConnectionEntity> findByFromIdInAndToIdIn(Collection<Long> id, Collection<Long> ids);
}
