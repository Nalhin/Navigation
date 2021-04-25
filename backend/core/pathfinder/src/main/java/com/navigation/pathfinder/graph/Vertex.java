package com.navigation.pathfinder.graph;


import java.util.Objects;

public class Vertex {

  private final long id;
  private final Coordinates coordinates;

  Vertex(long id, Coordinates coordinates) {
    this.id = id;
    this.coordinates = coordinates;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public long getId() {
    return id;
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
