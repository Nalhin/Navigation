package com.navigation.pathfinding.domain;

import com.navigation.pathfinder.pathfinding.PathSummary;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class PathfindingExecutionSummary {

  private final PathSummary pathSummary;
  private final Instant start;
  private final Instant end;
  private final PathfindingAlgorithms algorithm;
  private final PathfindingOptimizations optimization;

  public PathfindingExecutionSummary(
      PathSummary pathSummary,
      Instant start,
      Instant end,
      PathfindingAlgorithms algorithm,
      PathfindingOptimizations optimization) {
    this.pathSummary = pathSummary;
    this.start = start;
    this.end = end;
    this.algorithm = algorithm;
    this.optimization = optimization;
  }

  public PathSummary getPathSummary() {
    return pathSummary;
  }

  public double getExecutionDurationInSeconds() {
    return ChronoUnit.MILLIS.between(start, end) / 1000.0;
  }

  public PathfindingAlgorithms getAlgorithm() {
    return algorithm;
  }

  public PathfindingOptimizations getOptimization() {
    return optimization;
  }
}
