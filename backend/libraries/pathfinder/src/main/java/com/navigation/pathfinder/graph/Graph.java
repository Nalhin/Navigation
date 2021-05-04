package com.navigation.pathfinder.graph;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class Graph {

  private final Map<Vertex, Set<Edge>> adjacencyList;
  private final Map<Long, Vertex> vertices;

  Graph(Map<Vertex, Set<Edge>> adjacencyList, Map<Long, Vertex> vertices) {
    this.adjacencyList = deepImmutableCopy(adjacencyList);
    this.vertices = new HashMap<>(vertices);
  }

  public Collection<Edge> getVertexEdges(Vertex node) {
    return adjacencyList.getOrDefault(node, Collections.emptySet());
  }

  private Map<Vertex, Set<Edge>> deepImmutableCopy(Map<Vertex, Set<Edge>> adjacencyList) {
    return adjacencyList.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, entry -> Set.copyOf(entry.getValue())));
  }

  public Graph reversed() {
    var reversedAdjacencyList = new HashMap<Vertex, Set<Edge>>();

    for (var entry : adjacencyList.entrySet()) {
      for (var edge : entry.getValue()) {
        var reversed = edge.reversed();
        var list = reversedAdjacencyList.getOrDefault(reversed.getFrom(), new HashSet<>());
        list.add(reversed);
        reversedAdjacencyList.put(reversed.getFrom(), list);
      }
    }

    return new Graph(reversedAdjacencyList, vertices);
  }

  public Collection<Vertex> vertices() {
    return vertices.values();
  }

  public Collection<Edge> edges() {
    return adjacencyList.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
  }

  @Override
  public String toString() {
    return "Graph{" + "adjacencyList=" + adjacencyList + ", vertices=" + vertices + '}';
  }
}
