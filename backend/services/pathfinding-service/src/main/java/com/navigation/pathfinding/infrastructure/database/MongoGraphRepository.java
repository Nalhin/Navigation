package com.navigation.pathfinding.infrastructure.database;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.domain.Bounds;
import com.navigation.pathfinding.domain.GraphRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MongoGraphRepository implements GraphRepository {

  private final PathfindingDatabaseMapper pathfindingDatabaseMapper;
  private final MongoConnectionRepository connectionRepository;
  private final MongoNodeRepository nodeRepository;

  public MongoGraphRepository(
          MongoConnectionRepository connectionRepository,
          MongoNodeRepository nodeRepository,
          PathfindingDatabaseMapper mapper) {
    this.connectionRepository = connectionRepository;
    this.nodeRepository = nodeRepository;
    this.pathfindingDatabaseMapper = mapper;
  }

  @Override
  public Optional<Vertex> closestNode(Coordinates location) {
    Optional<StreetNodeEntity> node =
        nodeRepository.findTop1ByLocationNear(
            new GeoJsonPoint(location.getLongitude(), location.getLatitude()));

    return node.map(
        first ->
            new Vertex(
                first.getId(),
                new Coordinates(first.getLocation().getY(), first.getLocation().getX())));
  }

  @Cacheable(value = "graph", sync = true)
  @Override
  public Graph prepareGraph() {
    var builder = new GraphBuilder();
    var locations = nodeRepository.findAll();

    locations.forEach((streetNode) -> builder.addVertex(pathfindingDatabaseMapper.toVertex(streetNode)));

    connectionRepository
        .findAll()
        .forEach(
            (connection) ->
                builder.connectByIds(
                    connection.getFromId(), connection.getToId(), connection.getMaxSpeed()));

    return builder.asGraph();
  }

  @Cacheable(value = "graphBounded", sync = true)
  @Override
  public Graph prepareGraphWithinBounds(Bounds bounds) {

    var builder = new GraphBuilder();
    var locations = nodeRepository.findByLocationWithin(pathfindingDatabaseMapper.toBox(bounds));

    locations.forEach((streetNode) -> builder.addVertex(pathfindingDatabaseMapper.toVertex(streetNode)));

    var ids = locations.stream().map(StreetNodeEntity::getId).collect(Collectors.toList());

    connectionRepository
        .findByFromIdInAndToIdIn(ids, ids)
        .forEach(
            (connection) ->
                builder.connectByIds(
                    connection.getFromId(), connection.getToId(), connection.getMaxSpeed()));

    return builder.asGraph();
  }
}
