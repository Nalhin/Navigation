package com.navigation.pathfinder.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class Graph {

  private final Map<Vertex, List<Edge>> nodesWithEdges;
  private final Map<Long, Vertex> vertices;

  Graph(Map<Vertex, List<Edge>> nodesWithEdges, Map<Long, Vertex> vertices) {
    this.nodesWithEdges = nodesWithEdges;
    this.vertices = vertices;
  }

  public Vertex getVertexById(long id) {
    return vertices.get(id);
  }

  public Collection<Edge> getNodeEdges(Vertex node) {
    return nodesWithEdges.get(node);
  }

  public Collection<Vertex> vertices() {
    return vertices.values();
  }

  public Collection<Edge> edges() {
    return nodesWithEdges.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
  }
}
