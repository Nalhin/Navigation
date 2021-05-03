package com.navigation.pathfinder.graph;

import java.util.*;

public final class GraphBuilder {

  private final Map<Long, Vertex> vertices = new HashMap<>();
  private final Map<Vertex, List<Edge>> adjacencyList = new HashMap<>();

  public GraphBuilder addVertex(long id, Coordinates location) {
    return addVertex(new Vertex(id, location));
  }

  public GraphBuilder addVertex(Vertex vertex) {
    adjacencyList.put(vertex, new ArrayList<>());
    vertices.put(vertex.getId(), vertex);
    return this;
  }

  public GraphBuilder connect(Vertex from, Vertex to, int maxSpeed) {
    if (!adjacencyList.containsKey(from)) {
      adjacencyList.put(from, new ArrayList<>());
    }
    var edges = adjacencyList.get(from);
    edges.add(new Edge(from, to, maxSpeed));

    return this;
  }

  public GraphBuilder connectByIds(long fromId, long toId, int maxSpeed) {
    if (!vertices.containsKey(fromId) || !vertices.containsKey(toId)) {
      throw new IllegalArgumentException("Vertex not present");
    }

    return connect(vertices.get(fromId), vertices.get(toId), maxSpeed);
  }

  public Graph asGraph() {
    return new Graph(adjacencyList, vertices);
  }
}
