package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.path.PathSummary;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PathWithExecutionSummary {

  private final PathSummary pathSummary;
  private final Instant start;
  private final Instant end;
  private final PathfindingAlgorithms algorithm;
  private final PathfindingOptimizations optimization;

  public PathWithExecutionSummary(
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

  public PathSummary getPath() {
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
