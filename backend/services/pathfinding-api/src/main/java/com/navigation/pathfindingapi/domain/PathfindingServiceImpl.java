package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PathfindingServiceImpl implements PathfindingService {

  private final PathfindingStrategyFactory factory = new PathfindingStrategyFactory();
  private final MapRepository mapRepository;
  private Graph graph;

  public PathfindingServiceImpl(MapRepository mapRepository) {
    this.mapRepository = mapRepository;
  }

  @Override
  public PathWithExecutionDuration calculatePathBetween(CalculatePathBetweenQuery query) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getStart()));
      var endFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getEnd()));
      var startNode = startFuture.get().orElseThrow();
      var endNode = endFuture.get().orElseThrow();

      var graph = loadGraph();

      var now = Instant.now();
      var path = factory
          .pathfindingStrategy(query.getAlgorithm(), query.getOptimizations())
          .findShortestPath(
              graph.getVertexById(startNode.getId()), graph.getVertexById(endNode.getId()), graph);

      return new PathWithExecutionDuration(path, Duration.between(now, Instant.now()));
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
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
