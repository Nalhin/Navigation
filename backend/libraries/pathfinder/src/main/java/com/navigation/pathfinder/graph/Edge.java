package com.navigation.pathfinder.graph;

public final class Edge {

  private final Vertex from;
  private final Vertex to;
  private final int maxSpeed;

  Edge(Vertex from, Vertex to, int maxSpeed) {
    this.from = from;
    this.to = to;
    this.maxSpeed = maxSpeed;
  }

  public Vertex getFrom() {
    return from;
  }

  public Vertex getTo() {
    return to;
  }

  public int getMaxSpeed() {
    return maxSpeed;
  }
}
