package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;

public class PathBetweenCoordinatesQuery {

  private final Coordinates start;
  private final Coordinates end;

  private final PathfindingAlgorithms pathfindingAlgorithm;
  private final PathfindingOptimizations pathfindingOptimization;

  public PathBetweenCoordinatesQuery(
      Coordinates start,
      Coordinates end,
      PathfindingAlgorithms pathfindingAlgorithm,
      PathfindingOptimizations pathfindingOptimization) {
    this.start = start;
    this.end = end;
    this.pathfindingAlgorithm = pathfindingAlgorithm;
    this.pathfindingOptimization = pathfindingOptimization;
  }

  public Coordinates getStart() {
    return start;
  }

  public Coordinates getEnd() {
    return end;
  }

  public PathfindingAlgorithms getPathfindingAlgorithm() {
    return pathfindingAlgorithm;
  }

  public PathfindingOptimizations getPathfindingOptimization() {
    return pathfindingOptimization;
  }
}
