package com.navigation.pathfinder.graph;

public class GraphEdge {

  private final GraphNode from;
  private final GraphNode to;
  private final int maxSpeedInKmPerHour = 30;

  public GraphEdge(GraphNode from, GraphNode to) {
    this.from = from;
    this.to = to;
  }

  public GraphNode getFrom() {
    return from;
  }

  public GraphNode getTo() {
    return to;
  }

  public int getMaxSpeedInKmPerHour() {
    return maxSpeedInKmPerHour;
  }
}
