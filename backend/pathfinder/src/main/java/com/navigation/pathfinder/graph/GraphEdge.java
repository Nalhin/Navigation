package com.navigation.pathfinder.graph;

public class GraphEdge {
  private final int maxSpeed;
  private final boolean isBidirectional;
  private final GraphNode from;
  private final GraphNode to;

  public GraphEdge(int maxSpeed, boolean isBidirectional, GraphNode from, GraphNode to) {
    this.maxSpeed = maxSpeed;
    this.isBidirectional = isBidirectional;
    this.from = from;
    this.to = to;
  }
}
