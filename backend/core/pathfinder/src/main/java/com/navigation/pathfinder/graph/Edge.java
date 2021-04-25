package com.navigation.pathfinder.graph;

public class Edge {

  private final Vertex from;
  private final Vertex to;
  private final int maxSpeed = 30;

  public Edge(Vertex from, Vertex to) {
    this.from = from;
    this.to = to;
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
