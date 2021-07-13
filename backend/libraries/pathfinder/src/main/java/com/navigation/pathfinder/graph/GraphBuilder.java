package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.exceptions.VertexNotPresentException;
import java.util.*;

public final class GraphBuilder {

  private final Map<Long, Vertex> vertices = new HashMap<>();
  private final Map<Vertex, List<Edge>> adjacencyList = new HashMap<>();

  public GraphBuilder addVertex(Vertex vertex) {
    adjacencyList.put(vertex, new ArrayList<>());
    vertices.put(vertex.getId(), vertex);
    return this;
  }

  public GraphBuilder connect(Vertex from, Vertex to, int maxSpeed) {
    if (!vertices.containsKey(from.getId())) {
      throw new VertexNotPresentException(from.getId());
    }
    if (!vertices.containsKey(to.getId())) {
      throw new VertexNotPresentException(to.getId());
    }

    connectVertices(from, to, maxSpeed);
    return this;
  }

  private void connectVertices(Vertex from, Vertex to, int maxSpeed) {
    adjacencyList.computeIfAbsent(from, (e) -> new ArrayList<>()).add(new Edge(from, to, maxSpeed));
  }

  public GraphBuilder connectByIds(long fromId, long toId, int maxSpeed) {
    if (!vertices.containsKey(fromId)) {
      throw new VertexNotPresentException(fromId);
    }
    if (!vertices.containsKey(toId)) {
      throw new VertexNotPresentException(toId);
    }
    return connect(vertices.get(fromId), vertices.get(toId), maxSpeed);
  }

  public Graph asGraph() {
    return new Graph(adjacencyList, vertices);
  }
}
