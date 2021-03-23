package com.navigation.pathfinder.graph;


import java.util.Objects;

public class Vertex {

  private final int id;
  private final Coordinates coordinates;

  public Vertex(int id, Coordinates coordinates) {
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
    Vertex vertex = (Vertex) o;
    return id == vertex.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
