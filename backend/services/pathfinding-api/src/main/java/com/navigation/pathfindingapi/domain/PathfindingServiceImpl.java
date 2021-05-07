package com.navigation.pathfindingapi.domain;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
class PathfindingServiceImpl implements PathfindingService {

  private final PathfindingStrategyFactory factory = new PathfindingStrategyFactory();
  private final GraphRepository graphRepository;

  public PathfindingServiceImpl(GraphRepository graphRepository) {
    this.graphRepository = graphRepository;
  }

  @Override
  public PathWithExecutionSummary calculatePathBetween(PathBetweenCoordinatesQuery query) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(() -> graphRepository.closestNode(query.getStart()));
      var endFuture =
          CompletableFuture.supplyAsync(() -> graphRepository.closestNode(query.getEnd()));
      var startVertex = startFuture.get().orElseThrow();
      var endVertex = endFuture.get().orElseThrow();

      var graph = graphRepository.prepareGraph();

      var before = Instant.now();
      var path =
          factory
              .pathfindingStrategy(query.getPathfindingAlgorithm(), query.getPathfindingOptimization())
              .findShortestPath(startVertex, endVertex, graph);

      return new PathWithExecutionSummary(
          path, before, Instant.now(), query.getPathfindingAlgorithm(), query.getPathfindingOptimization());
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Override
  public PathWithExecutionSummary calculateBoundedPathBetween(
          PathBetweenCoordinatesQuery query, Bounds bounds) {
    try {
      var startFuture =
          CompletableFuture.supplyAsync(() -> graphRepository.closestNode(query.getStart()));
      var endFuture =
          CompletableFuture.supplyAsync(() -> graphRepository.closestNode(query.getEnd()));
      var startVertex = startFuture.get().orElseThrow();
      var endVertex = endFuture.get().orElseThrow();

      var graph = graphRepository.prepareGraphWithinBounds(bounds);

      var before = Instant.now();
      var path =
          factory
              .pathfindingStrategy(query.getPathfindingAlgorithm(), query.getPathfindingOptimization())
              .findShortestPath(startVertex, endVertex, graph);

      return new PathWithExecutionSummary(
          path, before, Instant.now(), query.getPathfindingAlgorithm(), query.getPathfindingOptimization());
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
