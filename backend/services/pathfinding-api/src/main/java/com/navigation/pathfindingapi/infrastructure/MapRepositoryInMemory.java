package com.navigation.pathfindingapi.infrastructure;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.weight.HaversineDistanceCalculator;
import com.navigation.pathfindingapi.domain.MapConnection;
import com.navigation.pathfindingapi.domain.MapLocation;
import com.navigation.pathfindingapi.domain.MapNode;
import com.navigation.pathfindingapi.domain.MapRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class MapRepositoryInMemory implements MapRepository {

  private final LoadedMap loadedMap;
  private final HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

  public MapRepositoryInMemory(MapProperties mapProperties, MapRepositoryLoader loader) {
    this.loadedMap = loader.loadMap(mapProperties.getFilePath());
  }

  @Override
  public Optional<MapNode> closestNode(MapLocation location) {
    return loadedMap.getNodes().stream()
        .min(Comparator.comparingDouble((node) -> distanceCalculator.calculateDistance(
            new Coordinates(location.getLatitude(), location.getLongitude()),
            new Coordinates(node.getLocation().getLatitude(), node.getLocation().getLongitude()))));
  }

  @Override
  public List<MapNode> getNodes() {
    return loadedMap.getNodes();
  }

  @Override
  public List<MapConnection> getConnections() {
    return loadedMap.getConnections();
  }
}
