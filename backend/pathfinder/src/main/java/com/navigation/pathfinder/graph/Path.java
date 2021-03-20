package com.navigation.pathfinder.graph;

import java.util.List;

public class Path {

  private final double distanceInMeters;
  private final int timeInSeconds;
  private final List<GraphNode> nodes;

  public Path(double distanceInMeters, int timeInSeconds, List<GraphNode> nodes) {
    this.distanceInMeters = distanceInMeters;
    this.timeInSeconds = timeInSeconds;
    this.nodes = nodes;
  }
}
