package com.navigation.pathfinder.graph;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class Graph {

  private final Map<Vertex, List<Edge>> adjacencyList;
  private final Map<Long, Vertex> vertices;
  private final ConcurrentHashMap<Graph, Graph> cache = new ConcurrentHashMap<>();

  Graph(Map<Vertex, List<Edge>> adjacencyList, Map<Long, Vertex> vertices) {
    this.adjacencyList = deepImmutableCopy(adjacencyList);
    this.vertices = new HashMap<>(vertices);
  }

  public Collection<Edge> getVertexEdges(Vertex node) {
    return adjacencyList.getOrDefault(node, Collections.emptyList());
  }

  private Map<Vertex, List<Edge>> deepImmutableCopy(Map<Vertex, List<Edge>> adjacencyList) {
    return adjacencyList.entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey, entry -> List.copyOf(entry.getValue())));
  }

  public Graph reversed() {
    return cache.computeIfAbsent(this, Graph::computeReversedGraph);
  }

  private Graph computeReversedGraph() {
    var reversedAdjacencyList = new HashMap<Vertex, List<Edge>>();

    for (var entry : adjacencyList.entrySet()) {
      for (var edge : entry.getValue()) {
        var reversed = edge.reversed();
        var list = reversedAdjacencyList.getOrDefault(reversed.getFrom(), new ArrayList<>());
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
