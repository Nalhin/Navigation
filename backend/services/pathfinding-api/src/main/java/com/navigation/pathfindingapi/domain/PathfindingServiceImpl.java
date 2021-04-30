package com.navigation.pathfindingapi.domain;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PathfindingServiceImpl implements PathfindingService {

  private final PathfindingStrategyFactory factory = new PathfindingStrategyFactory();
  private final MapRepository mapRepository;

  public PathfindingServiceImpl(MapRepository mapRepository) {
    this.mapRepository = mapRepository;
  }

  @Override
  public PathWithExecutionSummary calculatePathBetween(CalculatePathBetweenQuery query) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getStart()));
      var endFuture =
          CompletableFuture.supplyAsync(() -> mapRepository.closestNode(query.getEnd()));
      var startNode = startFuture.get().orElseThrow();
      var endNode = endFuture.get().orElseThrow();

      var graph = mapRepository.prepareGraph();

      var before = Instant.now();
      var path =
          factory
              .pathfindingStrategy(query.getAlgorithm(), query.getOptimizations())
              .findShortestPath(
                  graph.getVertexById(startNode.getId()),
                  graph.getVertexById(endNode.getId()),
                  graph);

      return new PathWithExecutionSummary(path, before, Instant.now(), query.getAlgorithm(), query.getOptimizations());
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Override
  public PathWithExecutionSummary calculateBoundedPathBetween(
      CalculatePathBetweenQuery query, BoundsQuery boundsQuery) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(
              () -> mapRepository.closestNodeWithinBounds(query.getStart(), boundsQuery));
      var endFuture =
          CompletableFuture.supplyAsync(
              () -> mapRepository.closestNodeWithinBounds(query.getEnd(), boundsQuery));
      var startNode = startFuture.get().orElseThrow();
      var endNode = endFuture.get().orElseThrow();

      var graph = mapRepository.prepareGraphWithinBounds(boundsQuery);

      var before = Instant.now();
      var path =
          factory
              .pathfindingStrategy(query.getAlgorithm(), query.getOptimizations())
              .findShortestPath(
                  graph.getVertexById(startNode.getId()),
                  graph.getVertexById(endNode.getId()),
                  graph);

      return new PathWithExecutionSummary(path, before, Instant.now(), query.getAlgorithm(), query.getOptimizations());
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
