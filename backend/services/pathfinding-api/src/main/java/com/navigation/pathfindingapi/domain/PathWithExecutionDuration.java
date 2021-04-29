package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.pathfinding.PathSummary;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PathWithExecutionDuration {

  private final PathSummary pathSummary;
  private final Instant start;
  private final Instant end;

  public PathWithExecutionDuration(PathSummary pathSummary, Instant start, Instant end) {
    this.pathSummary = pathSummary;
    this.start = start;
    this.end = end;
  }

  public PathSummary getPath() {
    return pathSummary;
  }

  public double getExecutionDurationInSeconds() {
    return ChronoUnit.MILLIS.between(start, end) / 1000.0;
  }
}
