package com.navigation.pathfindingapi.infrastructure.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoConnectionRepository extends MongoRepository<StreetConnectionEntity, String> {}
