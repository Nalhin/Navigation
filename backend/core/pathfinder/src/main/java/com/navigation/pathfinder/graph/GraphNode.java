package com.navigation.pathfinder.graph;


import java.util.Objects;

public class GraphNode {

  private final int id;
  private final Coordinates coordinates;

  public GraphNode(int id, Coordinates coordinates) {
    this.id = id;
    this.coordinates = coordinates;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GraphNode graphNode = (GraphNode) o;
    return id == graphNode.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
