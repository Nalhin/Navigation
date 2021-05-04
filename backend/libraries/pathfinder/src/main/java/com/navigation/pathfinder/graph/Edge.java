package com.navigation.pathfinder.graph;

import java.util.Objects;

public final class Edge {

  private final Vertex from;
  private final Vertex to;
  private final int maxSpeed;

  public Edge(Vertex from, Vertex to, int maxSpeed) {
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

  public Edge reversed() {
    return new Edge(to, from, maxSpeed);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }
}
