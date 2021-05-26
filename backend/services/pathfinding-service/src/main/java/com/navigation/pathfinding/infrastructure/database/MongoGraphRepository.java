package com.navigation.pathfinding.infrastructure.database;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.application.GraphRepository;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.BoundsQuery;
import io.vavr.control.Option;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MongoGraphRepository implements GraphRepository {

  private final MongoConnectionRepository connectionRepository;
  private final MongoNodeRepository nodeRepository;
  private final PathfindingDatabaseMapper databaseMapper;

  public MongoGraphRepository(
      MongoConnectionRepository connectionRepository,
      MongoNodeRepository nodeRepository,
      PathfindingDatabaseMapper mapper) {
    this.connectionRepository = connectionRepository;
    this.nodeRepository = nodeRepository;
    this.databaseMapper = mapper;
  }

  @Override
  public Option<Vertex> closestVertex(Coordinates location, double distanceThresholdInKm) {
    Option<StreetNodeEntity> node =
        nodeRepository.findTop1ByLocationNear(
            new GeoJsonPoint(location.getLongitude(), location.getLatitude()),
            new Distance(distanceThresholdInKm, Metrics.KILOMETERS));

    return node.map(databaseMapper::toVertex);
  }

  @Cacheable(value = "graph", sync = true)
  @Override
  public Graph prepareGraph() {
    var locations = nodeRepository.findAll();
    var connections = connectionRepository.findAll();

    return databaseMapper.buildGraph(locations, connections);
  }

  @Cacheable(value = "graphBounded", sync = true)
  @Override
  public Graph prepareGraphWithinBounds(BoundsQuery bounds) {
    var streetNodes = nodeRepository.findByLocationWithin(databaseMapper.toBox(bounds));
    var ids = streetNodes.stream().map(StreetNodeEntity::getId).collect(Collectors.toList());
    var connections = connectionRepository.findByFromIdInAndToIdIn(ids, ids);

    return databaseMapper.buildGraph(streetNodes, connections);
  }
}
