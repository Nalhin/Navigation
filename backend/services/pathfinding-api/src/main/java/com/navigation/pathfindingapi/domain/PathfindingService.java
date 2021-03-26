package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.pathfinding.DijkstraPathfindingStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class PathfindingService {

  private final MapRepository mapRepository;

  public PathfindingService(MapRepository mapRepository) {
    this.mapRepository = mapRepository;
  }

  public Path getClosestPathBetween(MapLocation start, MapLocation end) {
    var startNode = mapRepository.closestNode(start).orElseThrow();
    var endNode = mapRepository.closestNode(end).orElseThrow();

    var mapNodes = mapRepository.getNodes();
    var mapConnections = mapRepository.getConnections();
    var builder = new GraphBuilder();

    Map<Long, Vertex> vertexSet = new HashMap<>();
    for (MapNode node : mapNodes) {
      var currVertex = new Vertex(node.getId(), new Coordinates(node.getLocation().getLatitude(), node.getLocation().getLongitude()));
      builder.addVertex(currVertex);
      vertexSet.put(currVertex.getId(), currVertex);
    }
    for (MapConnection connection : mapConnections) {
      builder.connect(vertexSet.get(connection.getFrom().getId()), vertexSet.get(connection.getTo().getId()));
    }
    var strategy = new DijkstraPathfindingStrategy();
    return strategy.findShortestPath(vertexSet.get(startNode.getId()), vertexSet.get(endNode.getId()), builder.asGraph());
  }
}
