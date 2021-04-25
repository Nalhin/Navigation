package com.navigation.pathfinder.graph;

import java.util.List;

public final class Path {

  private final List<Vertex> vertices;

  public Path(List<Vertex> vertices) {
    this.vertices = vertices;
  }

  public List<Vertex> getVertices() {
    return vertices;
  }
}
