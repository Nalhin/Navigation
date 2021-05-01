package com.navigation.pathfinder.graph;

import java.util.*;
import java.util.List;

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

  public Collection<Edge> getVertexEdges(Vertex node) {
    return nodesWithEdges.getOrDefault(node, Collections.emptyList());
  }

  public Graph reversed() {
    var reversedNodesWithEdges = new HashMap<Vertex, List<Edge>>();
    for (var entry : nodesWithEdges.entrySet()) {
      for (var edge : entry.getValue()) {
        var reversed = edge.reversed();
        var list = reversedNodesWithEdges.getOrDefault(reversed.getFrom(), new ArrayList<>());
        list.add(reversed);
        reversedNodesWithEdges.put(reversed.getFrom(), list);
      }
    }

    return new Graph(reversedNodesWithEdges, vertices);
  }

  public Collection<Vertex> vertices() {
    return vertices.values();
  }

  public Collection<Edge> edges() {
    return nodesWithEdges.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
  }
}
