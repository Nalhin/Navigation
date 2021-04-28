package com.navigation.pathfindingapi.infrastructure.database;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfindingapi.domain.MapConnection;
import com.navigation.pathfindingapi.domain.MapNode;
import com.navigation.pathfindingapi.domain.MapRepository;
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

  @Override
  public List<MapNode> getNodes() {
    return nodeRepository.findAll().stream()
        .map(
            item ->
                new MapNode(
                    item.getId(),
                    new Coordinates(item.getLocation().getY(), item.getLocation().getX())))
        .collect(Collectors.toList());
  }

  @Override
  public List<MapConnection> getConnections() {
    return connectionRepository.findAll().stream()
        .map(item -> new MapConnection(item.getFromId(), item.getToId(), item.getMaxSpeed()))
        .collect(Collectors.toList());
  }
}
