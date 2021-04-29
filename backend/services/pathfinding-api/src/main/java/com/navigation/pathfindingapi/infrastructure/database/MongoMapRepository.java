package com.navigation.pathfindingapi.infrastructure.database;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfindingapi.domain.MapNode;
import com.navigation.pathfindingapi.domain.MapRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MongoMapRepository implements MapRepository {

  private final MongoConnectionRepository connectionRepository;
  private final MongoNodeRepository nodeRepository;

  public MongoMapRepository(
      MongoConnectionRepository connectionRepository, MongoNodeRepository nodeRepository) {
    this.connectionRepository = connectionRepository;
    this.nodeRepository = nodeRepository;
  }

  @Override
  public Optional<MapNode> closestNode(Coordinates location) {
    Optional<StreetNodeEntity> node =
        nodeRepository.findTop1ByLocationNear(
            new GeoJsonPoint(location.getLongitude(), location.getLatitude()));

    return node.map(
        first ->
            new MapNode(
                first.getId(),
                new Coordinates(first.getLocation().getY(), first.getLocation().getX())));
  }

  @Cacheable("graph")
  @Override
  public Graph prepareGraph() {
    var builder = new GraphBuilder();
    nodeRepository
        .findAll()
        .forEach(
            (node) ->
                builder.addVertex(
                    node.getId(),
                    new Coordinates(node.getLocation().getY(), node.getLocation().getX())));

    connectionRepository
        .findAll()
        .forEach(
            (connection) ->
                builder.connectByIds(
                    connection.getFromId(), connection.getToId(), connection.getMaxSpeed()));

    return builder.asGraph();
  }
}
