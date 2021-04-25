package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.pathfinding.DijkstraPathfindingStrategy;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PathfindingService {

  private final MapRepository mapRepository;
  private Graph graph;

  public PathfindingService(MapRepository mapRepository) {
    this.mapRepository = mapRepository;
  }

  public Path getClosestPathBetween(Coordinates start, Coordinates end) {
    try {
      var startFuture = CompletableFuture.supplyAsync(() -> mapRepository.closestNode(start));
      var endFuture = CompletableFuture.supplyAsync(() -> mapRepository.closestNode(end));
      var startNode = startFuture.get().orElseThrow();
      var endNode = endFuture.get().orElseThrow();

      var graph = loadGraph();

      return new DijkstraPathfindingStrategy()
          .findShortestPath(
              graph.getVertexById(startNode.getId()), graph.getVertexById(endNode.getId()), graph);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  public synchronized Graph loadGraph() {
    if (graph != null) {
      return graph;
    }
    var mapNodes = mapRepository.getNodes();
    var mapConnections = mapRepository.getConnections();
    var builder = new GraphBuilder();

    mapNodes.forEach(node -> builder.addVertex(node.getId(), node.getLocation()));

    mapConnections.forEach(
        connection ->
            builder.connectByIds(
                connection.getFromId(), connection.getToId(), connection.getMaxSpeed()));

    graph = builder.asGraph();
    return graph;
  }
}
