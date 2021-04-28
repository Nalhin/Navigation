package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfinder.graph.Path;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PathfindingServiceImpl implements PathfindingService {

  private final PathfindingStrategyFactory factory = new PathfindingStrategyFactory();
  private final MapRepository mapRepository;
  private Graph graph;

  public PathfindingServiceImpl(MapRepository mapRepository) {
    this.mapRepository = mapRepository;
  }

  @Override
  public Path calculatePathBetween(CalculatePathBetweenQuery query) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getStart()));
      var endFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getEnd()));
      var startNode = startFuture.get().orElseThrow();
      var endNode = endFuture.get().orElseThrow();

      var graph = loadGraph();

      return factory
          .pathfindingStrategy(query.getAlgorithm(), query.getOptimizations())
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
